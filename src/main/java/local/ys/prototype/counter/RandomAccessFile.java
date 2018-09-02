package local.ys.prototype.counter;

import java.io.IOException;

interface RandomAccessFile extends AutoCloseable {
    int read(byte[] buffer, long position) throws IOException;
}
