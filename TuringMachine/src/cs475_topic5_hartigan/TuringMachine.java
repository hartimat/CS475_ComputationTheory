/*
 * COURSE: CS475
 * ASSIGNMENT: Topic 5
 * AUTHOR: Matthew Hartigan
 * DATE: 2-February-2020
 * DESCRIPTION: 
 */
package cs475_topic5_hartigan;

import java.lang.String;
import java.io.File;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.ArrayList;

/**
 *
 * @author Matt
 */
public class TuringMachine {
    
    // VARIABLES
    static LinkedList<String> initialState = new LinkedList<String>();
    static LinkedList<String> finalStates = new LinkedList<String>();
    static LinkedList<Transition> transitions = new LinkedList<Transition>();
    static LinkedList<String> inputStack = new LinkedList<String>();
    static String currentState = new String();
    
    
    // CONSTRUCTORS
    /**
     * Default constructor
     */
    public TuringMachine() {
    }
    
    
    // METHODS
    /**
     * Parses an input file into a PDA.
     * BIG-O ANALYSIS:
     * There are two loops in this function.  It is O(n +m); however, we would typically
     * expect the "n" to be small in comparison to the "m" because they represent 
     * final states and transitions respectively.  Therefore it is more likely O(m).
     * @param inputFile
     */
    public void parse(File inputFile) {
        try {
            Scanner sc = new Scanner(inputFile);
            
            initialState.add(sc.nextLine());    // initial state
            String rawFinalStates = sc.nextLine();    // final states
            String[] parsedFinalStates = rawFinalStates.split(" ");
            for (int i=0; i<parsedFinalStates.length; i++) {
                finalStates.add(parsedFinalStates[i]);
            }
            while (sc.hasNextLine()) {    // transitions
                transitions.add(Transition.parse(sc.nextLine()));
                transitions.add(Transition.parse(sc.nextLine()));
            }
        }
        catch (Exception ex) {
            System.out.println("Exception while attempting to parse input file.  Exiting now.");
            System.exit(99);
        }
    }
    
    /**
     * Evaluates whether the given input string is compliant with the PDA.
     * BIG-O ANALYSIS:
     * There are nested for loops in this function.  It is O(n*m) since the two
     * loops are for the input string and transition list respectively.  This is equivalent
     * to the size of the loop for the finite automaton from Week 1 (at least in terms of
     * Big-O).
     * @param inputString
     */
    static void evaluate(String inputString, Tape tape) {
        System.out.println();
        System.out.println("Evaluating the validity of input string...");
        
        // Initialize 
        currentState = (initialState.get(0));    // start state
        
        // Loop through entire inputString
        for (int i=0; i < inputString.length(); i++) {
            String nextInputChar = inputString.substring(i, i+1);    // get next input char
            System.out.println();
            System.out.println("Current state: " + currentState);
            System.out.println("Next input: " + nextInputChar);
            
            for (int j=0; j < transitions.size(); j++) {    // loop through all transitions
                if ( transitions.get(j).getFromState().equals(currentState) ) {    // match "fromState"
                    
                    // Replace
                    if (transitions.get(j).getInput().equals(nextInputChar) && 
                        !transitions.get(j).getOutput().equals("")) {
                        
                        // Adjust state
                        currentState = transitions.get(j).getToState();
                        
                        // Adjust tape
                        int headPosition = tape.getHeadPosition();
                        ArrayList<String> cells = tape.getCells();
                        cells.set(headPosition, transitions.get(j).getOutput());
                        tape.setCells(cells);
                        
                        // Adjust head position
                        if (transitions.get(j).getDirection().equals("R")) {
                            tape.setHeadPosition(tape.getHeadPosition() + 1);
                        }
                        else if (transitions.get(j).getDirection().equals("L")) {
                            if (tape.getHeadPosition() != 0) {    // ensure not at far left of tape
                                tape.setHeadPosition(tape.getHeadPosition() - 1);
                            }
                        }
                        System.out.println("The operation is: replace");
                        Transition.display(transitions.get(j));
                        System.out.println("New head position: " + tape.getHeadPosition());
                        tape.displayCells();
                        System.out.println();
                        break;
                    }
                    
                    // Skip
                    if (transitions.get(j).getInput().equals(nextInputChar) && 
                        transitions.get(j).getOutput().equals("")) {
                        
                        // Adjust state
                        currentState = transitions.get(j).getToState();
                        
                        // Adjust head position
                        if (transitions.get(j).getDirection().equals("R")) {
                            tape.setHeadPosition(tape.getHeadPosition() + 1);
                        }
                        else if (transitions.get(j).getDirection().equals("L")) {
                            if (tape.getHeadPosition() != 0) {    // ensure not at far left of tape
                                tape.setHeadPosition(tape.getHeadPosition() - 1);
                            }
                        }
                        System.out.println("The operation is: skip");
                        Transition.display(transitions.get(j));
                        System.out.println("New head position: " + tape.getHeadPosition());
                        tape.displayCells();
                        System.out.println();
                        break;
                    }
                }
            }
        }
        
        if (finalStates.contains(currentState)) {
            System.out.println();
            System.out.println("The Turing Machine DOES accept the input string.  Congrats.");
            System.exit(0);
        }
        else {
            System.out.println();
            System.out.println("The Turing Machine DOES NOT accept the input string.");
            System.exit(1);
        }
    }
    
    /**
     * Displays the alphabet of the given PDA..
     * @param inputFile
     */    
    static void displayAlphabet() {
        System.out.println("Here is your Turing  Alphabet:");
        
        // List of all states
        LinkedList<String> allStates = new LinkedList<>();
        
        allStates.add(initialState.get(0));    // pull from initial state
        
        for (int i=0; i<finalStates.size(); i++) {    // pull from final states
            if (!allStates.contains(finalStates.get(i))) {
                allStates.add(finalStates.get(i));
            }
        }
        
        for (int i=0; i<transitions.size(); i++) {    // pull from transitions
            if (!allStates.contains(transitions.get(i).getToState())) {
                allStates.add(transitions.get(i).getToState());
            }     
            if (!allStates.contains(transitions.get(i).getFromState())) {
                allStates.add(transitions.get(i).getFromState());
            }    
        }
        
        System.out.print("Set of states: ");
        for (int i=0; i<allStates.size(); i++) {
            System.out.print(" " + allStates.get(i));
        }
        System.out.println();
        
        // List of input alphabet
        LinkedList<String> inputAlphabet = new LinkedList<>();
        
        for (int i=0; i<transitions.size(); i++) {    // pull from transitions
            if (!inputAlphabet.contains(transitions.get(i).getInput())) {
                inputAlphabet.add(transitions.get(i).getInput());
            }     
        }
        
        System.out.print("Input alphabet: ");
        for (int i=0; i<inputAlphabet.size(); i++) {
            System.out.print(" " + inputAlphabet.get(i));
        }
        System.out.println();
        
        // Transitions
        System.out.println("Transitions: ");
        for (int j=0; j < transitions.size(); j++) {
            System.out.print("[");
            System.out.print(transitions.get(j).getFromState() + ", ");
            System.out.print(transitions.get(j).getInput() + ", ");
            System.out.print(transitions.get(j).getOutput() + ", ");
            System.out.print(transitions.get(j).getDirection() + ", ");
            System.out.print(transitions.get(j).getToState() + "]");
            System.out.println();
        }
        
        // Initial state
        System.out.println("Initial State: " + initialState);
        
        // Final states
        System.out.println("Final States: " + finalStates);
    }
}
