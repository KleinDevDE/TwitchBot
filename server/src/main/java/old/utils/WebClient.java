package old.utils;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class WebClient {
    public static String getContent(String url){
        try {
            URL url1 = new URL(url);
            URLConnection con = url1.openConnection();
            InputStream in = con.getInputStream();
            String encoding = con.getContentEncoding();  // ** WRONG: should use "con.getContentType()" instead but it returns something like "text/html; charset=UTF-8" so this value must be parsed to extract the actual encoding
            encoding = encoding == null ? "UTF-8" : encoding;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[8192];
            int len = 0;
            while ((len = in.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            return new String(baos.toByteArray(), encoding);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void downloadImage(String url, String name) {
        try {
            URL link = new URL(url);
            InputStream in = new BufferedInputStream(link.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1 != (n = in.read(buf))) {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();
            byte[] response = out.toByteArray();
            FileOutputStream fos = new FileOutputStream("images/" + name);
            fos.write(response);
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[]  getDownloadedBytes(String url){
        try {
            URL link = new URL(url);
            InputStream in = new BufferedInputStream(link.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1 != (n = in.read(buf))) {
                out.write(buf, 0, n);
            }
            byte[] response = out.toByteArray();
            out.close();
            in.close();
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
