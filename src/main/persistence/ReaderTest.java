package persistence;

import model.Alarm;
import model.Bundle;
import model.GeneralClock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;



//A test class for the reader Class
public class ReaderTest {

    private static final String TEST_FILE = "./src/main/persistence/ReaderTestFile.txt";
    private ArrayList<GeneralClock> alarms = new ArrayList<>();

    @Test
    void readAlarmsTest() {
        Alarm first = new Alarm(5, 30, "FirstAlarm");
        try {
            alarms = Reader.readAlarms(new File(TEST_FILE));
            assertTrue(Alarm.equals(first, (Alarm) alarms.get(0)));
            assertEquals(alarms.get(1).getName(), "firstBundle");
            assertTrue(alarms.get(1).getStatus());
            Bundle firstBundleFirstAlarm = (Bundle) alarms.get(1);
            assertTrue(Alarm.equals(firstBundleFirstAlarm.getAlarm(0), new Alarm(5, 40, "bundleAlarm1")));

            Alarm fourth = (Alarm) alarms.get(4);
            assertTrue(Alarm.equals(fourth, new Alarm(4, 29, "Yote")));

        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }

    }


    @Test
    //From TellerApp Example Program
    void testIOException() {
        try {
            Reader.readAlarms(new File("./nonexistent/path/AlArMs.txt"));
        } catch (IOException e) {
            // expected
        }
    }


}
