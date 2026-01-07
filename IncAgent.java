package test;

public class IncAgent implements Agent {
    private final String[] subs, pubs;

    public IncAgent(String[] subs, String[] pubs) {
        this.subs = subs;
        this.pubs = pubs;
        TopicManagerSingleton.get().getTopic(subs[0]).subscribe(this);
    }

    @Override
    public String getName() { return "IncAgent"; }

    @Override
    public void reset() {}

    @Override
    public void callback(String topic, Message msg) {
        double val = msg.asDouble;
        if (!Double.isNaN(val)) {
            TopicManagerSingleton.get().getTopic(pubs[0]).publish(new Message(val + 1));
        }
    }

    @Override
    public void close() {}
}