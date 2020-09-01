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


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import static org.junit.jupiter.api.Assertions.*;


//Large parts of code taken from TellerApp WriterTest Class
//A test class for the Writer Class.
class WriterTest {
    private static final String TEST_FILE = "./src/main/persistence/WriterTestFile.txt";
    private Writer testWriter;
    private ArrayList<GeneralClock> alarms;

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new Writer(new File(TEST_FILE));
        alarms = new ArrayList<>();
        alarms.add(new Alarm(10, 3, "firstAlarm"));
        alarms.add(new Alarm(11, 4, "secondAlarm"));

        Bundle temp = new Bundle("bundle");
        temp.addAlarm(new Alarm(4, 20, "bundleFirst"));
        temp.addAlarm(new Alarm(5, 30, "bundleSecond"));

        alarms.add(temp);
    }

    @Test
    void testWriteAccounts() {
        //Save all the GeneralClocks to file.
        for (GeneralClock x : alarms) {
            testWriter.write((Saveable) x);
        }
        testWriter.close();

        // Ensure all clocks have expected values.
        try {

            ArrayList<GeneralClock> newAlarms = Reader.readAlarms(new File(TEST_FILE));

            assertTrue(Alarm.equals((Alarm) newAlarms.get(0), (Alarm) alarms.get(0)));
            assertTrue(Alarm.equals((Alarm) alarms.get(1), (Alarm) newAlarms.get(1)));
            assertEquals(newAlarms.get(2).getName(), "bundle");

            Bundle temp = (Bundle) newAlarms.get(2);

            assertEquals(temp.getName(), "bundle");
            assertEquals(temp.getStatus(), true);
            assertTrue(Alarm.equals(temp.getAlarm(0), new Alarm(4, 20, "bundleFirst")));
            assertTrue(Alarm.equals(temp.getAlarm(1), new Alarm(5, 30, "bundleSecond")));


        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
