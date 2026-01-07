package test;

import java.util.Date;

public class Message {
    public final byte[] data;
    public final String asText;
    public final double asDouble;
    public final Date date;

    public Message(String asText) {
        this.asText = asText;
        this.data = asText.getBytes();
        this.date = new Date();

        double d;
        try {
            d = Double.parseDouble(asText);
        } catch (NumberFormatException e) {
            d = Double.NaN;
        }
        this.asDouble = d;
    }

    public Message(byte[] data) {
        this(new String(data));
    }

    public Message(double asDouble) {
        this(Double.toString(asDouble));
    }
}