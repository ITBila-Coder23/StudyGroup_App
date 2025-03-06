package storage;

import java.io.FileOutputStream;
import java.io.IOException;

public class DatabaseHelper {
    public static void resetDatabase() {
        try {
            new FileOutputStream("database.dat").close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
