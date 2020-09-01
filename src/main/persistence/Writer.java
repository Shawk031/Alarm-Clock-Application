package persistence;

import model.GeneralClock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

// Code taken from Writer class in TellerApp
// A class which records data down onto a text file. Basically, this is what enables the data to stay after the program
//is closed. It does this by storing it all in a text file. The reader is what restores the stored data to
//the information previously used by the user.
public class Writer {
    private PrintWriter printWriter;

    // EFFECTS: constructs a writer that will write data to file
    public Writer(File file) throws FileNotFoundException, UnsupportedEncodingException {
        //So the UTF-8 is basically saying 'use 8 bits to store each character'. Java's standard is 16 bits, which
        // I would only need if I decided to use Chinese characters and other things. So this helps with memory.
        printWriter = new PrintWriter(file, "UTF-8");
    }


    //So this function is called on an Alarm or a bundle, and takes in a file. Basically I need this to write out the
    //required information about the alarm of bundle that I'd want to retrieve when this method is called on a
    //GeneralClock.

    // MODIFIES: this
    // EFFECTS: writes a GeneralClock to file
    public void write(Saveable saveable) {
        saveable.save(printWriter);
    }


    //I'm assuming this function works by closing the writer and then saving all the info? So something is being
    //written in while the writer is 'open' and once the writer is closed then the file is saved. So to not close
    //the writer is basically to write something down without saving it. I think.

    // MODIFIES: this
    // EFFECTS: close print writer
    // NOTE: you MUST call this method when you are done writing data!
    public void close() {
        printWriter.close();
    }
}
