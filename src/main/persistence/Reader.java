package persistence;

import model.Alarm;
import model.Bundle;
import model.GeneralClock;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//Code largely taken from TellerApp Reader Class.
// A reader that can read clock data from a file. This class will never be instantiated. It exists only for static
//functions.
public class Reader {
    //this basically tells the reader when to stop reading.
    public static final String DELIMITER = ",";

    public static ArrayList<GeneralClock> readAlarms(File file) throws IOException {
        //so this takes the current file and turns every line into a String, and holds an array of Strings i think.
        List<String> fileContent = readFile(file);
        return parseContent(fileContent);
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: returns a list of accounts parsed from list of strings
    // where each string contains data for one account
    private static ArrayList<GeneralClock> parseContent(List<String> fileContent) {
        ArrayList<GeneralClock> clocks = new ArrayList<>();

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            //So right now this is only good add a single alarm. I need it to add an
            // arraylist of General Clocks when dealing with a bundle.
            clocks.add(parseClock(lineComponents));
        }

        return clocks;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: components has size 4 where element 0 represents the
    // id of the next account to be constructed, element 1 represents
    // the id, elements 2 represents the name and element 3 represents
    // the balance of the account to be constructed
    // EFFECTS: returns an account constructed from components
    private static GeneralClock parseClock(List<String> components) {

        if (components.get(0).equals("b")) {
            GeneralClock lessTemporary = parseClockBundle(components);
            return lessTemporary;

        } else if (components.get(0).equals("a")) {
            GeneralClock lessTemporary = parseAlarm(components);
            return lessTemporary;
        }

        return new Alarm(1, 1, "Failed");
    }



    private static Bundle parseClockBundle(List<String> components) {
        String name = components.get(1);
        boolean bundleStatus = Boolean.parseBoolean(components.get(2));
        int length = Integer.parseInt(components.get(3));

        Bundle temporary = new Bundle(name);

        String tempName;
        int tempHours;
        int tempMins;
        boolean tempStatus;

        for (int i = 0; i < length; i++) {
            tempHours = Integer.parseInt(components.get(4 + (i * 4)));
            tempMins = Integer.parseInt(components.get(5 + (i * 4)));
            tempName = components.get(6 + (i * 4));


            if (components.get(7 + (i * 4)).equals("true")) {
                tempStatus = true;
            } else {
                tempStatus = false;
            }

            temporary.addAlarm(new Alarm(tempHours, tempMins, tempName, tempStatus));
        }
        return temporary;
    }

    private static Alarm parseAlarm(List<String> components) {
        int hours = Integer.parseInt(components.get(1));
        int mins = Integer.parseInt(components.get(2));
        String name = components.get(3);
        boolean tempStatus;

        if (components.get(4).equals("T")) {
            tempStatus = true;
        } else {
            tempStatus = false;
        }

        return new Alarm(hours, mins, name, tempStatus);
    }
}

























