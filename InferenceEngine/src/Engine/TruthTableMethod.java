package Engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
    
public class TruthTableMethod extends LogicAlgorithms
{
    //variables list 
    private ArrayList<String> varList = new ArrayList<String>();

    private ArrayList<HornClauses> hClauses;
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

    public TruthTableMethod(String ask, KnowledgeBase kb)
    {
        //set the full name and the short name of algorithms
        setShortName("TT");
        setFullName("Truth Table Algorithm");

        this.ask = ask;
        this.hClauses = kb.getHornClauses();

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

        this.colLiteralIndices = new int[hClauses.size()][2];
        this.colFactIndices = new int[colFactIndices.size()]; 
        this.colArrayValue = new int[hClauses.size()];
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
                    if(hClauses.get(j).countLiteral() == 2)
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
    }

    for(int i = 0; i < rowNum; i++)
    {
        
    }
    
}
