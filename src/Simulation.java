import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/***
 * The class that initiates all other objects.
 */
public class Simulation {

    /* Object input fields */
    public static RAM    mem;

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

