package test;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ParallelAgent implements Agent {
    private final Agent wrapped;
    private final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(100);
    private volatile boolean stop = false;
    private final Thread thread;

    public ParallelAgent(Agent wrapped) {
        this.wrapped = wrapped;
        this.thread = new Thread(() -> {
            while (!stop) {
                try { queue.take().run(); } catch (InterruptedException e) { if (stop) break; }
            }
        });
        this.thread.start();
    }
    @Override public String getName() { return wrapped.getName(); }
    @Override public void reset() { wrapped.reset(); }
    @Override public void callback(String topic, Message msg) { queue.add(() -> wrapped.callback(topic, msg)); }
    @Override public void close() { stop = true; thread.interrupt(); wrapped.close(); }
}