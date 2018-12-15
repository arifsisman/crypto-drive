package Crypto;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class Key {
    static KeyStore keyStore;
    static char[] keyStorePassword = "CryptoDrive".toCharArray();

    public static void keyStoreInit() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        keyStore = KeyStore.getInstance("JCEKS");
        File f = new File("tokens/keystore.jks");
        if(!f.exists()){
            f.getParentFile().mkdir();
            f.createNewFile();
        }
        keyStore.load(null, keyStorePassword);
    }

    static SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

        SecureRandom secureRandom = new SecureRandom();
        keyGenerator.init(256, secureRandom);

        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey;
    }

    public static void keyStoreLoad() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        try(InputStream keyStoreData = new FileInputStream("tokens/keystore.jks")){
            keyStore.load(keyStoreData, keyStorePassword);
        }
         catch (FileNotFoundException e){
             e.printStackTrace();
        }catch (NoSuchAlgorithmException | CertificateException e){
            e.printStackTrace();
        }catch (IOException e) {
            System.out.println("KeyStore file created.");
        }
    }

    static void storeKey(){
        try (FileOutputStream keyStoreOutputStream = new FileOutputStream("tokens/keystore.jks")) {
            keyStore.store(keyStoreOutputStream, keyStorePassword);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    static SecretKey getKey(String keyAlias) throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException {
        KeyStore.ProtectionParameter entryPassword =
                new KeyStore.PasswordProtection(keyStorePassword);

        KeyStore.SecretKeyEntry keyEntry = (KeyStore.SecretKeyEntry)keyStore.getEntry(keyAlias, entryPassword);
        //Certificate google = keyStore.getCertificate("google.com");
        return keyEntry.getSecretKey();
    }

    static void setKey(SecretKey key) throws KeyStoreException {
        KeyStore.ProtectionParameter entryPassword =
                new KeyStore.PasswordProtection(keyStorePassword);

        KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(key);


        keyStore.setEntry("keyAlias", secretKeyEntry, entryPassword);
        //keyStore.setCertificateEntry("google.com", trustedCertificate);
    }
}
