package Engine;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KnowledgeBase {
    private List<HornClause> hornClauseList; // List of Horn clauses
    private List<String> facts; // List of facts

    // Default constructor
    public KnowledgeBase() {
        hornClauseList = new ArrayList<>();
        facts = new ArrayList<>();
    }

    // Constructor that takes a BufferedReader as input and reads the input from it
    public KnowledgeBase(BufferedReader reader) {
        this();
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
                    hornClauseList.add(parseHornClause(sentence));
                } else {
                    facts.add(sentence);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
        }
    }

    // Parses a string representation of a Horn clause into a HornClause object
    private HornClause parseHornClause(String hornClauseString) {
        String[] parts = hornClauseString.split("=>");
        String inferred = parts[1].trim();

        String literalsString = parts[0].trim();
        String[] literalsArray = literalsString.split("&");
        List<String> literals = new ArrayList<>();
        for (String literal : literalsArray) {
            literals.add(literal.trim());
        }

        return new HornClause(literals, inferred);
    }

    // Returns the list of Horn clauses
    public List<HornClause> getHornClauses() {
        return hornClauseList;
    }

    // Returns the list of facts
    public List<String> getFacts() {
        return facts;
    }

    // Returns a string representation of the knowledge base
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        int count = hornClauseList.size();

        sb.append("Printing ").append(count).append(" Horn clauses...\n");

        for (HornClause hornClause : hornClauseList) {
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
