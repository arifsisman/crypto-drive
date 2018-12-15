import Crypto.CipherOps;
import Crypto.Key;
import Crypto.Provider;
import Drive.DriveService;
import Monitor.MonitorDirectory;

import java.io.IOException;
import java.security.GeneralSecurityException;


public class Main {
    public static String folderId;
    public static void main(String[] args) throws IOException, GeneralSecurityException, InterruptedException {
        //Drive Service initialization
        DriveService.initialize();

        //Bouncy Castle provider for crypto operations
        Provider.addProvider();

        //Key Store initialization
        Key.keyStoreInit();
        Key.keyStoreLoad();
        //Cipher initialize for encryption/decryption operations
        CipherOps.setCipherMode();
        CipherOps.encrypt("C:\\Users\\musta\\IdeaProjects\\CryptoDrive\\src\\main\\resources\\files\\photo.jpg");
        CipherOps.decrypt("C:\\Users\\musta\\IdeaProjects\\CryptoDrive\\src\\main\\resources\\files\\photo.jpg.enc");


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
