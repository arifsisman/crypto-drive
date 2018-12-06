package Drive;

import com.google.api.services.drive.model.File;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import static Drive.DriveService.service;

public class Download {
    public static void listAndDownload(int listSize){
        int index = 0;
        Scanner sc = new Scanner(System.in);
        java.util.List<File> files = List.listFiles(listSize);
        System.out.println("Please enter the number of the file to be downloaded: ");
        if(sc.hasNextInt()){
            index = sc.nextInt();
            if(index>listSize-1){
                System.out.println("The file number must be less than the list size.");
                index = 0;
            }
        }
        sc.close();
        String fileId =  files.get(index).getId();
        String fileName = files.get(index).getName();
        System.out.println(fileName+" is downloading.");
        downloadFile(fileName, fileId);
    }

    public static File downloadFile(String fileName, String fileId){
        try{
        FileOutputStream fos = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        service.files().get(fileId)
                .executeMediaAndDownloadTo(outputStream);
        try {
            fos = new FileOutputStream(new java.io.File("src/main/resources/files/"+fileName));
            outputStream.writeTo(fos);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } finally {
            fos.close();
            System.out.println("Download finished.");}
        }
        catch(IOException e){
            System.out.println("An error occurred. Exception:"+e.getMessage());
        }
        return new File();
    }
}
