/*
 * COURSE: CS475
 * ASSIGNMENT: Topic 4
 * AUTHOR: Matthew Hartigan
 * DATE: 26-January-2020
 * DESCRIPTION: Class that represents pushdown automata transitions.
 */
package cs475_topic4_hartigan;

/**
 *
 * @author Matt
 */
public class Transition {
    
    // VARIABLES
    private String fromState;
    private String input;
    private String stackTop;
    private String stackOperation;
    private String toState;
    
    
    // CONSTRUCTORS
    /**
     * Default constructor
     */
    public Transition() {
    }
    
    
    // GETTERS AND SETTERS
    /**
     *
     * @return
     */
    public String getToState() {
        return toState;
    }
    
    /**
     *
     * @return
     */
    public String getFromState() {
        return fromState;
    }

    /**
     *
     * @return
     */
    public String getInput() {
        return input;
    }

    /**
     *
     * @return
     */
    public String getStackTop() {
        return stackTop;
    }

    /**
     *
     * @return
     */
    public String getStackOperation() {
        return stackOperation;
    }

    /**
     *
     * @param fromState
     */
    public void setFromState(String fromState) {
        this.fromState = fromState;
    }

    /**
     *
     * @param input
     */
    public void setInput(String input) {
        this.input = input;
    }

    /**
     *
     * @param stackTop
     */
    public void setStackTop(String stackTop) {
        this.stackTop = stackTop;
    }

    /**
     *
     * @param stackOperation
     */
    public void setStackOperation(String stackOperation) {
        this.stackOperation = stackOperation;
    }

    /**
     *
     * @param toState
     */
    public void setToState(String toState) {
        this.toState = toState;
    }

    
    // METHODS
    /**
     * Parses a transition from given input string.
     * @param inputLine
     * @return
     */
    public static Transition parse(String inputLine) {
        Transition parsedTransition = new Transition();
        
        String[] parsedTransitionString  = inputLine.split(" ");
        parsedTransition.fromState = parsedTransitionString[0];
        parsedTransition.input = parsedTransitionString[1].substring(1);
        parsedTransition.stackTop = parsedTransitionString[2];
        parsedTransition.stackOperation = parsedTransitionString[3].substring(0, parsedTransitionString[3].length()-1);
        parsedTransition.toState = parsedTransitionString[4];
        
        return parsedTransition;
    }
}
