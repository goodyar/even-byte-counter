package local.ys.prototype.counter;

class EvenByteCounter {

    public static void main(String[] args) throws Exception {
        int coreNumber = Runtime.getRuntime().availableProcessors();
        int bufferSize = 1024 * 1024;
        String pathname = "";

        RandomAccessWorkerStarter starter = new RandomAccessWorkerStarter(new NIOFileFactory());
        starter.run(coreNumber, bufferSize, pathname);
    }
}
