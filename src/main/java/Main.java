import Drive.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import static Drive.DriveService.DriveServiceInit;

public class Main {
    private static com.google.api.services.drive.Drive service;
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        DriveServiceInit();
        //List.getFileList(10);
        //String filePath = "src/main/resources/files/photo.jpg";
        //Upload.withPath(filePath);
        //Download.listAndDownload(20);
        //Download.listItems(20);
        //String folderId = Folder.create("CryptoDrive");
        //Folder.insert("photo.jpg", folderId, "src/main/resources/files/photo.jpg");
        Search.searchFiles("1e193miK4BZO191OWHHQaHzzq5zxXF_YX");
    }
}
