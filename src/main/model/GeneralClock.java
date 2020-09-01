package model;

//GeneralClock interface. This is so that both alarm and bundle can be in the same interface
import java.io.PrintWriter;

//Maybe make this one implement the saveable method.
public interface GeneralClock {
    void changeStatus();

    void ring(int hours, int minutes);

    String getName();

    boolean getStatus();

    void setName(String str);

    boolean isAlarm();

    String getDisplayString();


}
