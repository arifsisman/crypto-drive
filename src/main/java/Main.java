import Drive.List;
import Drive.Upload;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static Drive.Drive.DriveInit;

public class Main {
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        com.google.api.services.drive.Drive service = DriveInit();
        List.listFiles(service);
        String filePath = "C:\\Users\\musta\\IdeaProjects\\CryptoDrive\\src\\main\\resources\\files\\photo.jpg";
        Upload.withPath(filePath);
    }
}
