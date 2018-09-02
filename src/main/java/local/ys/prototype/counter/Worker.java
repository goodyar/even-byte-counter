package local.ys.prototype.counter;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

class Worker implements Runnable {

    private final byte[] buffer;
    private final Counter counter;
    private final File file;
    private final CountDownLatch doneSignal;

    private long evenByteCounter;

    Worker(byte[] buffer, Counter counter, File file, CountDownLatch doneSignal) {
        this.buffer = buffer;
        this.counter = counter;
        this.file = file;
        this.doneSignal = doneSignal;
    }

    @Override
    public void run() {
        try {
            readFile();
            counter.add(evenByteCounter); // Добавляем в счётчик.
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            doneSignal.countDown();
        }
    }

    /*
     * Читаем файл с текущей позиции пока не кончится. Курсор сдвигают потоки по очереди.
     */

    private void readFile() throws IOException {
        int bytesReade;

        while ((bytesReade = file.read(buffer)) > -1) {
            readBuffer(buffer, bytesReade);
        }
    }

    private void readBuffer(byte[] buffer, int bytesReade) {
        for (int i = 0; i < bytesReade; i++) {
            countEvenBytes(buffer[i]);
        }
    }

    private void countEvenBytes(byte b) {
        if ((b & 1) == 0) {
            evenByteCounter++;
        }
    }
}
