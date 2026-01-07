package test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ParallelAgent implements Agent {
    
    private static class TopicMessage {
        public final String topic;
        public final Message msg;
        public TopicMessage(String topic, Message msg) {
            this.topic = topic;
            this.msg = msg;
        }
    }

    private final Agent wrappedAgent;
    private final BlockingQueue<TopicMessage> queue;
    private final Thread workerThread;
    private volatile boolean isClosed = false;

    public ParallelAgent(Agent agent, int capacity) {
        this.wrappedAgent = agent;
        this.queue = new ArrayBlockingQueue<>(capacity);

        this.workerThread = new Thread(() -> {
            while (!isClosed || !queue.isEmpty()) {
                try {
                    TopicMessage tm = queue.take(); 
                    wrappedAgent.callback(tm.topic, tm.msg);
                } catch (InterruptedException e) {
                    if (isClosed && queue.isEmpty()) break;
                }
            }
        });
        this.workerThread.start();
    }

    @Override
    public String getName() {
        return wrappedAgent.getName();
    }

    @Override
    public void reset() {
        wrappedAgent.reset();
        queue.clear();
    }

    @Override
    public void callback(String topic, Message msg) {
        if (!isClosed) {
            try {
                queue.put(new TopicMessage(topic, msg));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void close() {
        isClosed = true;
        workerThread.interrupt(); 
        wrappedAgent.close();
    }
}