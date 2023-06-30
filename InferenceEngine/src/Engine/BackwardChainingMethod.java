package Engine;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BackwardChainingMethod extends LogicAlgorithms {
    public BackwardChainingMethod(KnowledgeBase kb, String q) {
        super(kb, q);
        setShortName("BC");
        setFullName("Backward Chaining");
    }

    @Override
    public String testInput(){
        Set<String> inferred = new HashSet<>();

        ArrayList<String> inferredList = new ArrayList<>();

        boolean result = backwardChain(getInput(), inferred, inferredList, new HashSet<>());

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
            return "NO: It was not possible to prove " + getInput();
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

        for (HornClause hornClause : getKnowledgeBase().getHornClause()) {
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
            if (fact.equals(getInput())) {
                return true;
            }
        }

        return false;
    }
}
