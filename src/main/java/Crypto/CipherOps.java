package Crypto;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;

import static Drive.DriveService.folderId;

public class CipherOps {
    private static Cipher cipher;
    private static final String initVector = folderId.substring(0,16);

    public static void setCipherMode() throws NoSuchPaddingException, NoSuchAlgorithmException {
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    }

    public static byte[] encrypt(String filePath) throws BadPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, InvalidKeyException, KeyStoreException, UnrecoverableEntryException, NoSuchPaddingException, InvalidAlgorithmParameterException {
        Path path = Paths.get(filePath);
        SecretKey key = Key.generateKey();
        Key.setKey(key, FilenameUtils.getBaseName(filePath));
        //System.out.println(FilenameUtils.getBaseName(filePath));
        Key.storeKey();
        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
        SecretKeySpec skeySpec = new SecretKeySpec(key.getEncoded(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] plainText  = Files.readAllBytes(path);
        byte[] cipherText = cipher.doFinal(plainText);
        FileUtils.writeByteArrayToFile(new File("C:\\Users\\musta\\IdeaProjects\\CryptoDrive\\src\\main\\resources\\files\\"+ path.getFileName() +".enc"), cipherText);
        return cipherText;
    }

    public static byte[] decrypt(String filePath) throws BadPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, InvalidKeyException, UnrecoverableEntryException, KeyStoreException, NoSuchPaddingException {
        Path path = Paths.get(filePath);
        //System.out.println(FilenameUtils.getBaseName(FilenameUtils.getBaseName(filePath)));
        SecretKey key = Key.getKey(FilenameUtils.getBaseName(FilenameUtils.getBaseName(filePath)));

        try{
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getEncoded(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        byte[] plainText  = Files.readAllBytes(path);
        byte[] cipherText = cipher.doFinal(plainText);
        FileUtils.writeByteArrayToFile(new File("C:\\Users\\musta\\IdeaProjects\\CryptoDrive\\src\\main\\resources\\files\\"+"new"+FilenameUtils.getBaseName(filePath)), cipherText);
        //FileUtils.writeByteArrayToFile(new File("C:\\Users\\musta\\IdeaProjects\\CryptoDrive\\src\\main\\resources\\files\\photo1.jpg"), cipherText);
        return cipherText;
    }

}
