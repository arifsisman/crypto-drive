package Monitor;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.sql.Time;
import java.time.LocalTime;

/**
 * @author Mustafa Sisman
 */
public class MonitorDirectory {
    public static void listen() throws IOException, InterruptedException {

        Path cryptoDriveFolder = Paths.get("C:\\Users\\musta\\Documents\\CryptoDrive");
        WatchService watchService = FileSystems.getDefault().newWatchService();
        cryptoDriveFolder.register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);
        boolean valid = true;
        do {
            WatchKey watchKey = watchService.take();

            for (WatchEvent event : watchKey.pollEvents()) {
                WatchEvent.Kind kind = event.kind();
                if (StandardWatchEventKinds.ENTRY_CREATE.equals(kind)){
                    String fileName = event.context().toString();
                    System.out.println(Time.valueOf(LocalTime.now())+" File Created:" + fileName);
                }
                if (StandardWatchEventKinds.ENTRY_DELETE.equals(kind)){
                    String fileName = event.context().toString();
                    System.out.println(Time.valueOf(LocalTime.now())+" File Deleted:" + fileName);
                }
                if (StandardWatchEventKinds.ENTRY_MODIFY.equals(kind)){
                    String fileName = event.context().toString();
                    System.out.println(Time.valueOf(LocalTime.now())+" File Modified:" + fileName);
                }
            }
            valid = watchKey.reset();
        } while (valid);

    }
}