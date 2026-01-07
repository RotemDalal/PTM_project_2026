package test;

public class PlusAgent implements Agent {
    private final String[] subs, pubs;
    private double x = 0, y = 0;

    public PlusAgent(String[] subs, String[] pubs) {
        this.subs = subs;
        this.pubs = pubs;
        TopicManagerSingleton.get().getTopic(subs[0]).subscribe(this);
        TopicManagerSingleton.get().getTopic(subs[1]).subscribe(this);
    }

    @Override
    public String getName() { return "PlusAgent"; }

    @Override
    public void reset() { x = 0; y = 0; }

    @Override
    public void callback(String topic, Message msg) {
        if (topic.equals(subs[0])) x = msg.asDouble;
        if (topic.equals(subs[1])) y = msg.asDouble;

        if (!Double.isNaN(x) && !Double.isNaN(y)) {
            TopicManagerSingleton.get().getTopic(pubs[0]).publish(new Message(x + y));
        }
    }

    @Override
    public void close() {}
}