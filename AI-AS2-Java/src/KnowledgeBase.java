import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class KnowledgeBase {
    private ArrayList<HornClauses> hornClauseList; // List of Horn clauses
    private ArrayList<String> facts; // List of facts

    // Default constructor
    public KnowledgeBase() {
        hornClauseList = new ArrayList<HornClauses>();
        facts = new ArrayList<String>();
    }

    // Constructor that takes a BufferedReader as input and reads the input from it
    public KnowledgeBase(BufferedReader reader) {
        hornClauseList = new ArrayList<HornClauses>();
        facts = new ArrayList<String>();

        readInput(reader);
    }

    // Reads the input from the BufferedReader and populates the list of Horn clauses and facts
    public void readInput(BufferedReader reader) {
        try {
            reader.readLine();

            String tellString = reader.readLine();

            tellString = tellString.replaceAll("\\s", "");

            String[] sentenceStrings = tellString.split(";");

            for (String sentence : sentenceStrings) {
                if (sentence.contains("=>")) {
                    hornClauseList.add(new HornClauses(sentence));
                } else {
                    facts.add(sentence);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
        }
    }

    // Returns the list of Horn clauses
    public ArrayList<HornClauses> getHornClauses() {
        return hornClauseList;
    }

    // Returns the list of facts
    public ArrayList<String> getFacts() {
        return facts;
    }

    // Returns a string representation of the knowledge base
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        int count = hornClauseList.size();

        sb.append("Printing ").append(count).append(" Horn clauses...\n");

        for (HornClauses hornClause : hornClauseList) {
            sb.append(hornClause).append("\n");
        }

        sb.append("Printed ").append(count).append(" Horn clauses.\n");

        count = facts.size();

        sb.append("Printing ").append(count).append(" facts...\n");

        for (String fact : facts) {
            sb.append(fact).append("\n");
        }

        sb.append("Printed ").append(count).append(" facts.\n");

        return sb.toString();
    }
}