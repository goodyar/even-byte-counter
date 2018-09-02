package local.ys.prototype.counter;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;

class WorkerStarter {

    private final FileFactory factory;

    WorkerStarter(FileFactory factory) {
        this.factory = factory;
    }

    /*
     * Запускаем потоки по количеству ядер.
     * Объект с интерфейсом File порождается конкретной фабрикой с интерфейсом FileFactory.
     * См. в файле EvenByteCounter.
     */

    void run(int coreNumber, int bufferSize, String pathname) throws Exception {
        Instant startInstant, stopInstant;
        Counter counter = new Counter();
        CountDownLatch doneSignal = new CountDownLatch(coreNumber);

        try (File file = factory.create(pathname)) {
            startInstant = Instant.now();

            for (int i = 0; i < coreNumber; i++) {
                byte[] buffer = new byte[bufferSize];
                Worker worker = new Worker(buffer, counter, file, doneSignal);
                Thread thread = new Thread(worker);
                thread.start();
            }

            doneSignal.await();
            stopInstant = Instant.now();

            Duration elapsed = Duration.between(startInstant, stopInstant);
            System.out.printf("io type: %s, counter: %d, core number: %d, buffer size: %d bytes, elapsed time: %d ms %n",
                    file, counter.getCounter(), coreNumber, bufferSize, elapsed.toMillis());
        }
    }
}
