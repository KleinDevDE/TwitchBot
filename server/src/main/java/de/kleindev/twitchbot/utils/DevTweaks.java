package de.kleindev.twitchbot.utils;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class DevTweaks {
	private static final char[] numbers = "0123456789".toCharArray();

	public static boolean isLong(String string) {
		try {
			Long.parseLong(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isNumber(char c) {
		for (char n : numbers) {
			if (n == c)
				return true;
		}
		return false;
	}

	public static boolean isDouble(String string) {
		try {
			Double.parseDouble(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isFloat(String string) {
		try {
			Float.parseFloat(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isInteger(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isBoolean(String string) {
		try {
			Boolean.parseBoolean(string);
			return true;
		} catch (UnsupportedOperationException e) {
			return false;
		}
	}

	public static boolean isValidURL(String url) {
		try {
			URL u = new URL(url);
			if (u.openConnection() != null)
				return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public static boolean isUUID(String string) {
		try {
			UUID.fromString(string);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static boolean methodExists(Method obj) {
		boolean methodExists = false;
		try {
			obj.invoke("", (Object[]) null);
			methodExists = true;
		} catch (Exception ignored) {
		}
		return methodExists;
	}

	/* https://dzone.com/articles/generate-random-alpha-numeric */
	public static String randomAlphaNumeric(int count, String chars) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * chars.length());
			builder.append(chars.charAt(character));
		}
		return builder.toString();
	}

	public static String humanReadableByteCount(long bytes, boolean si) {
		int unit = si ? 1000 : 1024;
		if (bytes < unit)
			return bytes + " B";
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}

	public static String readInputStream(InputStream input) throws IOException {
		try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
			return buffer.lines().collect(Collectors.joining("\n"));
		}
	}

	public static String listToString(List<String> list) {
		StringBuilder builder = new StringBuilder();
		for (String s : list) {
			if (builder.toString().equals(""))
				builder.append(s);
			else
				builder.append("\n").append(s);
		}
		return builder.toString();
	}

	public static String getSpaces(int amount) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < amount; i++){
			sb.append(" ⠀ ");
		}
		return sb.toString();
	}

	public static String getSpaces2(int amount) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < amount; i++){
			if(i%2 == 0) {
				System.out.print("1");
				sb.append(" ");
			} else {
				System.out.print("2");
				sb.append("⠀");
			}
		}
		return sb.toString();
	}

	public static String fillString(String string, int requiredWidth){
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
		StringBuilder sb = new StringBuilder();
		Graphics2D g = img.createGraphics();
		int stringWidth = 0;
		int space_small = 2;
		int space_normal = 3;
		int space_big = 8;
		boolean run = true;
		for(char c : string.toCharArray()){
			int a = g.getFontMetrics().stringWidth(String.valueOf(c));
			stringWidth = stringWidth + a;
		}
		g.dispose();
		int totalWidth = requiredWidth - stringWidth;

		// -----

		if(totalWidth < 0)
			return string; //	TODO may cut string
		sb.append(string);

		while (run){
			if (totalWidth >= 13){
				totalWidth = totalWidth-13;
				sb.append(" ").append(" ").append("⠀");
			} else if(totalWidth >= 8){
				totalWidth = totalWidth-8;
				sb.append("⠀");
			} else if(totalWidth >= 4){
				totalWidth = totalWidth-4;
				sb.append(" ");
			} else if(totalWidth >= 2){
				totalWidth = totalWidth-2;
				sb.append(" ");
			} else run = false;
		}
		return sb.toString();
	}

	public static FilenameFilter getFileNameFilter(String... extensions) {
		List<String> extensionslist = Arrays.asList(extensions);
		FilenameFilter fileNameFilter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (name.lastIndexOf('.') > 0) {
					// get last index for '.' char
					int lastIndex = name.lastIndexOf('.');
					// get extension
					String str = name.substring(lastIndex);
					// match path name extension
					return extensionslist.contains(str);
				}
				return false;
			}
		};
		return fileNameFilter;
	}

	public static boolean listContainsKeyOfArray(Set<String> list, String[] array) {
		for (String s : array) {
			if (list.contains(s))
				return true;
		}
		return false;
	}

	public static boolean isZip(File f) {
		int fileSignature = 0;
		try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
			fileSignature = raf.readInt();
		} catch (IOException e) {
			// handle if you like
		}
		return fileSignature == 0x504B0304 || fileSignature == 0x504B0506 || fileSignature == 0x504B0708;
	}

	public static boolean isJAR(File f) {
		int fileSignature = 0;
		try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
			fileSignature = raf.readInt();
		} catch (IOException e) {
			// handle if you like
		}
		return fileSignature == 0x504B0304 || fileSignature == 0x504B0506 || fileSignature == 0x504B0708;
	}

	@SneakyThrows
	public static String encodeFileToBase64Binary(File file) {
		byte[] encoded = Base64.getEncoder().encode(FileUtils.readFileToByteArray(file));
		return new String(encoded, StandardCharsets.US_ASCII);
	}

	public static void decodeBase64BinaryToFile(String base64String, File file) throws IOException {
		byte[] encoded = Base64.getEncoder().encode(base64String.getBytes(StandardCharsets.US_ASCII));

		if (!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		if (!file.exists()){
			file.createNewFile();
		}

		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(new String(encoded, StandardCharsets.US_ASCII));
		fileWriter.flush();
		fileWriter.close();
	}
}
