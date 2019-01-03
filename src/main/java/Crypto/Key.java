package Crypto;

import CryptoDrive.Constants;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * @author Mustafa Sisman
 */
class Key {
    private final char[] KEYSTORE_PASSWORD = "CryptoDrive".toCharArray();
    private static KeyStore keyStore;

    Key() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        keyStore = KeyStore.getInstance("JCEKS");
        File f = new File(Constants.KEYSTORE_PATH);
        if(!f.exists()){
            f.getParentFile().mkdir();
            f.createNewFile();
            keyStore.load(null, KEYSTORE_PASSWORD);
        }
        else{
            if(f.length()==0)
                keyStore.load(null, KEYSTORE_PASSWORD);
            else
                keyStore.load(new FileInputStream(Constants.KEYSTORE_PATH), KEYSTORE_PASSWORD);
        }
    }

    SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256, new SecureRandom());
        return keyGenerator.generateKey();
    }

    void storeKey() throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException {
        try (FileOutputStream keyStoreOutputStream = new FileOutputStream(Constants.KEYSTORE_PATH)) {
            keyStore.store(keyStoreOutputStream, KEYSTORE_PASSWORD);
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
        return DigestUtils.shaHex(hash);
    }
}
