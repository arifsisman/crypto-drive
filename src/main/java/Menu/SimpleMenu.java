package Menu;

import Crypto.CipherOps;
import Drive.Download;
import Drive.DriveService;
import File.CDPaths;
import File.Zip;
import Monitor.Directory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Mustafa Sisman
 */
public class SimpleMenu {
    private static CipherOps cipher = new CipherOps();
    static List<Item> itemList = new ArrayList<>();
    private static Scanner s = new Scanner(System.in);
    private static int choice;

    public class Item{
        String itemId;
        String itemName;
        boolean subMenu;
        Item parent;

        public Item(String itemId, String itemName, boolean subMenu, Item parent) {
            this.itemId = itemId;
            this.itemName = itemName;
            this.subMenu = subMenu;
            this.parent = parent;
        }

        public Item(String itemId, String itemName) {
            this.itemId = itemId;
            this.itemName = itemName;
        }
    }
    public SimpleMenu(){
        itemList.add(new Item("1","Upload"));
        itemList.add(new Item("2","Download"));
        itemList.add(new Item("3","Delete Local Files"));
        itemList.add(new Item("4","Delete Remote Files"));
        itemList.add(new Item("5","Exit"));
    }

    public static void display(){
        System.out.println("\n-----CryptoDrive-----");
        for (Item i: itemList){
            System.out.println(i.itemId+") "+i.itemName);
        }
        System.out.println("---------------------");
    }

    public static void listen() throws IOException {
        do{
            display();
            choice = s.nextInt();
            switch (choice){
                case 1:
                    System.out.println("Drag&Drop files into "+CDPaths.CRYPTO_DRIVE_UPLOAD);
                    break;
                case 2:
                    String fileName = Download.listAndDownload(20);
                    try {
                        cipher.decrypt(CDPaths.CRYPTO_DRIVE_ENCRYPTED + File.separator + fileName);
                        String filePath = CDPaths.CRYPTO_DRIVE_DOWNLOAD + File.separator + FilenameUtils.getBaseName(fileName);
                        RandomAccessFile raf = new RandomAccessFile(new File(filePath), "r");
                        long n = raf.readInt();
                        raf.close();
                        //if zip file
                        if (n == 0x504B0304){
                            Zip.unzipIt(filePath,CDPaths.CRYPTO_DRIVE_DOWNLOAD + File.separator + FilenameUtils.getBaseName(FilenameUtils.getBaseName(fileName)));
                            //delete temp .zip
                            new File(filePath).delete();
                        }
                    } catch (BadPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException
                            | InvalidKeyException | UnrecoverableEntryException | KeyStoreException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    System.out.println("Do you want to delete local files ? <y/n>");
                    if(s.next().charAt(0)=='y'){
                        FileUtils.deleteDirectory(new File(CDPaths.CRYPTO_DRIVE));
                        Directory.checkDirectory();
                    }
                    break;
                case 4:
                    System.out.println("Do you want to delete remote files ? <y/n>");
                    if(s.next().charAt(0)=='y'){
                    DriveService.service.files().delete(DriveService.folderId).execute();
                    DriveService.hasFolder("CryptoDrive");}
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid Selection\n");
                    break;
            }
        }while(choice!=5);
    }
}
