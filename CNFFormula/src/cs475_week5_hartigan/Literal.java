/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs475_week5_hartigan;

/**
 *
 * @author Matt
 */
public class Literal {
    
    // FIELDS
    String name = null;
    boolean isNegated = false;
    
    // CONSTRUCTORS
    public Literal() {
    }
    
    // METHODS
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsNegated() {
        return isNegated;
    }

    public void setIsNegated(boolean isNegated) {
        this.isNegated = isNegated;
    }
    
    
}
