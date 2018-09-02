package local.ys.prototype.counter;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static org.mockito.Mockito.*;

class WorkerTest {

    @Test
    void run() throws IOException {
        byte[] testBuffer = {2, 3, 4, 5, 6, 7, 3, 3, 8, 1, 33};
        long numberOfEvenBytes = 4; // 2, 4, 6, 8

        Counter mockCounter = mock(Counter.class);
        File mockFile = mock(File.class);
        CountDownLatch mockDoneSignal = mock(CountDownLatch.class);

        when(mockFile.read(testBuffer)).thenReturn(testBuffer.length).thenReturn(-1);

        Worker worker = new Worker(testBuffer, mockCounter, mockFile, mockDoneSignal);
        worker.run();

        verify(mockCounter).add(numberOfEvenBytes);
    }
}
