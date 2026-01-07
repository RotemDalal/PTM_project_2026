package test;
public class IncAgent implements Agent {
    private final String[] subs, pubs;
    public IncAgent(String[] subs, String[] pubs) {
        this.subs = subs; this.pubs = pubs;
        TopicManagerSingleton.get().getTopic(subs[0]).subscribe(this);
    }
    @Override public String getName() { return "IncAgent"; }
    @Override public void reset() {}
    @Override public void callback(String topic, Message msg) {
        if (!Double.isNaN(msg.asDouble))
            TopicManagerSingleton.get().getTopic(pubs[0]).publish(new Message(msg.asDouble + 1));
    }
    @Override public void close() {}
}