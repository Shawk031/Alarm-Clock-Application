package model;


import persistence.Reader;
import persistence.Saveable;
import ui.AlarmApp;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.File;
import java.io.PrintWriter;

//Class that represents an individual alarm clock. This has 4 main fields: Mins, denoting what minute the alarm should
//ring at. Hours, denoting what hour the alarm should ring at. Name, denoting the name of this alarm. Status, denoting
//whether this alarm should ring when it's appointed time comes.
public class Alarm implements GeneralClock, Saveable {

    public static final Color COLOR = new Color(150, 150, 250);
    protected int mins;
    protected int hours;
    protected String name;
    protected boolean status;
    private boolean firstRing;

    //Requires: Hours must be less than or equal to 24 and no smaller than 0. Mins must be less than or equal to
    // 59 and no smaller than 0.
    //Modifies: This by instantiating variables
    //Effect: Constructor.
    public Alarm(int hours, int mins, String name) {
        this.hours = hours;
        this.mins = mins;
        this.name = name;
        status = true;
    }


    //See data specifications for constructor above
    public Alarm(int hours, int mins, String name, boolean status) {
        this.hours = hours;
        this.mins = mins;
        this.name = name;
        this.status = status;
    }


    //returns true if this object is an alarm or not. Obviously, for this class, it will return
    //true. This method is called on GeneralClocks.
    public boolean isAlarm() {
        return true;
    }


    //Compares two alarms, returning true if the alarms share their time of going off and their name.
    public static boolean equals(Alarm first, Alarm second) {
        return first.getName().equals(second.getName()) && first.getHours() == second.getHours()
                && first.getMins() == second.getMins() && first.getStatus() == second.getStatus();
    }


    //EFFECT: Causes the alarm to ring when systemTime = the hour and minute the alarm is set to.
    public void ring(int currentHours, int currentMinutes) {
        if (currentHours == hours && currentMinutes == mins && status) {
            if (firstRing) {
                System.out.println(name + " has rung!");
                AlarmApp.playSound("./data/AlarmRing.wav");
                firstRing = false;
            }
        } else {
            firstRing = true;
        }
    }

    //MODIFIES: This
    //EFFECT: Swaps the value of status
    public void changeStatus() {
        this.status = !status;
    }

    //Getter and Setter Methods

    //Effect: Returns the hours this alarm is set to go off at
    public int getHours() {
        return hours;
    }

    //Effect: Returns the minutes this alarm is set to go off at
    public int getMins() {
        return mins;
    }

    //Effect: Returns name of this object
    public String getName() {
        return name;
    }

    //Effect: Returns status of this
    public boolean getStatus() {
        return status;
    }

    //Requires: Integer no more than 24 and no less than 0
    //Modifies: This
    //Effect: Changes this alarms hour time it's set to go off at
    public void setHours(int hours) {
        this.hours = hours;
    }

    //Requires: Integer less than 60 and at least 0
    //Modifies: This
    //Effect: Changes this alarms minute time it's set to go off at
    public void setMins(int mins) {
        this.mins = mins;
    }

    //Requires: String
    //Modifies: This
    //Effect: Changes the name of this alarm
    public void setName(String name) {
        this.name = name;
    }

    //EFFECT: Returns a string depicting the name of an alarm, its status, and the time it'll go off.
    public String toString() {
        String minutes;
        if (mins < 10) {
            minutes = "0" + mins;
        } else {
            minutes = "" + mins;
        }
        if (status) {
            return name + " activated and set to go off at " + hours + ":" + minutes;

        }
        return name + " currently off but set to go off at " + hours + ":" + minutes;
    }


    //EFFECT: Returns a String for the alarm to display in the UI (the time the alarm goes off).
    public String getDisplayString() {
        String minutes;
        if (mins < 10) {
            minutes = "0" + mins;
        } else {
            minutes = "" + mins;
        }
        return hours + ":" + minutes;
    }

    //from tellerApp Accounts class
    public void save(PrintWriter printWriter) {
        printWriter.print("a");
        printWriter.print(Reader.DELIMITER);
        printWriter.print(hours);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(mins);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(name);
        printWriter.print(Reader.DELIMITER);

        if (status) {
            printWriter.println("T");
        } else {
            printWriter.println("F");
        }

    }

}
