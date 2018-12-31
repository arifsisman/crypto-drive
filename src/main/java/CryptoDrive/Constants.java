package CryptoDrive;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Mustafa Sisman
 */
public class Constants {
    public final static String home = System.getProperty("user.home");
    public final static String CRYPTO_DRIVE = home + File.separator + "CryptoDrive";
    public final static String CRYPTO_DRIVE_ENCRYPTED = CRYPTO_DRIVE + File.separator + ".Encrypted";
    public final static String CRYPTO_DRIVE_UPLOAD = CRYPTO_DRIVE + File.separator + "Upload";
    public final static String CRYPTO_DRIVE_DOWNLOAD = CRYPTO_DRIVE + File.separator + "Download";

    public final static Path CRYPTO_DRIVE_PATH = Paths.get(CRYPTO_DRIVE);
    public final static Path CRYPTO_DRIVE_ENCRYPTED_PATH = Paths.get(CRYPTO_DRIVE_ENCRYPTED);
    public final static Path CRYPTO_DRIVE_UPLOAD_PATH = Paths.get(CRYPTO_DRIVE_UPLOAD);
    public final static Path CRYPTO_DRIVE_DOWNLOAD_PATH = Paths.get(CRYPTO_DRIVE_DOWNLOAD);

    public final static String KEYSTORE_PATH = "src/main/resources/keystore.jks";
    public static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    public static final String RESOURCES_DIRECTORY_PATH = "src/main/resources";
}
