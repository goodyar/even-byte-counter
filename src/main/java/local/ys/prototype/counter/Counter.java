package local.ys.prototype.counter;

class Counter {

    private long counter;

    synchronized void add(long value) {
        counter += value;
    }

    long getCounter() {
        return counter;
    }
}
