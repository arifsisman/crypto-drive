package Drive;

import Menu.SimpleMenu;
import com.google.api.services.drive.model.File;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import static Drive.DriveService.service;
import File.CDPaths;

/**
 * @author Mustafa Sisman
 */
public class Download {
    public static void listAndDownload(int listSize) throws IOException{
        int index = -2;
        Scanner sc = new Scanner(System.in);
        java.util.List<File> files = List.listFiles(listSize);
        System.out.println("Please enter the number of the file to be downloaded: ");
        if(sc.hasNextInt()){
            index = sc.nextInt();
            if(index==0)
                SimpleMenu.listen();
            if(index>listSize-1){
                System.out.println("The file number must be less than the list size.");
                index = -2;
            }
        }
        String fileId =  files.get(index-1).getId();
        String fileName = files.get(index-1).getName();
        System.out.println(fileName+" is downloading.");
        downloadFile(fileName, fileId);
    }

    private static void downloadFile(String fileName, String fileId){
        try{
        FileOutputStream fos = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        service.files().get(fileId)
                .executeMediaAndDownloadTo(outputStream);
        try {
            fos = new FileOutputStream(new java.io.File( CDPaths.CRYPTO_DRIVE_ENCRYPTED + java.io.File.separator + fileName));
            outputStream.writeTo(fos);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } finally {
            assert fos != null;
            fos.close();
            System.out.println("Download finished.");
        }
        }
        catch(IOException e){
            System.out.println("An error occurred. Exception:"+e.getMessage());
        }
    }
}
