package ru.mail.polis.server;

import one.nio.http.HttpServer;
import one.nio.server.ServerConfig;
import ru.mail.polis.storageManager.FileBasicStorageImpl;
import ru.mail.polis.KVService;
import ru.mail.polis.handler.BackHandler;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by iters on 11/16/17.
 */
public class KvServiceImpl extends HttpServer implements KVService {

    public KvServiceImpl(ServerConfig config, File dir) throws IOException {
        super(config);
        addRequestHandlers(new BackHandler(new FileBasicStorageImpl(dir)));
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
