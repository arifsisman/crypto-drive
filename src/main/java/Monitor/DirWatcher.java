package Monitor;

import java.io.IOException;
import java.nio.file.*;
import java.sql.Time;
import java.time.LocalTime;

import static java.nio.file.StandardWatchEventKinds.*;

public class DirWatcher implements Runnable {

    private final Path dir;
    private final WatchService watcher;
    private final WatchKey key;

    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }

    /**
     * Creates a WatchService and registers the given directory
     */
    public DirWatcher(Path dir) throws IOException {
        this.dir = dir;
        this.watcher = FileSystems.getDefault().newWatchService();
        this.key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
    }

    public void run() {
        try {
            for (;;) {
                // wait for key to be signalled
                WatchKey key = watcher.take();

                if (this.key != key) {
                    System.err.println("WatchKey not recognized!");
                    continue;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent<Path> ev = cast(event);
                    System.out.format("%s %s: %s\n",Time.valueOf(LocalTime.now()), ev.kind(), dir.resolve(ev.context()));
                    WatchEvent.Kind kind = event.kind();
                    if (StandardWatchEventKinds.ENTRY_CREATE.equals(kind)){
                        //System.out.println(Time.valueOf(LocalTime.now())+" File Created:" + fileName);
                        //TODO trigger encrypt
                    }
                    if (StandardWatchEventKinds.ENTRY_DELETE.equals(kind)){

                    }
                    if (StandardWatchEventKinds.ENTRY_MODIFY.equals(kind)){

                    }
                }

                // reset key
                if (!key.reset()) {
                    break;
                }
            }
        } catch (InterruptedException x) {
            return;
        }
    }
}