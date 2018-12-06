package Drive;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import java.io.IOException;
import static Drive.DriveService.service;

public class Search {
    public static void searchFiles(String fileId) throws IOException {
        String pageToken = null;
        try{
        do {
            FileList result = service.files().list()
                    .setQ("'"+fileId+"' in parents")
                    .setSpaces("drive")
                    .setFields("nextPageToken, files(id, name)")
                    .setPageToken(pageToken)
                    .execute();
            for (File file : result.getFiles()) {
                System.out.printf("Found file: %s (%s)\n",
                        file.getName(), file.getId());
            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null);}
        catch (IOException e){
            System.out.println("No files found.");
        }
    }

    public static String searchFolder(String folderName) throws IOException {
        boolean hasFolder = false;
        String folderId = null;
        String pageToken = null;
        try{
            do {
            FileList result = service.files().list()
                    .setQ("name = '"+folderName+"'")
                    .setSpaces("drive")
                    .setFields("nextPageToken, files(id, name)")
                    .setPageToken(pageToken)
                    .execute();
            for (File file : result.getFiles()) {
                //CryptoDrive folder found
                if(!hasFolder){
                    hasFolder = true;
                    folderId=file.getId();}
                System.out.printf("Found folder: %s (%s)\n",
                        file.getName(), file.getId());
            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null);}
        catch (IOException e){
            System.out.println("No folders found.");
        }
        return folderId;
    }
}
