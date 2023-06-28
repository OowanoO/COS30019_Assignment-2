package Engine;

public abstract class LogicAlgorithm {
    private String algorithmCode; // The code of the algorithm
    private String fullName; // The full name of the algorithm
    private KnowledgeBase knowledgeBase; // The knowledge base used by the algorithm
    private String question; // The question to be tested by the algorithm

    // Default constructor
    public LogicAlgorithm() {}

    // Constructor that takes a knowledge base and a question as input
    public LogicAlgorithm(String algorithmCode, String fullName, KnowledgeBase knowledgeBase, String question) {
        this.algorithmCode = algorithmCode;
        this.fullName = fullName;
        this.knowledgeBase = knowledgeBase;
        this.question = question;
    }

    // Returns the code of the algorithm
    public String getAlgorithmCode() {
        return algorithmCode;
    }

    // Sets the code of the algorithm
    protected void setAlgorithmCode(String code) {
        algorithmCode = code;
    }

    // Returns the full name of the algorithm
    public String getFullName() {
        return fullName;
    }

    // Sets the full name of the algorithm
    protected void setFullName(String name) {
        fullName = name;
    }

    // Returns the knowledge base used by the algorithm
    protected KnowledgeBase getKnowledgeBase() {
        return knowledgeBase;
    }

    // Sets the knowledge base used by the algorithm
    public void setKnowledgeBase(KnowledgeBase kb) {
        knowledgeBase = kb;
    }

    // Returns the question to be tested by the algorithm
    protected String getQuestion() {
        return question;
    }

    // Sets the question to be tested by the algorithm
    public void setQuestion(String q) {
        question = q;
    }

    // Abstract method to test a question using the algorithm
    abstract public String testQuestion();

    // Abstract method to verify facts using the algorithm
    abstract public boolean verifyFacts();
}
