package Engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BCMethod extends LogicAlgorithm {
    public BCMethod(KnowledgeBase knowledgeBase, String question) {
        super("bc", "Backward Chaining", knowledgeBase, question);
    }

    @Override
    public String testQuestion() {
        Set<String> inferred = new HashSet<>();
        ArrayList<String> inferredList = new ArrayList<>();

        boolean result = backwardChain(getQuestion(), inferred, inferredList, new HashSet<>());

        if (result) {
            StringBuilder sb = new StringBuilder();
            sb.append("YES: ");
            for (int i = 0; i < inferredList.size(); i++) {
                sb.append(inferredList.get(i));
                if (i != inferredList.size() - 1) {
                    sb.append(", ");
                }
            }
            return sb.toString();
        } else {
            return "NO: It was not possible to prove " + getQuestion();
        }
    }

    private boolean backwardChain(String query, Set<String> inferred, ArrayList<String> inferredList,
                                  Set<String> processedQueries) {
        if (processedQueries.contains(query)) {
            return false;
        }
        processedQueries.add(query);

        if (getKnowledgeBase().getFacts().contains(query)) {
            if (!inferred.contains(query)) {
                inferred.add(query);
                inferredList.add(query);
            }
            return true;
        }

        for (HornClause hornClause : getKnowledgeBase().getHornClauses()) {
            if (hornClause.getInferred().equals(query)) {
                Set<String> clauseInferred = new HashSet<>(inferred);
                ArrayList<String> clauseInferredList = new ArrayList<>(inferredList);

                boolean allLiteralsInferred = true;

                for (String literal : hornClause.getLiterals()) {
                    if (!clauseInferred.contains(literal)) {
                        boolean literalResult = backwardChain(literal, clauseInferred, clauseInferredList,
                                new HashSet<>(processedQueries));

                        if (!literalResult) {
                            allLiteralsInferred = false;
                            break;
                        }
                    }
                }

                if (allLiteralsInferred) {
                    for (String fact : clauseInferredList) {
                        if (!inferred.contains(fact)) {
                            inferred.add(fact);
                            inferredList.add(fact);
                        }
                    }
                    if (!inferred.contains(query)) {
                        inferred.add(query);
                        inferredList.add(query);
                    }
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean verifyFacts() {
        for (String fact : getKnowledgeBase().getFacts()) {
            if (fact.equals(getQuestion())) {
                return true;
            }
        }
        return false;
    }
}

