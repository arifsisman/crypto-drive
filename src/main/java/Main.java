import Crypto.CipherOps;
import Drive.DriveService;
import File.CDPaths;
import File.Zip;
import Monitor.DirWatcher;
import Monitor.Directory;
import org.apache.http.annotation.NotThreadSafe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Path;
import java.io.File;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Mustafa Sisman
 */
public class Main {
    private static Thread t;
    private static Future<?> future;
    public static void main(String[] args) throws IOException, GeneralSecurityException, InterruptedException {
        //Drive Service initialization
        DriveService.initialize();

        //Check local CryptoDrive directory exists, if not create
        Directory.checkDirectory();

        //Call monitor thread
        threadMethod();
        t.start();

        if(new Scanner(System.in).nextInt() == 0){
            t.join();
            future.cancel(true);
            System.exit(0);
        }
    }

    private static void threadMethod(){
        t = new Thread(()->{}) {
            public void run() {
                DirWatcher watcher = null;
                try {
                    watcher = new DirWatcher(Path.of(CDPaths.CRYPTO_DRIVE_UPLOAD));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ExecutorService executor = Executors.newSingleThreadExecutor();
                future = executor.submit(Objects.requireNonNull(watcher));
                executor.shutdown();
            }
        };
    }

}
