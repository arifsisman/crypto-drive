import Drive.DriveService;
import Monitor.MonitorDirectory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;

public class Main {
    public static String folderId;
    public static void main(String[] args) throws IOException, GeneralSecurityException, InterruptedException {
        //Drive Service initialization
        DriveService.initialize();
        //Bouncy Castle provider for crypto operations
        Security.addProvider(new BouncyCastleProvider());
        //Monitor the directory for changes
        MonitorDirectory.listen();




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
