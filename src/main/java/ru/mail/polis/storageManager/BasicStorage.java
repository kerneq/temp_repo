package ru.mail.polis.storageManager;

import java.io.IOException;

/**
 * Created by iters on 11/16/17.
 */
public interface BasicStorage {
    boolean saveData(String id, byte[] data) throws IOException;
    boolean removeData(String id);
    byte[] getData(String key);
    boolean isDataExist(String key);
}
