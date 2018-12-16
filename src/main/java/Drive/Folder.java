package Drive;

import com.google.api.services.drive.model.File;

import java.io.IOException;

import static Drive.DriveService.service;

/**
 * @author Mustafa Sisman
 */
class Folder {
    static void create(){
        File fileMetadata = new File();
        fileMetadata.setName("CryptoDrive");
        fileMetadata.setMimeType("application/vnd.google-apps.folder");
        File file = null;
        try {
            file = service.files().create(fileMetadata)
                    .setFields("id")
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert file != null;
        System.out.println("Created folder ID: " + file.getId());
    }
}
