package local.ys.prototype.counter;

class EvenByteCounter {

    public static void main(String[] args) throws Exception {
        int coreNumber = Runtime.getRuntime().availableProcessors();
        int bufferSize = 100 * 1024 * 1024;
        String pathname = "c:/test/10g.test";

        RandomAccessWorkerStarter starter = new RandomAccessWorkerStarter(new NIOFileFactory());
        starter.run(coreNumber, bufferSize, pathname);
    }
}
