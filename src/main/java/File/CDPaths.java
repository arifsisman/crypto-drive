package File;

import java.io.File;

/**
 * @author Mustafa Sisman
 */
public class CDPaths {
    public final static String home = System.getProperty("user.home");
    public final static String CRYPTO_DRIVE = home + File.separator + "CryptoDrive";
    public final static String CRYPTO_DRIVE_ENCRYPTED = CRYPTO_DRIVE + File.separator + ".Encrypted";
    public final static String CRYPTO_DRIVE_UPLOAD = CRYPTO_DRIVE + File.separator + "Upload";
    public final static String CRYPTO_DRIVE_DOWNLOAD = CRYPTO_DRIVE + File.separator + "Download";
}
