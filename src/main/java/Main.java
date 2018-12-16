import Crypto.CipherOps;
import Drive.DriveService;
import File.Zip;
import Monitor.DirWatcher;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Mustafa Sisman
 */
public class Main {
    public static String folderId;
    public static void main(String[] args) throws IOException, GeneralSecurityException, InterruptedException {
        //Drive Service initialization
        DriveService.initialize();

        Thread t = new Thread() {
            public void run() {
                DirWatcher watcher = null;
                try {
                    watcher = new DirWatcher(Path.of(System.getProperty("user.home")+"\\"+"CryptoDrive"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Future<?> future = executor.submit(watcher);
                executor.shutdown();
            }
        };
        t.start();

        final String FILE_PATH = "C:\\Users\\musta\\IdeaProjects\\CryptoDrive\\src\\main\\resources\\files\\photo.jpg";
        final String FOLDER_PATH = "C:\\Users\\musta\\IdeaProjects\\CryptoDrive\\src\\main\\resources\\files\\Sunset Retro";

        //Cipher initialize for encryption/decryption operations
        CipherOps cipher = new CipherOps();
        //cipher.encrypt(FILE_PATH);
        //cipher.decrypt(FILE_PATH+".enc");

        Zip zip = new Zip();
        zip.generateFileList(new File(FOLDER_PATH));
        zip.zipIt(FOLDER_PATH+".zip");

        cipher.encrypt(FOLDER_PATH+".zip");
        cipher.decrypt(FOLDER_PATH+".zip.enc");

        //System.out.println("operation completed");
        t.join();

        // Now, the watcher runs in parallel
        // Do other stuff here

//        // Shutdown after 10 seconds
//        executor.awaitTermination(10, TimeUnit.SECONDS);
//        // abort watcher
//        future.cancel(true);
//
//        executor.awaitTermination(1, TimeUnit.SECONDS);
//        executor.shutdownNow();
        Scanner sc = new Scanner(System.in);
        if(sc.nextInt()==0)
            System.exit(0);

        //Monitor the directory for changes
        //MonitorDirectory.listen();

        //Search.searchFilesByName("", DriveService.folderId);
        //TextMenu menu = new TextMenu();
        //menu.MenuInit();
        //Search.searchFilesByName("cv");

        //List.getFileList(10);
        //Upload.uploadFile(Paths.get("src/main/resources/files/photo.jpg"));
        //Upload.toFolder("1_RWxhLaZsBJdU9lxGu8vDGQUcHaEQN9P",Paths.get("src/main/resources/files/photo.jpg"));


        //List.getFileList(20);
        //List.folders(20);
        //Upload.file("src/main/resources/files/photo.jpg");
        //Download.listAndDownload(20);
        //Download.listItems(20);
        //Folder.insert(folderId, Paths.get("src/main/resources/files/photo.jpg"));
        //Search.searchFiles(folderId);
    }

}
