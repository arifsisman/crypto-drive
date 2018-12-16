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
    private static DirWatcher watcher;
    static {
        try {
            watcher = new DirWatcher(Path.of(CDPaths.CRYPTO_DRIVE_UPLOAD));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, GeneralSecurityException, InterruptedException {
        //Drive Service initialization
        DriveService.initialize();

        //Check local CryptoDrive directory exists, if not create
        Directory.checkDirectory();

        //Cipher initialize for encryption/decryption operations
        CipherOps cipher = new CipherOps();

        //Call monitor thread
        threadMethod();
        t.start();

        final String FILE_PATH = "C:\\Users\\musta\\IdeaProjects\\CryptoDrive\\src\\main\\resources\\files\\photo.jpg";
        final String FOLDER_PATH = "C:\\Users\\musta\\IdeaProjects\\CryptoDrive\\src\\main\\resources\\files\\Sunset Retro";

        //cipher.encrypt(FILE_PATH);
        //cipher.decrypt(CDPaths.CRYPTO_DRIVE_ENCRYPTED+"\\"+ Paths.get(FILE_PATH).getFileName()+".enc");

//        Zip zip = new Zip();
//        zip.generateFileList(new File(FOLDER_PATH),FOLDER_PATH);
//        zip.zipIt(FOLDER_PATH+".zip",FOLDER_PATH);
//        zip.unzipIt("C:\\Users\\musta\\IdeaProjects\\CryptoDrive\\src\\main\\resources\\files\\Sunset Retro.zip","C:\\Users\\musta\\IdeaProjects\\CryptoDrive\\src\\main\\resources\\files\\unzip");

//        cipher.encrypt(FOLDER_PATH+".zip");
//        cipher.decrypt(FOLDER_PATH+".zip.enc");



        // Now, the watcher runs in parallel
        // Do other stuff here

//        // Shutdown after 10 seconds
//        executor.awaitTermination(10, TimeUnit.SECONDS);
//        // abort watcher
//        future.cancel(true);
//
//        executor.awaitTermination(1, TimeUnit.SECONDS);
//        executor.shutdownNow();
        if(new Scanner(System.in).nextInt() == 0){
            t.join();
            future.cancel(true);
            System.exit(0);
        }

        //Monitor the directory for changes
        //MonitorDirectory.listen();

        //Search.searchFilesByName("", DriveService.folderId);
        //TextMenu menu = new TextMenu();
        //menu.MenuInit();
        //Search.searchFilesByName("cv");

        //Upload.file("src/main/resources/files/photo.jpg");
        //Upload.toFolder("1_RWxhLaZsBJdU9lxGu8vDGQUcHaEQN9P",File.CDPaths.get("src/main/resources/files/photo.jpg"));


        //List.getFileList(20);
        //List.folders(20);
        //Upload.file("src/main/resources/files/photo.jpg");
        //Download.listAndDownload(20);
        //Download.listItems(20);
        //Folder.insert(folderId, File.CDPaths.get("src/main/resources/files/photo.jpg"));
        //Search.searchFiles(folderId);
    }

    private static void threadMethod(){
        t = new Thread(()->{}) {
            public void run() {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                future = executor.submit(watcher);
                executor.shutdown();
            }
        };
    }

}
