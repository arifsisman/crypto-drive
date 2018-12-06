package Drive;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.IOException;

import static Drive.DriveService.service;

public class Search {
    public static void searchFilesById(String fileId){
        String pageToken = null;
        try{
        do {
            FileList result = service.files().list()
                    .setQ("mimeType != 'application/vnd.google-apps.folder'")
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

    public static void searchFilesByName(String fileName,String folderId){
        String pageToken = null;
        try{
            do {
                FileList result = service.files().list()
                        .setQ("mimeType != 'application/vnd.google-apps.folder'")
                        .setQ("name contains '"+fileName+"'")
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
            } while (pageToken != null);}
        catch (IOException e){
            System.out.println("No files found.");
        }
    }

    public static String searchFolder(String folderName){
        boolean hasFolder = false;
        String folderId = null;
        String pageToken = null;
        try{
            do {
            FileList result = service.files().list()
                    .setQ("mimeType = 'application/vnd.google-apps.folder'")
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
