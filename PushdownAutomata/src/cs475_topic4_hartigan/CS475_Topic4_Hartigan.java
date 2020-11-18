/*
 * COURSE: CS475
 * ASSIGNMENT: Topic 4
 * AUTHOR: Matthew Hartigan
 * DATE: 26-January-2020
 * DESCRIPTION: Main class for implementing a discrete pushdown 
 * automata for CS475 Week 2 assignment.
 */
package cs475_topic4_hartigan;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import javax.swing.JOptionPane;

/**
 * Main class for implementing a discrete pushdown automata.
 * @author Matt
 */
public class CS475_Topic4_Hartigan {

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
        System.out.println("Welcome to the CS 475 Topic 4 Assignment!");
        System.out.println("We will be working with Finite Automatons.");
        System.out.println("Please seletion your input file.");
        System.out.println();
        
        // Read in deterministic PDA from input file
        File inputFile = read().getSelectedFile();
        
        // Create PDA, parse input file
        PushdownAutomata pda = new PushdownAutomata();
        pda.parse(inputFile);
        
        // Display PDA alphabet to user
        pda.displayAlphabet();
        
        // Prompt user for input string
        String inputString = JOptionPane.showInputDialog("Please input a string.");
        
        // Evaluate input string and display acceptance
        pda.evaluate(inputString);
    }
}
