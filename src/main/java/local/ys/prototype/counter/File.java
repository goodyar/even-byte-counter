package local.ys.prototype.counter;

import java.io.IOException;

interface File extends AutoCloseable {
    int read(byte[] buffer) throws IOException;
}
