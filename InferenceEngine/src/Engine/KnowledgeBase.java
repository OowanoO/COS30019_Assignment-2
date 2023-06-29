package Engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KnowledgeBase 
{
    private ArrayList<HornClause> hClause; // List of Horn clauses
    private ArrayList<String> facts; // List of facts

    public KnowledgeBase()
    {

    }

    public KnowledgeBase(BufferedReader inputFile) 
    {
        this();
        readInput(inputFile);
    }

    public void readInput(BufferedReader inputFile)
     {
        try {
            inputFile.readLine();

            String tellString = inputFile.readLine();

            tellString = tellString.replaceAll("\\s", "");

            String[] sentenceStrings = tellString.split(";");

            for (String sentence : sentenceStrings)
             {
                if (sentence.contains("=>"))
                {
                    hClause.add(parseHornClause(sentence));
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
}