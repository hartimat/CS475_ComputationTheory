/*
 * COURSE: CS475
 * ASSIGNMENT: Topic 5
 * AUTHOR: Matthew Hartigan
 * DATE: 2-February-2020
 * DESCRIPTION: 
 */
package cs475_topic5_hartigan;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import javax.swing.JOptionPane;

/**
 * Main class for implementing a turing machine.
 * @author Matt
 */
public class CS475_Topic5_Hartigan {

    /**
     * Reads the input file selected from the dialog box.
     * BIG-O ANALYSIS:
     * There are no loops in this function.  It is O(1)
     * @return 
     */    
    public static JFileChooser read () {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Great.  We're now opening: " + chooser.getSelectedFile().getName());
            System.out.println();
        }
        return chooser;
    }
    
    /**
     * Main function.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Greet User
        System.out.println("Welcome to the CS 475 Topic 5 Assignment!");
        System.out.println("We will be working with Turing Machines.");
        System.out.println("Please seletion your input file.");
        System.out.println();
        
        // Read in TM from input file
        File inputFile = read().getSelectedFile();
        
        // Create TM, parse input file
        TuringMachine tm = new TuringMachine();
        tm.parse(inputFile);
        
        // Display TM alphabet to user
        tm.displayAlphabet();
        
        // Prompt user for input string, convert to tape
        String inputString = JOptionPane.showInputDialog("Please input a string.");
        
        // Evaluate input string and display acceptance
        Tape tape = new Tape(inputString);
        tm.evaluate(inputString, tape);
    }
}
