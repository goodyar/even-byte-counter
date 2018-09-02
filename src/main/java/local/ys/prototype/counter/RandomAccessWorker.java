package local.ys.prototype.counter;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class RandomAccessWorker implements Runnable {
    private final long startPosition;
    private final long segmentSize;
    private final byte[] buffer;
    private final Counter counter;
    private final RandomAccessFile file;
    private final CountDownLatch doneSignal;

    private long position;
    private long evenByteCounter;


    RandomAccessWorker(long startPosition, long segmentSize, int bufferSize, Counter counter, RandomAccessFile file,
                       CountDownLatch doneSignal) {
        this.startPosition = startPosition;
        this.segmentSize = segmentSize;
        this.buffer = new byte[bufferSize];
        this.counter = counter;
        this.file = file;
        this.doneSignal = doneSignal;
    }

    @Override
    public void run() {
        try {
            readSegment();
            counter.add(evenByteCounter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            doneSignal.countDown();
        }
    }

    private void readSegment() throws IOException {
        readMainPart();
        readTail();
    }

    private void readMainPart() throws IOException {
        long chunks = segmentSize / buffer.length;

        for (long i = 0; i < chunks; i++) {
            position = startPosition + (i * buffer.length);
            int bytesRead = file.read(buffer, position);
            readBuffer(buffer, bytesRead);
        }

        position += buffer.length;
    }

    private void readTail() throws IOException {
        int tail = (int) (segmentSize % buffer.length); // 0 <= tail < buffer.length

        if (tail != 0) {
            byte[] tailBuffer = new byte[tail];
            int bytesRead = file.read(tailBuffer, position);
            readBuffer(tailBuffer, bytesRead);
        }
    }

    private void readBuffer(byte[] buffer, int bytesReade) {
        if (buffer.length != bytesReade) {
            throw new RuntimeException("buffer length != bytesReade");
        }

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
