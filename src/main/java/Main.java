import Drive.Download;
import Drive.List;
import Drive.Upload;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static Drive.Drive.DriveInit;

public class Main {
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        DriveInit();
        //List.getFileList(10);
        //String filePath = "src/main/resources/files/photo.jpg";
        //Upload.withPath(filePath);
        //String fileId = Download.listItems(20);
        //Download.downloadFile(fileId);
        Download.listItems(20);
    }
}
