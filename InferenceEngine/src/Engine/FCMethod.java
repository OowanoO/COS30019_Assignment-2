package Engine;

import java.util.ArrayList;
import java.util.List;

public class FCMethod extends LogicAlgorithm {

    private List<HornClause> hornClauses;
    private List<String> basicFacts;
    private String askStatement;
    private List<String> usedVariables;

    public FCMethod(KnowledgeBase knowledgeBase, String question) {
        super("FC", "Forward Chaining", knowledgeBase, question);
        hornClauses = knowledgeBase.getHornClauses();
        basicFacts = new ArrayList<>(knowledgeBase.getFacts());
        askStatement = question;
        usedVariables = new ArrayList<>();
    }

    @Override
    public String testQuestion() {
        if (solve()) {
            return "YES: " + String.join(", ", usedVariables);
        } else {
            return "NO: Not enough information to prove " + askStatement + ".";
        }
    }

    @Override
    public boolean verifyFacts() {
        return solve();
    }

    private boolean solve() {
        boolean result = false;

        while (!basicFacts.isEmpty()) {
            String currentVar = basicFacts.remove(0);
            usedVariables.add(currentVar);

            if (currentVar.equals(askStatement)) {
                result = true;
                break;
            }

            for (HornClause hornClause : hornClauses) {
                if (hornClause.getLiterals().contains(currentVar)) {
                    hornClause.deleteLiteral(currentVar);
                    if (hornClause.countLiterals() == 0) {
                        basicFacts.add(hornClause.getInferred());
                    }
                }
            }
        }

        return result;
    }
}
