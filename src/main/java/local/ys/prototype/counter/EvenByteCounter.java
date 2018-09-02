package local.ys.prototype.counter;

class EvenByteCounter {

    public static void main(String[] args) throws Exception {
        int coreNumber = Runtime.getRuntime().availableProcessors();
        int bufferSize = 1024 * 1024;
        String pathname = "c:/test/4g.test";

//        WorkerStarter starter = new WorkerStarter(new IOFileFactory());
//        starter.run(1, bufferSize, pathname);
//        starter.run(coreNumber, bufferSize, pathname);

        WorkerStarter starter = new WorkerStarter(new NIOFileFactory());
//        starter.run(1, bufferSize, pathname);
        starter.run(coreNumber, bufferSize, pathname);
    }
}
