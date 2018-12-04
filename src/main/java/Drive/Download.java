package Drive;
import com.google.api.services.drive.model.File;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

public class Download {
    public static void listAndDownload(int listSize) throws IOException {
        int index = 0;
        Scanner sc = new Scanner(System.in);
        java.util.List<File> files = List.getFileList(listSize);
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

    public static File downloadFile(String fileName, String fileId) throws IOException {
        FileOutputStream fos = null;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Drive.service.files().get(fileId)
                .executeMediaAndDownloadTo(outputStream);

        try {
            fos = new FileOutputStream(new java.io.File("src/main/resources/files/"+fileName));
            outputStream.writeTo(fos);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } finally {
            fos.close();
            System.out.println("Done");
        }


        return new File();
    }
}
