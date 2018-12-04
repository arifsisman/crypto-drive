import Drive.Download;
import Drive.Folder;
import Drive.List;
import Drive.Upload;
import org.graalvm.compiler.api.replacements.Fold;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static Drive.Drive.DriveInit;

public class Main {
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        DriveInit();
        //List.getFileList(10);
        //String filePath = "src/main/resources/files/photo.jpg";
        //Upload.withPath(filePath);
        Download.listAndDownload(20);
        //Download.listItems(20);
        //String folderId = Folder.create("Test folder");
        //Folder.insert("photo.jpg", folderId, "src/main/resources/files/photo.jpg");
    }
}
