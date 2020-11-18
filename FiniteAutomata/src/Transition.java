/*
 * COURSE: CS 475
 * ASSIGNMENT: Topic 3 Programming Assignment
 * AUTHOR: Matthew Hartigan
 * DATE: 18-Jan-2020
 */

import java.lang.String;

/**
 * This class represents the Transition of a DFA.
 * @author Matt
 */
public class Transition {
    // Fields
    String currentState;
    String token;   
    String nextState;
    
    // Constructors
    /**
     *
     */
    public Transition() {
    }
    
    /**
     *
     * @param state1
     * @param token
     * @param state2
     */
    public Transition(String state1, String token, String state2) {
        this.currentState = state1;
        this.token = token;
        this.nextState = state2;
    }
    
    // Methods
    /**
     *
     * @return
     */
    public String getCurrentState() {
        return this.currentState;
    }
    
    /**
     *
     * @return
     */
    public String getToken() {
        return this.token;
    }
    
    /**
     *
     * @return
     */
    public String getNextState() {
        return this.nextState;
    }
    
    /**
     *
     * @param state
     */
    public void setCurrentState(String state) {
        this.currentState = state;
    }
    
    /**
     *
     * @param token
     */
    public void setToken (String token) {
        this.token = token;
    }
    
    /**
     *
     * @param state
     */
    public void setNextState (String state) {
        this.nextState = state;
    }

}
