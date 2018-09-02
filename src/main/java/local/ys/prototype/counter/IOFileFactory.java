package local.ys.prototype.counter;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

class IOFileFactory implements FileFactory {

    private static final String MODE = "r";

    @Override
    public File create(String pathname) throws FileNotFoundException {
        RandomAccessFile file = new RandomAccessFile(pathname, MODE);
        return new IOFile(file);
    }
}
