package local.ys.prototype.counter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

class NIOFile implements File {

    private final FileChannel fileChannel;

    NIOFile(FileChannel fileChannel) {
        this.fileChannel = fileChannel;
    }

    @Override
    public int read(byte[] buffer) throws IOException {
        return fileChannel.read(ByteBuffer.wrap(buffer));
    }

    @Override
    public void close() throws IOException {
        fileChannel.close();
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}
