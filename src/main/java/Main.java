import java.io.IOException;
import java.security.GeneralSecurityException;

import static Drive.Drive.DriveInit;
import static Drive.Upload.Upload;

public class Main {
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        DriveInit();
        String filePath = "C:\\Users\\musta\\IdeaProjects\\drive2crypto\\src\\main\\resources\\files\\photo.jpg";
        Upload(filePath);
    }
}
