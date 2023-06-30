package Engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class KnowledgeBase {
    private ArrayList<HornClause> hornClauseList; // List of Horn clauses
    private ArrayList<String> facts; // List of facts

    public KnowledgeBase() {
        hornClauseList = new ArrayList<>();
        facts = new ArrayList<>();
    }

    public KnowledgeBase(BufferedReader reader) {
        hornClauseList = new ArrayList<>();
        facts = new ArrayList<>();

        readInput(reader);
    }

    public void readInput(BufferedReader reader) {
        try {
            reader.readLine();

            String tellString = reader.readLine();

            tellString = tellString.replaceAll("\\s", "");

            String[] sentenceStrings = tellString.split(";");

            for (String sentence : sentenceStrings) {
                if (sentence.contains("=>")) {
                    hornClauseList.add(new HornClause(sentence));
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
    public ArrayList<HornClause> getHornClauses() {
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
