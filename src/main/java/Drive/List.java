package Drive;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import java.io.IOException;
import static Drive.DriveService.service;

public class List {
    public static java.util.List<File> getFileList(int size) throws IOException {
        int counter = 0;
        // Print the names and IDs.
        FileList result = service.files().list()
                .setPageSize(size)
                .setFields("nextPageToken, files(id, name)")
                .execute();
        java.util.List<File> files = result.getFiles();
        if (files == null || files.isEmpty()) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : files) {
                System.out.printf("%d) %s (%s)\n", counter, file.getName(), file.getId());
                counter++;
            }
        }
        return files;
    }
}
