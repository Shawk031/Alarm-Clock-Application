package model;

import persistence.Reader;
import persistence.Saveable;

import java.awt.*;
import java.io.PrintWriter;
import java.util.ArrayList;


//This class represents a bundle of alarms. This class has 3 main fields: name (the name of the bundle),
//alarms (an arrayList of alarm objects stored within this bundle), status (whether the bundle is activated or not). If
//a bundle has status = false, the bundle will not call ring on any alarm within it, regardless of whether the alarms
// in it are on or not
public class Bundle implements GeneralClock, Saveable {

    public static final Color COLOR = new Color(150, 250, 150);
    private String name;
    private ArrayList<Alarm> alarms;
    private boolean status;

    //EFFECT: Constructs a new bundle with name. Instantiates alarms and sets status to true
    public Bundle(String name) {
        this.name = name;
        alarms = new ArrayList<>();
        status = true;
    }

    public Bundle(String name, ArrayList<Alarm> x) {
        this.name = name;
        alarms = x;
        status = true;
        System.out.println(this);
    }

    //EFFECT: returns false. This method exists and is meant to be called for the purposes of the GeneralClock
    //interface, to distinguish between alarms and bundles
    public boolean isAlarm() {
        return false;
    }

    //Effect: Calls ring on every alarm in the ArrayList alarms
    @Override
    public void ring(int hours, int mins) {
        for (Alarm x : alarms) {
            x.ring(hours, mins);
        }
    }

    //Modifies: This
    //Effect: Flips the status from on to off, and vice versa.
    public void changeStatus() {
        this.status = !status;
    }


    //Requires: Alarm object
    //Modifies: Alarms
    //Effect: Adds an alarm object to alarms.
    public void addAlarm(Alarm alarm) {
        alarms.add(alarm);
    }

    //Effect: returns alarm at index i in alarms
    public Alarm getAlarm(int i) {
        return alarms.get(i);
    }


    //Effect: returns the name of the bundle and all the alarms it contains
    public String toString() {
        String result = name + " currently ";

        if (status) {
            result += "on and containing: ";
        } else {
            result += "off and containing: ";
        }


        for (int i = 0; i < alarms.size(); i++) {
            result += alarms.get(i).toString() + ", ";
        }

        return result;
    }

    //effect: returns the length of alarms.
    public int length() {
        return alarms.size();
    }

    //effect: returns the name of the bundle
    public String getName() {
        return name;
    }

    //effect: sets the name of a bundle to this
    public void setName(String str) {
        this.name = str;
    }

    //effect: returns the status of the bundle
    public boolean getStatus() {
        return status;
    }

    /*
    //effect: sets the name of the bundle
    public void setName(String name) {
        this.name = name;
    } */

    //EFFECT: Returns the display string for this bundle. Will display the number of bundles
    public String getDisplayString() {
        return "Contains: " + alarms.size() + " Alarms";
    }


    //from tellerApp Accounts class
    //Effect: Saves the information from a bundle on to the data file.
    public void save(PrintWriter printWriter) {
        printWriter.print("b");
        printWriter.print(Reader.DELIMITER);
        printWriter.print(name);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(status);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(alarms.size());

        for (int i = 0; i < alarms.size(); i++) {

            printWriter.print(Reader.DELIMITER);
            printWriter.print(alarms.get(i).getHours());
            printWriter.print(Reader.DELIMITER);
            printWriter.print(alarms.get(i).getMins());
            printWriter.print(Reader.DELIMITER);

            printWriter.print(alarms.get(i).getName());
            printWriter.print(Reader.DELIMITER);
            printWriter.print(alarms.get(i).getStatus());
        }
        printWriter.println();
    }

}
