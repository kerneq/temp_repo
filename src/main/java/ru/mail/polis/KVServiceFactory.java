package ru.mail.polis;

import one.nio.server.AcceptorConfig;
import one.nio.server.ServerConfig;
import org.jetbrains.annotations.NotNull;
import ru.mail.polis.server.KvServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Constructs {@link KVService} instances.
 *
 * @author Vadim Tsesko <mail@incubos.org>
 */
final class KVServiceFactory {
    private static final long MAX_HEAP = 1024 * 1024 * 1024;

    private KVServiceFactory() {
        // Not supposed to be instantiated
    }

    /**
     * Construct a storage instance.
     *
     * @param port     port to bind HTTP server to
     * @param data     local disk folder to persist the data to
     * @return a storage instance
     */
    @NotNull
    static KVService create(final int port,
                            @NotNull final File data) throws IOException {
        if (Runtime.getRuntime().maxMemory() > MAX_HEAP) {
            throw new IllegalStateException("The heap is too big. Consider setting Xmx.");
        }

        if (port <= 0 || 65536 <= port) {
            throw new IllegalArgumentException("Port out of range");
        }

        if (!data.exists()) {
            throw new IllegalArgumentException("Path doesn't exist: " + data);
        }

        if (!data.isDirectory()) {
            throw new IllegalArgumentException("Path is not a directory: " + data);
        }

        ServerConfig config = getBasicConfig(port);

        return new KvServiceImpl(config, data);
    }

    private static ServerConfig getBasicConfig(int port) {
        ServerConfig config = new ServerConfig();
        AcceptorConfig ac = new AcceptorConfig();

        ac.port = port;
        ac.address = "localhost";
        config.acceptors = new AcceptorConfig[] {ac};

        return config;
    }
}
