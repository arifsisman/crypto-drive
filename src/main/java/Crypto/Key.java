package Crypto;

import org.bouncycastle.util.encoders.Hex;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * @author Mustafa Sisman
 */
public class Key {
    private final String KEYSTORE_PATH = "tokens/keystore.jks";
    private final char[] KEYSTORE_PASSWORD = "CryptoDrive".toCharArray();
    static KeyStore keyStore;

    public Key() throws KeyStoreException, IOException {
        keyStore = KeyStore.getInstance("JCEKS");
        File f = new File(KEYSTORE_PATH);
        if(!f.exists()){
            f.getParentFile().mkdir();
            f.createNewFile();
        }
        loadKey();
    }

    SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256, new SecureRandom());
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey;
    }

    void storeKey() throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException {
        try (FileOutputStream keyStoreOutputStream = new FileOutputStream(KEYSTORE_PATH)) {
            keyStore.store(keyStoreOutputStream, KEYSTORE_PASSWORD);
        }
    }

    private void loadKey(){
        try(InputStream keyStoreData = new FileInputStream(KEYSTORE_PATH)){
            keyStore.load(keyStoreData, KEYSTORE_PASSWORD);
        }
         catch (FileNotFoundException e){
             e.printStackTrace();
        }catch (NoSuchAlgorithmException | CertificateException e){
            e.printStackTrace();
        }catch (IOException e) {
            System.out.println("KeyStore file created.");
        }
    }

    SecretKey getKey(String fileName) throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException {
        KeyStore.ProtectionParameter entryPassword =
                new KeyStore.PasswordProtection(KEYSTORE_PASSWORD);

        KeyStore.SecretKeyEntry keyEntry = (KeyStore.SecretKeyEntry)keyStore.getEntry(hash(fileName), entryPassword);
        return keyEntry.getSecretKey();
    }

    void setKey(SecretKey key, String fileName) throws KeyStoreException, NoSuchAlgorithmException {
        KeyStore.ProtectionParameter entryPassword =
                new KeyStore.PasswordProtection(KEYSTORE_PASSWORD);

        KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(key);
        keyStore.setEntry(hash(fileName), secretKeyEntry, entryPassword);
    }

    private static String hash(String fileName) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(
                fileName.getBytes(StandardCharsets.UTF_8));
        return new String(Hex.encode(hash));
    }
}
