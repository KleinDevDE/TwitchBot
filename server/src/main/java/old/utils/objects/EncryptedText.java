package old.utils.objects;

import javax.crypto.Cipher;
import java.io.*;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;

public class EncryptedText {

	File file;
	
	public EncryptedText(File file) {
		this.file = file;
		if(!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void store(String text) {
		try {
			KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
			gen.initialize(2048);
			KeyPair pair = gen.generateKeyPair();
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, pair.getPublic());
			byte[] data = cipher.doFinal(text.getBytes());
			try(DataOutputStream out = new DataOutputStream(new FileOutputStream(file, false))){
				out.writeInt(data.length);
				out.write(data);
			}
			File key = new File(file.getParent(), "key.dat");
			try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(key, false))){
				out.writeObject(pair.getPrivate());
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public String read() {
		try(DataInputStream in = new DataInputStream(new FileInputStream(file))){
			int size = in.readInt();
			byte[] data = new byte[size];
			in.read(data);
			try(ObjectInputStream in2 = new ObjectInputStream(new FileInputStream(new File(file.getParentFile(), "key.dat")))){
				PrivateKey key = (PrivateKey) in2.readObject();
				Cipher cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.DECRYPT_MODE, key);
				return new String(cipher.doFinal(data));
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
}
