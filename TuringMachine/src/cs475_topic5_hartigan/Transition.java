/*
 * COURSE: CS475
 * ASSIGNMENT: Topic 5
 * AUTHOR: Matthew Hartigan
 * DATE: 2-February-2020
 * DESCRIPTION: 
 */
package cs475_topic5_hartigan;

/**
 *
 * @author Matt
 */
public class Transition {
    
    // VARIABLES
    private String fromState;
    private String input;
    private String output;
    private String direction;
    private String toState;
    
    // CONSTRUCTORS
    /**
     * Default constructor
     */
    public Transition() {
        fromState = null;
        input = null;
        output = null;
        direction = null;
        toState = null;
        // inputSymbol = null;
        // writeSymbol = null;
        // direction = null;'
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
    public String getOutput() {
        return output;
    }

    /**
     *
     * @return
     */
    public String getDirection() {
        return direction;
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
     * @param output
     */
    public void setOutput(String output) {
        this.output = output;
    }

    /**
     *
     * @param direction
     */
    public void setDirection(String direction) {
        this.direction = direction;
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
        
        String tempString = parsedTransitionString[1].substring(1, parsedTransitionString[1].length()-1);    // remove parentheses
        String[] tempStringContents = tempString.split(",");
        parsedTransition.input = tempStringContents[0];
        parsedTransition.output = tempStringContents[1];
        parsedTransition.direction = tempStringContents[2];

        parsedTransition.toState = parsedTransitionString[2];
        
        return parsedTransition;
    }
    
    /**
     * Displays the input transition.
     * @param transition
     */    
    public static void display(Transition transition) {
        System.out.print("Transition: ");
        System.out.print("[");
        System.out.print(transition.getFromState() + ", ");
        System.out.print(transition.getInput() + ", ");
        System.out.print(transition.getOutput() + ", ");
        System.out.print(transition.getDirection() + ", ");
        System.out.print(transition.getToState() + "]");
        System.out.println();
    }
}
