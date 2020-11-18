/*
 * COURSE: CS 475
 * ASSIGNMENT: Topic 3 Programming Assignment
 * AUTHOR: Matthew Hartigan
 * DATE: 18-Jan-2020
 */

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.Scanner;
import java.util.LinkedList;
import java.lang.String;
import javax.swing.JOptionPane;


/**
 * This class implements a FiniteAutomata based on the definition provided
 * by a valid input file.  It then evaluates whether user input is acceptable
 * according to this definition. 
 * @author Matt
 */
public class FiniteAutomata {

    /**
     * Main function for the program.
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Greet user
        System.out.println("Welcome to the CS 475 Topic 3 Assignment!");
        System.out.println("We will be working with Finite Automatons.");
        System.out.println("Please seletion your input file.");
        
        
        // Prompt user for input file
        // BIG OH ANALYSIS OF THIS SECTION:
        //  O(1) since it will always execute in the same time / space
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Great.  We're now opening: " + chooser.getSelectedFile().getName());
        }
 
        
        // Read DFA from input file and process it
        // BIG OH ANALYSIS OF THIS SECTION:
        // O(n) because the performance will grow linearly with respect to the 
        // size of the input (e.g. if there is a long list of transitions provided)
        File inputFile = chooser.getSelectedFile();
        LinkedList<String> initialState = new LinkedList<String>();
        LinkedList<String> finalStates = new LinkedList<String>();
        LinkedList<Transition> transitions = new LinkedList<Transition>();
        try {
            Scanner sc = new Scanner(inputFile);
            
            // Parse initial state
            initialState.add(sc.nextLine());
            
            // Parse final state(s)
            String unparsedFinalStates = sc.nextLine();
            String[] parsedFinalStates = unparsedFinalStates.split(" ");
            for(int i=0; i<parsedFinalStates.length; i++) {
                finalStates.add(parsedFinalStates[i]);
            }            
            
            // Parse transition(s)
            while (sc.hasNextLine()) {
                String unparsedTransition = sc.nextLine();
                String[] parsedTransition = unparsedTransition.split(" ");
                Transition newTransition = new Transition();
                newTransition.setCurrentState(parsedTransition[0]);
                newTransition.setToken(parsedTransition[1]);
                newTransition.setNextState(parsedTransition[2]);          
                transitions.add(newTransition);
            }
        }
        catch (Exception e) {
            System.out.println("Exception while attempting to parse input file.  Exiting now.");
            System.exit(1);
        }       
            
        
        // Compile and display DFA alphabet to user
        // BIG OH ANALYSIS OF THIS SECTION:
        // O(n) because the performance will grow linearly with respect to the 
        // size of the input (e.g. if there is a long list of transitions provided)
        LinkedList<String> alphabet = new LinkedList<String>();
        alphabet.add(initialState.getFirst());    // Add initial state
        for (int k=0; k<finalStates.size(); k++) {    // Add unique final states
            if(! alphabet.contains(finalStates.get(k))) {
                alphabet.add(finalStates.get(k));
            }
        }
        for (int m=0; m<transitions.size(); m++) {    // Add unique transition elements
            if (! alphabet.contains(transitions.get(m).currentState)) {
                alphabet.add(transitions.get(m).currentState);
            }
            if (! alphabet.contains(transitions.get(m).token)) {
                alphabet.add(transitions.get(m).token);
            }
            if (! alphabet.contains(transitions.get(m).nextState)) {
                alphabet.add(transitions.get(m).nextState);
            }
        }
        
        System.out.println();
        System.out.println("Here is the input DFA's alphabet:");
        System.out.print("A = [ ");
        for (int n=0; n<alphabet.size(); n++) {
            System.out.print(alphabet.get(n) + "  ");
        }
        System.out.println("]");
        
        
        // Prompt user for input string (JOptionPane)
        String inputString = JOptionPane.showInputDialog("Please input a string.");
        
        
        // Evaluate input and display acceptance decision (JOptionPane)
        // BIG OH ANALYSIS OF THIS SECTION:
        // O(n^2) because of the nested for-loops moving through the input string
        // and the list of transitions.
        String currentState = initialState.getFirst();
        for (int q=0; q<inputString.length(); q++) {
            String currentToken = Character.toString(inputString.charAt(q));
            System.out.println("Current state: " + currentState);
            System.out.println("Current token: " + currentToken);
             for (int p=0; p<transitions.size(); p++) {
                 if (transitions.get(p).currentState.equals(currentState) && transitions.get(p).token.equals(currentToken)) {
                     currentState = transitions.get(p).nextState;
                     break;
                 }
             }
        }        
        
        // Display final verdict message to user.
        if (finalStates.contains(currentState)) {
            System.out.println("The input string '" + inputString + "' is ACCEPTED.");
        }
        else {
            System.out.println("The input string '" + inputString + "' is NOT ACCEPTED.");
        }
    }
    
}
