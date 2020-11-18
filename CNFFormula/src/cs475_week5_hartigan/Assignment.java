/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs475_week5_hartigan;

import java.util.HashMap;

/**
 *
 * @author Matt
 */
public class Assignment {
    
    // FIELDS
    CnfFormula formula = new CnfFormula();
    HashMap<String, Boolean> assignments = new HashMap<>();
    
    // CONSTRUCTORS
    public Assignment(CnfFormula inputFormula) {
        formula = inputFormula;
    }
    
    // METHODS
    public CnfFormula getFormula() {
        return formula;
    }

    public void setFormula(CnfFormula formula) {
        this.formula = formula;
    }

    public HashMap<String, Boolean> getAssignments() {
        return assignments;
    }

    public void setAssignments(HashMap<String, Boolean> assignments) {
        this.assignments = assignments;
    }
    
    public boolean getValue(String var) {
        return assignments.get(var);
    }
    
    public void setValue(String var, boolean val) {
        assignments.put(var, val);
    }
    
    public String[] variables() {
        String[] variableList = new String[formula.literals.size()];
        for (int i = 0; i < formula.literals.size(); i++) {
            variableList[i] = formula.literals.get(i).getName();
        }
        return variableList;
    }
    
}
