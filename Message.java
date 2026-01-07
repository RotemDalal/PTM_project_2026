package test;
import java.util.Date;

public class Message {
    public final byte[] data;
    public final String asText;
    public final double asDouble;
    public final Date date;

    public Message(String text) {
        this.asText = text;
        this.data = text.getBytes();
        this.date = new Date();
        double d;
        try { d = Double.parseDouble(text); } 
        catch (NumberFormatException e) { d = Double.NaN; }
        this.asDouble = d;
    }
    public Message(double d) { this(Double.toString(d)); }
    public Message(byte[] data) { this(new String(data)); }
}