package Engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
    
public class TruthTableMethod extends LogicAlgorithms
{
    //variables list 
    private ArrayList<String> varList = new ArrayList<String>();

    private ArrayList<HornClause> hClause;
    private String ask; 
    private ArrayList<String> factList;

    private boolean[][] truthTable;
    private boolean[] algorithmResults;
    
    private int rowNum; //numbers of rows
    private int colNums; //number of columns

    private int[][] colLiteralIndices;
    private int[] colFactIndices;
    private int[] colArrayValue; 

    private boolean[] inferenceResults;
    private int queryIndexVar = 0;
    private int counter = 0; 

    public TruthTableMethod(KnowledgeBase kb, String ask)
    {
        //set the full name and the short name of algorithms
        setShortName("TT");
        setFullName("Truth Table Algorithm");

        this.ask = ask;
        this.hClause = kb.getHornClause();
        this.factList = kb.getFacts();

        //get the list of the varibles 
        getVariableList(); 

        this.rowNum = (int) Math.pow(2,(varList.size()));
        this.colNums = varList.size();

        //define the truth table parameter
        this.truthTable = new boolean[rowNum][colNums];

        this.algorithmResults = new boolean[rowNum];
        for(int i = 0; i < rowNum; i++)
        {
            algorithmResults[i] = true;
        }

        this.colLiteralIndices = new int[hClause.size()][2];
        this.colFactIndices = new int[factList.size()]; 
        this.colArrayValue = new int[hClause.size()];
        this.inferenceResults = new boolean[rowNum];

        populateTruthTable();
        getColumnIndices(); 

    }

    public String testInput()
    {
        String testOutput;

        if(verifyFacts())
        {
            testOutput = "YES: " + counter;
        }

        else 
        {
            testOutput = "No: It cannot be prove " + ask;
        }

        return testOutput;

    }

    public boolean verifyFacts()
    {
        for(int i = 0; i < rowNum; i++)
        {
            for(int j = 0; j < colFactIndices.length; j++)
            {
                if(algorithmResults[i])
                {
                    if(truthTable[i][queryIndexVar])
                    {
                        algorithmResults[i] = true;
                        inferenceResults[i] = true; 

                        break; 
                    }

                    else
                    {
                        inferenceResults[i] = false;
                    }

                    algorithmResults[i] = truthTable[i][colFactIndices[j]];
                }

                else 
                {
                    break; 
                }
            }
        }

        for(int i = 0; i < rowNum; i++)
        {
            if(algorithmResults[i])
            {
                for (int j = 0; j < colLiteralIndices.length; j++) 
                {
                    if(hClause.get(j).countLiterals() == 2)
                    {
                        if(truthTable[i][colLiteralIndices[j][0]] && truthTable[i][colLiteralIndices[j][1]] && !truthTable[i][colArrayValue[j]])
                        {
                            algorithmResults[i] = false;
                        }
                    }

                    else 
                    {
                        if (truthTable[i][colLiteralIndices[j][0]] && !truthTable[i][colArrayValue[j]])
                         {
                            algorithmResults[i] = false;
                         }
                    }
                }
            }
        }

    for(int i = 0; i < rowNum; i++)
    {
        if(algorithmResults[i])
        {
            counter++;
        }

        else if (!inferenceResults[i] && algorithmResults[i])
        {
            return false;
        }
    }

    return true;
}

public void getVariableList()
{
    for (HornClause clause : hClause) 
    {
      for (String literal : clause.getLiterals()) 
      {
        varList.add(literal);
      }

      varList.add(clause.getInferred());
    }

    Set<String> uniVars = new HashSet<>(varList);
    varList.clear();

    varList.addAll(uniVars);

}

public void populateTruthTable() {

    // Loop through each row and column of the truth grid and set its value based on
    // a bitwise operation
    for (int i = 0; i < rowNum; i++)
     {
      for (int j = 0; j < colNums; j++) 
      {
        int t = i & 1 << colNums - 1 - j;

        truthTable[i][j] = (t == 0);

      }
    }
  }

public void getColumnIndices() {

    // Loop through each fact and variable in varList
    for (int i = 0; i < factList.size(); i++) 
    {
      for (int j = 0; j < varList.size(); j++)
       {
        // If the fact equals the variable, set the value of factIndices at index i to j
        if (factList.get(i).equals(varList.get(j)))
        {
          colFactIndices[i] = j;
        }

        // If the query string equals the variable, set queryIndexNum to j
        if (ask.equals(varList.get(j))) 
        {
          queryIndexVar = j;
        }
      }
    }

    for (int i = 0; i < varList.size(); i++) 
    {
      for (int j = 0; j < hClause.size(); j++)
       {

        // Loop through each literal in the horn clause
        for (int k = 0; k < hClause.get(j).countLiterals(); k++) 
        {

          // If the literal equals the variable, set the value of literalIndices at index
          // [j][k] to i
          if (hClause.get(j).getLiterals().get(k).equals(varList.get(i))) 
          {
            colLiteralIndices[j][k] = i;
          }
        }

        // If the inferred value of the horn clause equals the variable, set the value
        // of entailedArray at index j to i
        if (hClause.get(j).getInferred().equals(varList.get(i))) 
        {
          colArrayValue[j] = i;
        }
      }
    }
  }
}

