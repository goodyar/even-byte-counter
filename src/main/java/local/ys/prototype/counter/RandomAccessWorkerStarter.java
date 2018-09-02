package local.ys.prototype.counter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;

class RandomAccessWorkerStarter {

    private final RandomAccessFileFactory factory;

    RandomAccessWorkerStarter(RandomAccessFileFactory factory) {
        this.factory = factory;
    }

    /*
     * Делим файл на сегменты по количеству ядер.
     */

    void run(int coreNumber, int bufferSize, String pathname) throws Exception {
        Instant startInstant, stopInstant;
        Counter counter = new Counter();
        CountDownLatch doneSignal = new CountDownLatch(coreNumber);

        /*
         *  Объект с интерфейсом RandomAccessFile порождается конкретной фабрикой
         *  с интерфейсом RandomAccessFileFactory. Это не java.io.RandomAccessFile.
         *  См. файл EvenByteCounter.
         */

        try (RandomAccessFile file = factory.createRandomAccessFile(pathname)) {
            int lastCore = coreNumber - 1;
            long segmentSize = size(pathname) / coreNumber;
            long tail = size(pathname) % coreNumber;

            startInstant = Instant.now();
            long startPosition;

            // Запускаем для всех ядер, кроме последнего.
            for (int i = 0; i < lastCore; i++) {
                startPosition = i * segmentSize;
                RandomAccessWorker worker = new RandomAccessWorker(startPosition, segmentSize, bufferSize, counter,
                        file, doneSignal);
                Thread thread = new Thread(worker);
                thread.start();
            }

            /*
             * Если длина файла не делится целочисленно на количество ядер, то просто добавляем остаток
             * к сегменту последнего ядра и запускаем. При 1000 ядрах остаток < 1k.
             */
            startPosition = lastCore * segmentSize;
            RandomAccessWorker worker = new RandomAccessWorker(startPosition, segmentSize + tail, // <--
                    bufferSize, counter, file, doneSignal);
            Thread thread = new Thread(worker);
            thread.start();

            doneSignal.await();
            stopInstant = Instant.now();

            Duration elapsed = Duration.between(startInstant, stopInstant);
            System.out.printf("io type: %s, counter: %d, core number: %d, buffer size: %d bytes, elapsed time: %d ms %n",
                    file, counter.getCounter(), coreNumber, bufferSize, elapsed.toMillis());
        }
    }

    private long size(String pathname) throws IOException {
        Path path = Paths.get(pathname);
        return Files.size(path);
    }
}
