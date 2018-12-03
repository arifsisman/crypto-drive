package Drive;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Upload {
    public static void Upload(String filePath) throws IOException{
        //Get mime type and fileName from path
        Path path = Paths.get(filePath);
        String mime = Files.probeContentType(path);
        System.out.println("File is uploading. Please wait...");
        //Upload
        UploadFile(path.getFileName().toString(), new java.io.File(filePath),mime);
    }

    static String UploadFile(String fileName, java.io.File filePath, String type) throws IOException{
        File fileMetadata = new File();
        fileMetadata.setName(fileName);
        FileContent mediaContent = new FileContent(type, filePath);
        File file = Drive.service.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();
        return "File ID: " + file.getId();
    }
}
