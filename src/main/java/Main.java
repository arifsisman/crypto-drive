import Drive.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import static Drive.DriveService.DriveServiceInit;

public class Main {
    public static String folderId;
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        DriveServiceInit();
        //List.getFileList(10);
        //String filePath = "src/main/resources/files/photo.jpg";
        //Upload.withPath(filePath);
        //Download.listAndDownload(20);
        //Download.listItems(20);
        //Folder.insert("photo.jpg", folderId, "src/main/resources/files/photo.jpg");
        folderId = Search.searchFolder("CryptoDrive");
        if(folderId==null){//if there  is no folder named "CryptoDrive" then create new folder.
            Folder.create("CryptoDrive");
            folderId = Search.searchFolder("CryptoDrive");
        }
        Folder.insert(folderId, Paths.get("src/main/resources/files/photo.jpg"));
        Search.searchFiles(folderId);
    }
}
