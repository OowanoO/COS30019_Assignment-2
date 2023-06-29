package Engine;

public abstract class LogicAlgorithms
{
    private String shortName; // The short name of algorithms
    private String fullName; // The full name of the algorithms
    private KnowledgeBase knowledgeBase; // The knowledge base used by the algorithm
    private String input; // The input question that need to be test

    // Default constructor
    public LogicAlgorithms() 
    {

    }

    // Constructor that takes a knowledge base and a question as input
    public LogicAlgorithms(String shortName, String fullName, KnowledgeBase knowledgeBase, String input) 
    {
        this.shortName = shortName;
        this.fullName = fullName;
        this.knowledgeBase = knowledgeBase;
        this.input = input;
    }

    // Returns the short name of the algorithm
    public String getShortName() 
    {
        return shortName;
    }

    // Sets the short name of the algorithm
    protected void setShortName(String sname) 
    {
        shortName = sname;
    }

    // Returns the full name of the algorithm
    public String getFullName() 
    {
        return fullName;
    }

    // Sets the full name of the algorithm
    protected void setFullName(String fname)
     {
        fullName = fname;
    }

    // Returns the knowledge base used by the algorithm
    protected KnowledgeBase getKnowledgeBase() 
    {
        return knowledgeBase;
    }

    // Sets the knowledge base used by the algorithm
    public void setKnowledgeBase(KnowledgeBase kb) 
    {
        knowledgeBase = kb;
    }

    // Returns the question to be tested by the algorithm
    protected String getInput() 
    {
        return input;
    }

    // Sets the input question to be tested by the algorithm
    public void setQuestion(String inquest) 
    {
        input = inquest;
    }

    // Abstract method to test a question using the algorithm
    abstract public String testInput();

    // Abstract method to verify facts using the algorithm
    abstract public boolean verifyFacts();
}