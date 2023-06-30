package Engine;

import java.util.ArrayList;

public class HornClause {
    private ArrayList<String> literals; // List of literals in the clause
    private String inferred; // The inferred literal

    // Constructor that takes a sentence as input and splits it into literals and inferred literal
    public HornClause(String sentence) {
        literals = new ArrayList<String>();

        String[] splitByEntailment = sentence.split("=>");

        String[] splitByConjunction = splitByEntailment[0].split("&");
        for (String literal : splitByConjunction) {
            literals.add(literal.trim());
        }

        inferred = splitByEntailment[1].trim();
    }

    // Returns the list of literals
    public ArrayList<String> getLiterals() {
        return literals;
    }

    // Returns the literal at the specified index
    public String getLiteralAtIndex(int index) {
        if (index >= 0 && index < literals.size()) {
            return literals.get(index);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    // Returns the number of literals in the clause
    public int countLiterals() {
        return literals.size();
    }

    // Deletes the specified literal from the list of literals
    public void deleteLiteral(String literal) {
        literals.remove(literal);
    }

    // Returns the inferred literal
    public String getInferred() {
        return inferred;
    }

    // Returns a string representation of the Horn clause
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < literals.size(); i++) {
            if (i != 0) {
                sb.append("&");
            }

            sb.append(literals.get(i));
        }

        sb.append("=>");
        sb.append(inferred);
        return sb.toString();
    }
}
