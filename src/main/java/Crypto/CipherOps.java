package Crypto;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.nio.charset.StandardCharsets;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;

import static Drive.DriveService.folderId;
import File.CDPaths;

/**
 * @author Mustafa Sisman
 */
public class CipherOps {
    private static final String initVector = folderId.substring(0,16);
    private static byte[] initVecBytes;
    private static Key myKey;
    private static Cipher cipher;
    static {
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            initVecBytes = initVector.getBytes(StandardCharsets.UTF_8.name());
            myKey = new Key();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | KeyStoreException | IOException e) {
            e.printStackTrace();
        }
    }

    public CipherOps() {
        //Bouncy Castle provider for crypto operations
        Security.addProvider(new BouncyCastleProvider());
    }

    public byte[] encrypt(String filePath) throws BadPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, InvalidKeyException, KeyStoreException, InvalidAlgorithmParameterException, CertificateException {
        Path path = Paths.get(filePath);
        SecretKey key = myKey.generateKey();
        myKey.setKey(key, FilenameUtils.getBaseName(filePath));
        myKey.storeKey();
        IvParameterSpec iv = new IvParameterSpec(initVecBytes);
        SecretKeySpec skeySpec = new SecretKeySpec(key.getEncoded(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] plainText  = Files.readAllBytes(path);
        byte[] cipherText = cipher.doFinal(plainText);
        toFile(CDPaths.CRYPTO_DRIVE_ENCRYPTED,path.getFileName()+".enc", cipherText);
        return cipherText;
    }

    public byte[] decrypt(String filePath) throws BadPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, InvalidKeyException, UnrecoverableEntryException, KeyStoreException{
        Path path = Paths.get(filePath);
        String fileName = FilenameUtils.getBaseName(filePath);
        SecretKey key = myKey.getKey(FilenameUtils.getBaseName(fileName));
        try{
            IvParameterSpec iv = new IvParameterSpec(initVecBytes);
            SecretKeySpec skeySpec = new SecretKeySpec(key.getEncoded(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        byte[] cipherText  = Files.readAllBytes(path);
        byte[] plainText = cipher.doFinal(cipherText);
        toFile(CDPaths.CRYPTO_DRIVE_DOWNLOAD, fileName, plainText);
        return plainText;
    }

    private void toFile(String directory,String fileName,byte[] content) throws IOException {
        FileUtils.writeByteArrayToFile(new File(directory+ File.separator +fileName), content);
    }

}
