/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs475_week5_hartigan;

import java.util.ArrayList;

/**
 *
 * @author Matt
 */
public class Clause {
    
    // FIELDS
    String expression = null;
    ArrayList<Literal> literals = null;
    
    // CONSTRUCTORS
    public Clause(String inputExpression) {
        expression = inputExpression;
    }
    
    // METHODS
    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public ArrayList<Literal> getLiterals() {
        return literals;
    }

    public void setLiterals(ArrayList<Literal> literals) {
        this.literals = literals;
    }
    
    public boolean verify(Assignment assignment) {
        return false;
    }
    
    public String[] literals() {
        String[] literals = null;
        return literals;
    }
}
