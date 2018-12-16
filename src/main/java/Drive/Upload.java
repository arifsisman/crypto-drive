package Drive;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import static Drive.DriveService.service;

/**
 * @author Mustafa Sisman
 */
public class Upload {
    public static String file(String filePath){
        try{
            Path realPath = Paths.get(filePath);
            //Get mime type and fileName from path
            String mime = Files.probeContentType(realPath);
            System.out.println("File is uploading. Please wait...");
            //Upload
            File fileMetadata = new File();
            fileMetadata.setName(realPath.getFileName().toString());
            FileContent mediaContent = new FileContent(mime, realPath.toFile());
            File file = service.files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();
            System.out.println("File upload completed.");
            return "File ID: " + file.getId();
        }
        catch (IOException e){
        System.out.println("An error occurred while uploading file. "+e.getMessage());}
        return null;
    }

    public static void toFolder(String folderId, String filePath){
        try{
            Path realPath = Paths.get(filePath);
            File fileMetadata = new File();
            fileMetadata.setName(realPath.getFileName().toString());
            fileMetadata.setParents(Collections.singletonList(folderId));
            //Get mime type and fileName from path
            String mime = Files.probeContentType(realPath);
            FileContent mediaContent = new FileContent(mime, realPath.toFile());
            File file = service.files().create(fileMetadata, mediaContent)
                    .setFields("id, parents")
                    .execute();
            System.out.println("Inserted file ID: " + file.getId());
        }
        catch (IOException e){
            System.out.println("An error occurred while uploading file. "+e.getMessage());}
    }
}
