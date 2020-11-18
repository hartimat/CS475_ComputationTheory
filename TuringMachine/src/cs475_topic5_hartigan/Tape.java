/*
 * COURSE: CS475
 * ASSIGNMENT: Topic 5
 * AUTHOR: Matthew Hartigan
 * DATE: 2-February-2020
 * DESCRIPTION: 
 */
package cs475_topic5_hartigan;

import java.util.ArrayList;

/**
 *
 * @author Matt
 */
public class Tape {
    private int headPosition = 0;
    private ArrayList<String> cells = null;

    // CONSTRUCTORS
    public Tape(String input) {
        cells = new ArrayList<String>();
        for (int i=0; i < input.length(); i++) {
            cells.add(Character.toString(input.charAt(i)));
        }
}

    // GETTERS AND SETTERS
    public int getHeadPosition() {
        return headPosition;
    }

    public void setHeadPosition(int headPosition) {
        this.headPosition = headPosition;
    }

    public ArrayList<String> getCells() {
        return cells;
    }

    public void setCells(ArrayList<String> cells) {
        this.cells = cells;
    }
    
    // METHODS
    public void displayCells () {
        System.out.print("Displaying tape: ");
        for (int i=0; i<cells.size(); i++) {
            System.out.print(cells.get(i) + " ");
        }
    }

}
