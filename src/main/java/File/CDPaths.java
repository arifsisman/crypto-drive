package File;

import java.io.File;
import java.nio.file.Path;

/**
 * @author Mustafa Sisman
 */
public class CDPaths {
    public final static String home = System.getProperty("user.home");
    public final static String CRYPTO_DRIVE = home + File.separator + "CryptoDrive";
    public final static String CRYPTO_DRIVE_ENCRYPTED = CRYPTO_DRIVE + File.separator + ".Encrypted";
    public final static String CRYPTO_DRIVE_UPLOAD = CRYPTO_DRIVE + File.separator + "Upload";
    public final static String CRYPTO_DRIVE_DOWNLOAD = CRYPTO_DRIVE + File.separator + "Download";

    public final static Path CRYPTO_DRIVE_PATH = Path.of(CRYPTO_DRIVE);
    public final static Path CRYPTO_DRIVE_ENCRYPTED_PATH = Path.of(CRYPTO_DRIVE_ENCRYPTED);
    public final static Path CRYPTO_DRIVE_UPLOAD_PATH = Path.of(CRYPTO_DRIVE_UPLOAD);
    public final static Path CRYPTO_DRIVE_DOWNLOAD_PATH = Path.of(CRYPTO_DRIVE_DOWNLOAD);
}
