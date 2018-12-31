package Monitor;

import CryptoDrive.Constants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author Mustafa Sisman
 */
public class Directory {
    private static ArrayList<Path> pathList = new ArrayList<>();
    public static void checkDirectory() throws IOException {
        Path cryptoFolderPath = Paths.get(Constants.CRYPTO_DRIVE);
        pathList.add(Paths.get(Constants.CRYPTO_DRIVE_UPLOAD));
        pathList.add(Paths.get(Constants.CRYPTO_DRIVE_DOWNLOAD));
        pathList.add(Paths.get(Constants.CRYPTO_DRIVE_ENCRYPTED));

        if(Files.exists(cryptoFolderPath)){
            for(Path p : pathList)
            if(Files.exists(p)){
                System.out.println(p.getFileName()+" folder is using at "+cryptoFolderPath);
            }
            else{
                createFolder(p);
            }
        }
        else{
            createFolder(cryptoFolderPath);
            checkDirectory();
        }
    }

    private static void createFolder(Path path) throws IOException {
        File f = new File(path.toUri());
        f.mkdir();
        f.createNewFile();
        //System.out.println(f.getName()+" created at "+f.getAbsolutePath());
    }
}
