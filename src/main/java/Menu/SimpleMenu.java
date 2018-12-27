package Menu;

import Drive.Download;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Mustafa Sisman
 */
public class SimpleMenu {
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
    public SimpleMenu() throws IOException {
        itemList.add(new Item("1","Download"));
        itemList.add(new Item("2","Delete Local Enc. Files"));
        itemList.add(new Item("3","Exit"));
    }

    public static void display(){
        System.out.println("---CryptoDrive---");
        for (Item i: itemList){
            System.out.println(i.itemId+") "+i.itemName);
        }
        System.out.println("-----------------");
    }

    public static void listen() throws IOException {
        do{
        display();
        choice = s.nextInt();
        switch (choice){
            case 1:
                Download.listAndDownload(20);
                break;
            case 2:
                break;
            case 3:
                System.exit(0);
            default:
                System.out.println("Invalid Selection\n");
                break;
        }
    }while(choice!=3);
}
}
