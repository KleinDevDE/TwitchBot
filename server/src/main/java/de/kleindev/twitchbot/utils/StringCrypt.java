package de.kleindev.twitchbot.utils;

import lombok.SneakyThrows;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.security.*;

public class StringCrypt {
	@SneakyThrows
	public static void generateKey(File file){
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(8192);
		KeyPair kp = kpg.generateKeyPair();
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file, false))){
			out.writeObject(kp.getPrivate());
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String encrypt(File keyFile, String text){
		try (ObjectInputStream in2 = new ObjectInputStream(new FileInputStream(new File(keyFile.getParentFile(), "key.dat")))){
			PrivateKey key = (PrivateKey) in2.readObject();
			Cipher cipher;
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encrypted = cipher.doFinal(text.getBytes());
			return new String(encrypted);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decrypt(File keyFile, String text){
		try (ObjectInputStream in2 = new ObjectInputStream(new FileInputStream(new File(keyFile.getParentFile(), "key.dat")))){
			PrivateKey key = (PrivateKey) in2.readObject();
			Cipher cipher;
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decrypted = cipher.doFinal(text.getBytes());
			return new String(decrypted);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}