import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/***
 * The class that initiates all other objects.
 *
 * @author mestiasz@lafayette.edu - changed wordsize, memory size and byte_width
 */
public class Simulation {

    /* Object input fields */
    public static RAM    mem;

    /**
     * @author mestiasz@lafayette.edu - Changed wordsize to 32 and update byteWidth to 0x4
     * @param args
     */
    public static void main(String[] args) {

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                mem = RAM.createAndShowGUI(64, 0x4);
            }
        });
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                CPU.createAndShowGUI(32, 8, mem);
                Helper.wordsize(32);
            }
        });
    }
}

