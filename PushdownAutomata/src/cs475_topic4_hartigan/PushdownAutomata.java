/*
 * COURSE: CS475
 * ASSIGNMENT: Topic 4
 * AUTHOR: Matthew Hartigan
 * DATE: 26-January-2020
 * DESCRIPTION: Class that represents a discrete pushdown automata.
 */
package cs475_topic4_hartigan;

import java.lang.String;
import java.io.File;
import java.util.Scanner;
import java.util.LinkedList;

/**
 *
 * @author Matt
 */
public class PushdownAutomata {
    
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
    public PushdownAutomata() {
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
    static void evaluate(String inputString) {
        System.out.println();
        System.out.println("Evaluating the validity of input string...");
        
        // Initialize 
        currentState = (initialState.get(0));    // start state
        inputStack.push("e");    // empty stack to begin
        inputString = "e" + inputString;    // prepend empty input special char
        inputString = inputString + "e";    // append empty input special char
        boolean isAccepted = false;
        
        // Loop through entire inputString
        for (int i=0; i < inputString.length(); i++) {
            String nextInputChar = inputString.substring(i, i+1);    // get next input char
            System.out.println();
            System.out.println();
            System.out.println("Current state: " + currentState);
            System.out.println("Next input: " + nextInputChar);
            System.out.println("The stack top is: " + inputStack.getFirst());
            System.out.print("The stack is:");
            for (int k=0; k < inputStack.size(); k++) {
                System.out.print(" " + inputStack.get(k));
            }
            System.out.println();
            
            for (int j=0; j < transitions.size(); j++) {    // loop through all transitions
                if ( transitions.get(j).getFromState().equals(currentState) ) {    // match "fromState"
                    
                    // Replace
                    if (transitions.get(j).getInput().equals(nextInputChar) && 
                        transitions.get(j).getStackTop().equals(inputStack.getFirst()) && 
                        !transitions.get(j).getStackOperation().equals("e")) {
                        inputStack.pop();
                        inputStack.push(transitions.get(j).getStackOperation());
                        currentState = transitions.get(j).getToState();
                        System.out.println("The operation is: replace");
                        break;
                    }

                    // Pop
                    else if (transitions.get(j).getInput().equals(nextInputChar) && 
                        transitions.get(j).getStackTop().equals(inputStack.getFirst()) && 
                        transitions.get(j).getStackOperation().equals("e") && 
                        !transitions.get(j).getStackTop().equals("$")) {
                        inputStack.pop();
                        currentState = transitions.get(j).getToState();
                        System.out.println("The operation is: pop");
                        break;
                    }                        
                    
                    // Push
                    else if (transitions.get(j).getInput().equals(nextInputChar) && 
                        transitions.get(j).getStackTop().equals("e")) {
                        inputStack.push(transitions.get(j).getStackOperation());
                        currentState = transitions.get(j).getToState();
                        System.out.println("The operation is: push");
                        break;
                    }           
                    
                    // Input matches, stackTop is empty stack special char
                    else if (transitions.get(j).getInput().equals(nextInputChar) && 
                        transitions.get(j).getStackTop().equals("$") && 
                        inputStack.getFirst().equals("$")) {
                        inputStack.push(transitions.get(j).getStackOperation());
                        currentState = transitions.get(j).getToState();
                        System.out.println("The operation is: end");

                        // Check current state is included in final states
                        if (finalStates.contains(currentState)) {
                            isAccepted = true;
                        }
                    }
                }
            }
        }
        
        if (isAccepted) {
            System.out.println();
            System.out.println("The PDA DOES accept the input string.  Congrats.");
            System.exit(0);
        }
        else {
            System.out.println();
            System.out.println("The PDA DOES NOT accept the input string.");
            System.exit(1);
        }
    }
    
    /**
     * Displays the alphabet of the given PDA..
     * @param inputFile
     */    
    static void displayAlphabet() {
        System.out.println("Here is your PDA Alphabet:");
        
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
            System.out.print(transitions.get(j).getStackTop() + ", ");
            System.out.print(transitions.get(j).getStackOperation() + ", ");
            System.out.print(transitions.get(j).getToState() + "]");
            System.out.println();
        }
        
        // Initial state
        System.out.println("Initial State: " + initialState);
        
        // Final states
        System.out.println("Final States: " + finalStates);
    }
}
