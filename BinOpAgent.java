package test;

import java.util.function.BinaryOperator;

public class BinOpAgent implements Agent {
    private final String name;
    private final String input1, input2, output;
    private final BinaryOperator<Double> operator;
    private double val1 = 0, val2 = 0;

    public BinOpAgent(String name, String input1, String input2, String output, BinaryOperator<Double> operator) {
        this.name = name;
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
        this.operator = operator;

        TopicManagerSingleton.get().getTopic(input1).subscribe(this);
        TopicManagerSingleton.get().getTopic(input2).subscribe(this);
        TopicManagerSingleton.get().getTopic(output).addPublisher(this);
    }

    @Override
    public String getName() { return name; }

    @Override
    public void reset() {
        val1 = 0;
        val2 = 0;
    }

    @Override
    public void callback(String topic, Message msg) {
        if (topic.equals(input1)) val1 = msg.asDouble;
        if (topic.equals(input2)) val2 = msg.asDouble;

        double result = operator.apply(val1, val2);
        TopicManagerSingleton.get().getTopic(output).publish(new Message(result));
    }

    @Override
    public void close() {}
}