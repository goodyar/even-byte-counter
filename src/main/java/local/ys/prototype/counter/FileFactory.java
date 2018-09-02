package local.ys.prototype.counter;

import java.io.IOException;

interface FileFactory {
    File create(String pathname) throws IOException;
}
