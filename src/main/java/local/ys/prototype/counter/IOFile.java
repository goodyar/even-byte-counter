package local.ys.prototype.counter;

import java.io.IOException;
import java.io.RandomAccessFile;

class IOFile implements File {

    private final RandomAccessFile file;

    IOFile(RandomAccessFile file) {
        this.file = file;
    }

    @Override
    public synchronized int read(byte[] buff) throws IOException {
        return file.read(buff);
    }

    @Override
    public void close() throws IOException {
        file.close();
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}
