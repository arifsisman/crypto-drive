package Drive;

import com.google.api.services.drive.model.File;

import java.io.IOException;

import static Drive.DriveService.service;

public class Folder {
    public static String create(String folderName){
        File fileMetadata = new File();
        fileMetadata.setName(folderName);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");
        File file = null;
        try {
            file = service.files().create(fileMetadata)
                    .setFields("id")
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Created folder ID: " + file.getId());
        return file.getId();
    }
}
