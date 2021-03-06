package local.ys.prototype.counter;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

class NIOFileFactory implements FileFactory {

    private static final OpenOption OPTION = StandardOpenOption.READ;

    @Override
    public File create(String pathname) throws IOException {
        Path path = Paths.get(pathname);
        FileChannel fileChannel = FileChannel.open(path, OPTION);
        return new NIOFile(fileChannel);
    }
}
