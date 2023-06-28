package Engine;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TTMethod extends LogicAlgorithm {

    private String queryStr;
    private List<HornClause> hornClauses;
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

    public TTMethod(KnowledgeBase kb, String queryStr) {
        setAlgorithmCode("TT");
        setFullName("Truth Table");

        this.queryStr = queryStr;
        this.hornClauses = kb.getHornClauses();
        this.factList = kb.getFacts();
        this.varList = new ArrayList<>();
        getVarList();
        this.colCount = varList.size();
        this.rowCount = (int) Math.pow(2, varList.size());
        this.truthGrid = new boolean[rowCount][colCount];
        this.logicResults = new boolean[rowCount];

        for (int i = 0; i < rowCount; i++) {
            logicResults[i] = true;
        }

        this.literalIndices = new int[hornClauses.size()][2];
        this.factIndices = new int[factList.size()];
        this.entailedArray = new int[hornClauses.size()];
        this.inferenceResults = new boolean[rowCount];
        this.queryIndexNum = 0;
        this.counter = 0;

        populateTruthGrid();
        getColumnIndices();
    }

    public String testQuestion() {
        String outputStr;

        if (verifyFacts()) {
            outputStr = "YES: " + counter;
        } else {
            outputStr = "NO: it was not possible to prove " + queryStr;
        }

        return outputStr;
    }

    public boolean verifyFacts() {
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

        return true;
    }

    public void getVarList() {
        for (HornClause clause : hornClauses) {
            for (String literal : clause.getLiterals()) {
                varList.add(literal);
            }
            varList.add(clause.getInferred());
        }

        Set<String> uniqueVars = new HashSet<>(varList);
        varList.clear();
        varList.addAll(uniqueVars);
    }

    public void populateTruthGrid() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                int v = i & 1 << colCount - 1 - j;
                truthGrid[i][j] = (v == 0);
            }
        }
    }

    public void getColumnIndices() {
        for (int i = 0; i < factList.size(); i++) {
            for (int j = 0; j < varList.size(); j++) {
                if (factList.get(i).equals(varList.get(j))) {
                    factIndices[i] = j;
                }
                if (queryStr.equals(varList.get(j))) {
                    queryIndexNum = j;
                }
            }
        }

        for (int i = 0; i < varList.size(); i++) {
            for (int j = 0; j < hornClauses.size(); j++) {
                for (int k = 0; k < hornClauses.get(j).getLiterals().size(); k++) {
                    if (hornClauses.get(j).getLiterals().get(k).equals(varList.get(i))) {
                        literalIndices[j][k] = i;
                    }
                }
                if (hornClauses.get(j).getInferred().equals(varList.get(i))) {
                    entailedArray[j] = i;
                }
            }
        }
    }
}
