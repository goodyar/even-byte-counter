package local.ys.prototype.counter;

import java.io.IOException;

interface RandomAccessFileFactory {
    RandomAccessFile createRandomAccessFile(String pathname) throws IOException;
}
