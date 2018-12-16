package Drive;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.IOException;

import static Drive.DriveService.service;

/**
 * @author Mustafa Sisman
 */
public class List {
    static java.util.List<File> listFiles(int size){
        int counter = 0;
        // Print the file names and IDs.
        FileList result = null;
        try {
            result = service.files().list()
                    .setQ("mimeType != 'application/vnd.google-apps.folder'")
                    .setPageSize(size)
                    .setFields("nextPageToken, files(id, name)")
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert result != null;
        java.util.List<File> files = result.getFiles();
        if (files == null || files.isEmpty()) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : files) {
                System.out.printf("%d)%s (%s)\n", counter, file.getName(), file.getId());
                counter++;
            }
        }
        return files;
    }

    public static java.util.List<File> listFolders(int size){
        int counter = 0;
        // Print the folder names and IDs.
        FileList result = null;
        try {
            result = service.files().list()
                    .setQ("mimeType = 'application/vnd.google-apps.folder'")
                    .setPageSize(size)
                    .setFields("nextPageToken, files(id, name)")
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert result != null;
        java.util.List<File> files = result.getFiles();
        if (files == null || files.isEmpty()) {
            System.out.println("No folders found.");
        } else {
            System.out.println("Folders:");
            for (File file : files) {
                System.out.printf("%d)%s (%s)\n", counter, file.getName(), file.getId());
                counter++;
            }
        }
        return files;
    }
}
