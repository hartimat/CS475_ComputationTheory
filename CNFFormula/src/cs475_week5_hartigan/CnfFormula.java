/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs475_week5_hartigan;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Matt
 */
public class CnfFormula {
    
    // FIELDS
    ArrayList<Literal> literals = null;
    ArrayList<Clause> clauses = null;
    
    // METHODS
    public ArrayList<Clause> getClauses() {
        return clauses;
    }

    public void setClauses(ArrayList<Clause> clauses) {
        this.clauses = clauses;
    }
    
    public ArrayList<Literal> getLiterals() {
        return literals;
    }

    public void setLiterals(ArrayList<Literal> literals) {
        this.literals = literals;
    }
    
    
     /**
     * Verifies whether the formula is valid based on the input assignment.
     * BIG-O ANALYSIS: There are two main for loops in this function, one of which
     * is nested.  Therefore, we can say the function is O(n*m + o)
     */  
    public boolean verify(Assignment assignment) {
        
        System.out.println("Validating now...");
        boolean[] clauseResults = new boolean[assignment.formula.clauses.size()];    // holds results for each clause in formula
        
        for (int i=0; i<assignment.formula.clauses.size(); i++) {    // for each clause in the formula
            boolean[] literalValues = new boolean[assignment.formula.clauses.get(i).literals.size()];  // create a boolean array based on the number of literals in each clause
            
            for (int j=0; j<assignment.formula.clauses.get(i).literals.size(); j++) {    // for each literal in the clause
                literalValues[j] = assignment.assignments.get(assignment.formula.clauses.get(i).literals.get(j).name);
                
                // check if it is negated, then update
                if (assignment.formula.clauses.get(i).literals.get(j).isNegated) {
                    literalValues[j] = ! literalValues[j];
                }
            }
            
            // evaluate each individual expression result
            boolean expressionResult = false;
            for (int k=0; k<literalValues.length; k++) {
                if (literalValues[k] == true) {    // search for any true result (makes the "OR" condition positive)
                    expressionResult = true;
                }
            }
            System.out.println("Expression " + i + " result: " + expressionResult);
            clauseResults[i] = expressionResult;
        }
        
        // evaluate the overall formula result
        boolean formulaResult = true;
        for (int m=0; m<clauseResults.length; m++) {
            if (clauseResults[m] == false) {
                formulaResult = false;
            }
        }
        System.out.println("Does the selection satsify the formula (T/F)???  " + formulaResult);
        
        return formulaResult;
    }
}
