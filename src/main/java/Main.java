import Drive.DriveService;
import File.CDPaths;
import Menu.SimpleMenu;
import Monitor.DirWatcher;
import Monitor.Directory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Mustafa Sisman
 */
public class Main {
    private static Thread t;
    private static Future<?> future;
    static SimpleMenu menu;
    public static void main(String[] args) throws IOException, GeneralSecurityException, InterruptedException {
        //Drive Service initialization
        DriveService.initialize();

        //Check local CryptoDrive directory exists, if not create
        Directory.checkDirectory();

        //Call monitor thread
        threadMethod();
        t.start();

        //Init and display menu
        menu = new SimpleMenu();
        menu.listen();

        t.join();
        future.cancel(true);
    }

    private static void threadMethod(){
        t = new Thread(()->{}) {
            public void run() {
                DirWatcher watcher = null;
                try {
                    watcher = new DirWatcher(CDPaths.CRYPTO_DRIVE_UPLOAD_PATH);
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
