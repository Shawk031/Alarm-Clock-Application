package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.Reader;
import persistence.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;


//A test class for alarms
public class AlarmTest {
    private Alarm test;
    private  String clocksFile = "./data/testAlarms.txt";

    @BeforeEach
    void testConstructor() {
        test = new Alarm(20, 40, "test");

    }

    @Test
    void testAlarm() {
        assertEquals(test.getHours(), 20);
        assertEquals(test.getMins(), 40);
        assertEquals(test.getName(), "test");
    }

    @Test
    void testEquals() {
        assertTrue(Alarm.equals(test, new Alarm(20, 40, "test")));
        assertFalse(Alarm.equals(test, new Alarm(21, 40, "test")));
        assertFalse(Alarm.equals(test, new Alarm(20, 41, "test")));
        assertFalse(Alarm.equals(test, new Alarm(20, 40, "Not Test")));

    }

    @Test
    void testGettersAndSetters() {
        assertEquals(test.getHours(), 20);
        test.setHours(5);
        assertEquals(test.getHours(), 5);

        assertEquals(test.getMins(), 40);
        test.setMins(20);
        assertEquals(test.getMins(), 20);

        assertEquals(test.getName(), "test");
        test.setName("smt");
        assertEquals(test.getName(), "smt");

        assertTrue(test.getStatus());
        test.changeStatus();
        assertFalse(test.getStatus());

    }


    @Test
    void testStatus() {
        assertTrue(test.getStatus());
        test.changeStatus();
        assertFalse(test.getStatus());

    }

    @Test
    void testToString() {
        assertEquals(test.toString(), "test activated and set to go off at 20:40");
        test.setMins(4);
        assertEquals(test.toString(), "test activated and set to go off at 20:04");
        test.setHours(1);
        assertEquals(test.toString(), "test activated and set to go off at 1:04");
        test.setName("yeot");
        assertEquals(test.toString(), "yeot activated and set to go off at 1:04");
        test.changeStatus();
        assertEquals(test.toString(), "yeot currently off but set to go off at 1:04");
        test.setMins(30);
        assertEquals(test.toString(), "yeot currently off but set to go off at 1:30");


    }


    @Test
    void testSave() {
        try {
            Writer writer = new Writer(new File(clocksFile));
            writer.write(new Alarm(10, 30, "testAlarm"));

            writer.close();
        } catch (FileNotFoundException e) {
            fail();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            fail();
            // this is due to a programming error
        }

        try {
            Alarm temp = (Alarm) Reader.readAlarms(new File(clocksFile)).get(0);
            assertTrue(Alarm.equals(new Alarm(10, 30, "testAlarm"), temp));

        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

}

















