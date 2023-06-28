import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// This class extends the LogicAlgorithm class and represents a truth table
public class TruthTable extends LogicAlgorithm {

  // Instance variables
  private String queryStr;
  private List<HornClauses> hornClauses;
  private List<String> factList;
  private List<String> varList;
  private int colCount;
  private int rowCount;
  private boolean[][] truthGrid;
  private boolean[] logicResults;
  private int[][] literalIndices;
  private int[] factIndices;
  private int[] entailedArray;
  private boolean[] inferenceResults;
  private int queryIndexNum;
  private int counter;

  // Constructor that takes in a KnowledgeBase object and a query string
  public TruthTable(KnowledgeBase kb, String queryStr) {

    // Set the algorithm code and full name using methods from the superclass
    setAlgorithmCode("TT");
    setFullName("Truth Table");

    // Initialize instance variables
    this.queryStr = queryStr;
    this.hornClauses = kb.getHornClauses();
    this.factList = kb.getFacts();
    this.varList = new ArrayList<>();
    getVarList();
    this.colCount = varList.size();
    this.rowCount = (int) Math.pow(2, varList.size());
    this.truthGrid = new boolean[rowCount][colCount];
    this.logicResults = new boolean[rowCount];

    // Set all values in logicResults to true
    for (int i = 0; i < rowCount; i++) {
      logicResults[i] = true;
    }

    // Initialize more instance variables
    this.literalIndices = new int[hornClauses.size()][2];
    this.factIndices = new int[factList.size()];
    this.entailedArray = new int[hornClauses.size()];
    this.inferenceResults = new boolean[rowCount];
    this.queryIndexNum = 0;
    this.counter = 0;

    // Populate the truth grid and get column indices
    populateTruthGrid();
    getColumnIndices();
  }

  // Method to test the question and return a string output
  public String testQuestion() {
    String outputStr;

    // If verifyFacts return true, set outputstr to "YES" with the counter value
    if (verifyFacts()) {
      outputStr = "YES: " + counter;
    } else {
      // Otherwise, set outputstr to "NO" with the query string
      outputStr = "NO: it was not possible to prove " + queryStr;
    }

    return outputStr;
  }

  // Method to verify facts and return a boolean value
  public boolean verifyFacts() {

    // Loop through each row of the truth grid
    for (int i = 0; i < rowCount; i++) {
      for (int j = 0; j < factIndices.length; j++) {
        if (logicResults[i]) {
          if (!truthGrid[i][queryIndexNum]) {
            logicResults[i] = false;
            inferenceResults[i] = false;
            break;
          } else {
            inferenceResults[i] = true;
          }
          logicResults[i] = truthGrid[i][factIndices[j]];
        } else {
          break;
        }
      }
    }

    for (int i = 0; i < rowCount; i++) {
      if (logicResults[i]) {
        for (int j = 0; j < literalIndices.length; j++) {
          if (hornClauses.get(j).countLiterals() == 2) {
            if (truthGrid[i][literalIndices[j][0]] && truthGrid[i][literalIndices[j][1]]
                && !truthGrid[i][entailedArray[j]]) {
              logicResults[i] = false;
            }
          } else {
            if (truthGrid[i][literalIndices[j][0]] && !truthGrid[i][entailedArray[j]]) {
              logicResults[i] = false;
            }
          }
        }
      }
    }

    for (int i = 0; i < rowCount; i++) {
      if (logicResults[i]) {
        counter++;
      }

      if (!inferenceResults[i] && logicResults[i]) {
        return false;
      }
    }

    // Return true if all conditions are met, otherwise return false
    return true;
  }

  // Method to get the list of variables from the horn clauses
  public void getVarList() {
    // loop through each horn clause and add its literals and inferred value to
    // varList
    for (HornClauses clause : hornClauses) {
      for (String literal : clause.getLiterals()) {
        varList.add(literal);
      }
      varList.add(clause.getInferred());
    }

    // Create a set of unique variables from varList and clear varList
    Set<String> uniqueVars = new HashSet<>(varList);
    varList.clear();

    // Add all unique variables back into varList
    varList.addAll(uniqueVars);
  }

  // Method to populate the truth grid with boolean values
  public void populateTruthGrid() {

    // Loop through each row and column of the truth grid and set its value based on
    // a bitwise operation
    for (int i = 0; i < rowCount; i++) {
      for (int j = 0; j < colCount; j++) {
        int v = i & 1 << colCount - 1 - j;
        truthGrid[i][j] = (v == 0);
      }
    }
  }

  // Method to get the column indices for the fact and query variables, as well as
  // the literals and inferred values of the horn clauses
  public void getColumnIndices() {

    // Loop through each fact and variable in varList
    for (int i = 0; i < factList.size(); i++) {
      for (int j = 0; j < varList.size(); j++) {

        // If the fact equals the variable, set the value of factIndices at index i to j
        if (factList.get(i).equals(varList.get(j))) {
          factIndices[i] = j;
        }

        // If the query string equals the variable, set queryIndexNum to j
        if (queryStr.equals(varList.get(j))) {
          queryIndexNum = j;
        }
      }
    }

    // Loop through each variable in varList and each horn clause
    for (int i = 0; i < varList.size(); i++) {
      for (int j = 0; j < hornClauses.size(); j++) {

        // Loop through each literal in the horn clause
        for (int k = 0; k < hornClauses.get(j).countLiterals(); k++) {

          // If the literal equals the variable, set the value of literalIndices at index
          // [j][k] to i
          if (hornClauses.get(j).getLiterals().get(k).equals(varList.get(i))) {
            literalIndices[j][k] = i;
          }
        }

        // If the inferred value of the horn clause equals the variable, set the value
        // of entailedArray at index j to i
        if (hornClauses.get(j).getInferred().equals(varList.get(i))) {
          entailedArray[j] = i;
        }
      }
    }
  }
}