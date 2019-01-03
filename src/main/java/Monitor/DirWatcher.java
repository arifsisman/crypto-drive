package Monitor;

import Crypto.CipherOps;
import CryptoDrive.Constants;
import Drive.DriveService;
import Drive.Upload;
import File.Zip;
import Menu.SimpleMenu;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.sql.Time;
import java.time.LocalTime;

import static java.nio.file.StandardWatchEventKinds.*;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class DirWatcher implements Runnable {
    private final Path dir;
    private final WatchService watcher;
    private final WatchKey key;
    private CipherOps cipher = new CipherOps();
    @SuppressWarnings("unchecked")
    private static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }

    /**
     * Creates a WatchService and registers the given directory
     */
    public DirWatcher(Path dir) throws IOException {
        this.dir = dir;
        this.watcher = FileSystems.getDefault().newWatchService();
        this.key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
    }

    public void run() {
        try {
            for (;;) {
                // wait for key to be signalled
                WatchKey key = watcher.take();

                if (this.key != key) {
                    System.err.println("WatchKey not recognized!");
                    continue;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent<Path> ev = cast(event);
                    System.out.format("%s %s: %s\n", Time.valueOf(LocalTime.now()), ev.kind(), dir.resolve(ev.context()));
                    WatchEvent.Kind kind = event.kind();
                    if (StandardWatchEventKinds.ENTRY_CREATE.equals(kind)){
                        if(dir.resolve(ev.context()).toFile().isFile()){
                            cipher.encrypt(dir.resolve(ev.context()).toString());
                            Upload.toFolder(DriveService.folderId, Constants.CRYPTO_DRIVE_ENCRYPTED + File.separator +ev.context().getFileName()+".enc");
                            SimpleMenu.display();
                        }
                        else{
                            String folderPath = Constants.CRYPTO_DRIVE_UPLOAD+File.separator+ev.context();
                            Zip zip = new Zip();
                            //wait thread to complete generate file lists for folder
                            await().atMost(20, SECONDS).until(() -> zip.generateFileListHelper(new File(folderPath),folderPath));
                            zip.zipIt(folderPath, folderPath);
                            cipher.encrypt(folderPath+".zip");
                            //Note to myself-> This method call is not needed because polling thread detects and uploads the .zip file already!
                            //Upload.toFolder(DriveService.folderId, encZipFolderPath);
                        }
                    }
//                    if (StandardWatchEventKinds.ENTRY_DELETE.equals(kind)){
//                    }
//                    if (StandardWatchEventKinds.ENTRY_MODIFY.equals(kind)){
//                    }
                }

                // reset key
                if (!key.reset()) {
                    break;
                }
            }
        }catch (NoSuchAlgorithmException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException |
                IOException | IllegalBlockSizeException | CertificateException | InterruptedException | KeyStoreException e) {
            e.printStackTrace();
        }
    }
}