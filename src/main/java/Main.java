import Drive.*;
import org.graalvm.compiler.api.replacements.Fold;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static Drive.Drive.DriveInit;

public class Main {
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        com.google.api.services.drive.Drive service = DriveInit();
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
