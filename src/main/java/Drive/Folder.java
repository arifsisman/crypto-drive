package Drive;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import static Drive.DriveService.service;

public class Folder {
    public static String create(String folderName) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(folderName);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");

        File file = service.files().create(fileMetadata)
                .setFields("id")
                .execute();
        System.out.println("Created folder ID: " + file.getId());
        return file.getId();
    }

    public static String insert(String folderId, Path path) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(path.getFileName().toString());
        fileMetadata.setParents(Collections.singletonList(folderId));
        java.io.File filePath = new java.io.File(path.toString());
        //Get mime type and fileName from path
        Path pathObj = Paths.get(path.toString());
        String mime = Files.probeContentType(pathObj);
        FileContent mediaContent = new FileContent(mime, filePath);
        File file = service.files().create(fileMetadata, mediaContent)
                .setFields("id, parents")
                .execute();
        System.out.println("Inserted file ID: " + file.getId());
        return file.getId();
    }
}
