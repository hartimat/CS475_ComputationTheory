/*
 * COURSE: CS475
 * ASSIGNMENT: Week 5 & 7
 * AUTHOR: Matthew Hartigan
 * DATE: 16-February-2020
 * DESCRIPTION: CNF satisfiability assignment
 */
package cs475_week5_hartigan;

import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;

/**
 *
 * @author Matt
 */
public class SatNpApp {
    
    // METHODS
    /**
     * Reads the input file selected from the dialog box.
     * BIG-O ANALYSIS: not required
     */    
    public static JFileChooser read () {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Great.  We're now opening: " + chooser.getSelectedFile().getName());
            System.out.println();
        }
        return chooser;
    }
    
      /**
     * Parses the input file
     * BIG-O ANALYSIS: not required
     */  
    public static CnfFormula parseCnfFormula(File inputFile) {

        // Separate input into clauses
        ArrayList<Clause> clauseList = new ArrayList<>();
        try{
            Scanner scanner = new Scanner (inputFile);
            String[] inputLine = scanner.nextLine().split(" \\^ ");
            scanner.close();

            for (int i=0; i<inputLine.length; i++) {
                Clause clause = new Clause(inputLine[i]);
                clauseList.add(clause);
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("File not found.  Error.  Exiting now.");
            System.exit(-1);
        }
        
        // Separate into literals within each clause
        for (int i=0; i < clauseList.size(); i ++) {
            String currentExpr = clauseList.get(i).getExpression();
            
            // remove parentheses
            currentExpr = currentExpr.trim();
            if (currentExpr.contains("(")) {
                currentExpr = currentExpr.substring(1);
            }
            if (currentExpr.contains(")")) {
                currentExpr = currentExpr.substring(0, currentExpr.length() -1 );
            }
            
            // parse unique literal elements
            String [] rawLiterals = currentExpr.split(" v ");
            ArrayList<Literal> processedLiterals = new ArrayList<>();
            
            for (int j=0; j<rawLiterals.length; j++) {
                
                Literal newLiteral = new Literal();
                boolean isNegative = false;
                boolean exists = false;
                
                if (rawLiterals[j].contains("n")) {    // detect negation
                    String base = rawLiterals[j].substring(1);
                    newLiteral.setName(base);
                    isNegative = true;
                }
                else {
                    newLiteral.setName(rawLiterals[j]);
                }
                
                for (int k=0; k<processedLiterals.size(); k++) {
                    String name = processedLiterals.get(k).getName();
                    if (newLiteral.getName().equals(name)) {
                        exists = true;
                    }
                }
                
                if (!exists) {
                    if (isNegative) {
                        newLiteral.setIsNegated(true);
                    }
                    processedLiterals.add(newLiteral);
                }
            }
            
            // update clause object with ArrayList of literals
            clauseList.get(i).setLiterals(processedLiterals);
        }
        
        // Transfer parsed clause lists to CNF Formula object
        CnfFormula formula = new CnfFormula();
        formula.setClauses(clauseList);
        ArrayList<Literal> fullLiteralList = new ArrayList<>();
        
        for (int k=0; k<clauseList.size(); k++) {
            for (int m=0; m<clauseList.get(k).getLiterals().size(); m++) {
                fullLiteralList.add(clauseList.get(k).literals.get(m));
            }
        }
        
        // Remove duplicate literals
        ArrayList<Literal> noDuplicateLiteralList = new ArrayList<>();
        for (int p=0; p<fullLiteralList.size(); p++) {
            String literalName = fullLiteralList.get(p).name;
            
            if (noDuplicateLiteralList.isEmpty()) {
                noDuplicateLiteralList.add(fullLiteralList.get(p));
            }
            else {
                boolean exists = false;
                for (int q=0; q<noDuplicateLiteralList.size(); q++) {
                    if (literalName.equals(noDuplicateLiteralList.get(q).getName())) {
                        exists = true;
                    }
                }
                
                if (!exists) {
                    noDuplicateLiteralList.add(fullLiteralList.get(p));
                }
            }
        }
      
        formula.setLiterals(noDuplicateLiteralList);
        return formula;
    }
    
      /**
     * Determines whether the input formula is satisfiable at all
     * BIG-O ANALYSIS: There are two main for loops in this function, one of 
     * which contains another nested for loop.  Therefore this function can be 
     * considered O(m + n*o)
     */  
    public static boolean isSatisfiable(Assignment assignment) {
        
        System.out.println("");
        System.out.println("Is the input CNF formula satisfiable at all?");
        System.out.println("Let's check for the following possible assignment permutations (0=false, 1=true):");
            
        // Generate a list of all the possible true / false combinations based on the number of variables
        Assignment testAssignment = assignment;
        String[] combos = new String[(int) Math.pow(2, testAssignment.formula.literals.size())];
        
        for (int i = 0; i < Math.pow(2, testAssignment.formula.literals.size()); i++) {
           String bin = Integer.toBinaryString(i);
           while (bin.length() < testAssignment.formula.literals.size()) {
               bin = "0" + bin;
           }
           combos[i] = bin;
           System.out.println(bin);
        }
        System.out.println();
        
        // Test the formula with each permutation until a satisfiable assignment is found
        ArrayList<Literal> literals = testAssignment.formula.literals;    // names of all literals in the formula
        
        for (int j = 0; j < combos.length; j++) {    // for each permutation...
            System.out.println("Permutation #" + j + ": " + combos[j]);
            
            // update the literal T/F assignments to match the permutation
            for (int k = 0; k < combos[j].length(); k++) {
                if (combos[j].charAt(k) == '0') {    // false
                    testAssignment.assignments.put(literals.get(k).getName(), false);
                }
                else if (combos[j].charAt(k) == '1') {    // true
                    testAssignment.assignments.put(literals.get(k).getName(), true);
                }
            }
            
            //then evaluate the expression for those assignments
            if (testAssignment.formula.verify(testAssignment)) {
                System.out.println("The first assignment scheme that satisfies the input formula was found!");
                System.out.println("Note: There may be other schemes that work too.");
                System.out.println("Here are the successful literal assignments:");
                System.out.println(testAssignment.assignments);
                return true;
            }
            
            System.out.println();
        }
        // Return the result
        return false;
    }
    
    /**
     * Main function.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Greet User
        System.out.println("Welcome to the CS 475 Week 5 Assignment!");
        System.out.println("We will be working with Conjunctive Normal Form formulas.");
        System.out.println("Please select your input file.");
        System.out.println();
        
        // Read cnf-formula from file
        AssignmentView view = new AssignmentView();
        File inputFile = read().getSelectedFile();
        
        // Parse and validate the input cnf-formula
        try {
            CnfFormula formula = parseCnfFormula(inputFile);    // parse
            
            Assignment assignment = new Assignment(formula);
            for (int i=0; i<formula.literals.size(); i++) {
                assignment.assignments.put(formula.literals.get(i).getName(), false);
            }

            view.setModel(assignment);
            view.setVisible(true);
            System.out.println("Evaluate whether the selected input values satisfy the formula...");


            if (formula.verify(assignment)) {    // validate
                JOptionPane.showMessageDialog(null, "Satisfied!");
            }
            else {
                JOptionPane.showMessageDialog(null, "NOT Satisfied!");
            }
        }
        catch (Exception e) {
            System.out.println("An exception occurred while validating the input CNF formula.  Exiting now.");
            System.exit(-1);
        }
        
        // Assess whether the formula is satisfiable at all
        CnfFormula formula = parseCnfFormula(inputFile);    // parse
        Assignment assignment = new Assignment(formula);
        for (int i=0; i<formula.literals.size(); i++) {
                assignment.assignments.put(formula.literals.get(i).getName(), false);
            }
        isSatisfiable(assignment);

        // Exit program
        System.exit(0);
    }    
}
