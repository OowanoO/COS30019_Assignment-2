package Engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
    
public class TruthTableMethod extends SearchAlgorithms
{
    //variables list 
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<HornClauses> hClauses;
    private String ask; 
    
    private int rowNum; //numbers of rows
    private int colNums; //number of columns

    public TruthTableMethod(String ask, KnowledgeBase kb)
    {
        //set the full name and the short name of algorithms
        setShortAlgorithmName("TT");
        setFullAlgorithmName("Truth Table Algorithm");

        this.ask = ask;
        this.hClauses = kb.getHornClauses();

        //get the list of the varibles 
        getVariableList(); 



    }
    
}
