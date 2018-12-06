package Drive;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import java.io.IOException;
import static Drive.DriveService.service;

public class Search {

    public static void searchFiles(String folderId) throws IOException {
        String pageToken = null;
        do {
            FileList result = service.files().list()
                    .setQ("'"+folderId+"' in parents")
                    .setSpaces("drive")
                    .setFields("nextPageToken, files(id, name)")
                    .setPageToken(pageToken)
                    .execute();
            for (File file : result.getFiles()) {
                System.out.printf("Found file: %s (%s)\n",
                        file.getName(), file.getId());
            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null);

    }
}
