package Engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class KnowledgeBase 
{
    private ArrayList<HornClause> hClauseList; // List of Horn clauses
    private ArrayList<String> facts; // List of facts

    public KnowledgeBase()
    {
         hClauseList = new ArrayList<HornClause>();
         facts = new ArrayList<String>();
    }

    public KnowledgeBase(BufferedReader reader) 
    {
        hClauseList = new ArrayList<HornClause>();
        facts = new ArrayList<String>();

        readInput(reader);
    }

    public void readInput(BufferedReader reader)
     {
        try
         {
            reader.readLine();

            String tellString = reader.readLine();

            tellString = tellString.replaceAll("\\s", "");

            String[] sentenceStrings = tellString.split(";");

            for (String sentence : sentenceStrings)
             {
                if (sentence.contains("=>"))
                {
                    hClauseList.add(new HornClause(sentence));
                } 
                else
                 {
                    facts.add(sentence);
                }
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            System.exit(2);
        }
    }

    // Returns the list of Horn clauses
    public ArrayList<HornClause> getHornClause() 
    {
        return hClauseList;
    }

    // Returns the list of facts
    public ArrayList<String> getFacts()
     {
        return facts;
    }

    // Returns a string representation of the knowledge base
    @Override
    public String toString() 
    {
        StringBuilder sb = new StringBuilder();

        int count = hClauseList.size();

        sb.append("Printing ").append(count).append(" Horn clauses...\n");

        for (HornClause hClauseList : hClauseList) {
            sb.append(hClauseList).append("\n");
        }

        sb.append("Printed ").append(count).append(" Horn clauses.\n");

        count = facts.size();

        sb.append("Printing ").append(count).append(" facts...\n");

        for (String fact : facts) 
        {
            sb.append(fact).append("\n");
        }

        sb.append("Printed ").append(count).append(" facts.\n");

        return sb.toString();
    }
}