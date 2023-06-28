import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BackwardChaining extends LogicAlgorithm {
    public BackwardChaining(KnowledgeBase kb, String q) {
        super(kb, q);
        setAlgorithmCode("BC");
        setFullName("Backward Chaining");
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

        if (getKB().getFacts().contains(query)) {
            if (!inferred.contains(query)) {
                inferred.add(query);
                inferredList.add(query);
            }
            return true;
        }

        for (HornClauses hornClause : getKB().getHornClauses()) {
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
        for (String fact : getKB().getFacts()) {
            if (fact.equals(getQuestion())) {
                return true;
            }
        }

        return false;
    }
}

// Print statements checking or debugging

// import java.util.ArrayList;
// import java.util.HashSet;
// import java.util.Set;

// public class BackwardChaining extends LogicAlgorithm {
// public BackwardChaining(KnowledgeBase kb, String q) {
// super(kb, q);
// setAlgorithmCode("BC");
// setFullName("Backward Chaining");
// }

// @Override
// public String testQuestion() {
// Set<String> inferred = new HashSet<>();

// ArrayList<String> inferredList = new ArrayList<>();

// boolean result = backwardChain(getQuestion(), inferred, inferredList, new
// HashSet<>());

// if (result) {
// StringBuilder sb = new StringBuilder();
// sb.append("YES: ");
// for (int i = 0; i < inferredList.size(); i++) {
// sb.append(inferredList.get(i));
// if (i != inferredList.size() - 1) {
// sb.append(", ");
// }
// }
// return sb.toString();
// } else {
// return "NO: It was not possible to prove " + getQuestion();
// }
// }

// private boolean backwardChain(String query, Set<String> inferred,
// ArrayList<String> inferredList, Set<String> processedQueries) {
// System.out.println("Current query: " + query);
// if (processedQueries.contains(query)) {
// System.out.println("Cycle detected: " + query + " has already been
// processed");
// return false;
// }
// processedQueries.add(query);

// if (getKB().getFacts().contains(query)) {
// System.out.println("Query is a fact in the knowledge base");
// if (!inferred.contains(query)) {
// System.out.println("Adding query to inferred set and list");
// inferred.add(query);
// inferredList.add(query);
// }
// return true;
// }

// for (HornClauses hornClause : getKB().getHornClauses()) {
// System.out.println("Checking horn clause: " + hornClause.getInferred());
// if (hornClause.getInferred().equals(query)) {
// System.out.println("Horn clause inferred matches query");
// Set<String> clauseInferred = new HashSet<>(inferred);

// ArrayList<String> clauseInferredList = new ArrayList<>(inferredList);

// boolean allLiteralsInferred = true;

// for (String literal : hornClause.getLiterals()) {
// System.out.println("Checking literal: " + literal);
// if (!clauseInferred.contains(literal)) {
// System.out.println("Literal not in clauseInferred set, calling backwardChain
// with literal");
// boolean literalResult = backwardChain(literal, clauseInferred,
// clauseInferredList, new HashSet<>(processedQueries));

// if (!literalResult) {
// allLiteralsInferred = false;
// break;
// }
// }
// }

// if (allLiteralsInferred) {
// System.out.println("All literals in horn clause inferred, adding to inferred
// set and list");
// for (String fact : clauseInferredList) {
// if (!inferred.contains(fact)) {
// inferred.add(fact);
// inferredList.add(fact);
// }
// }
// if (!inferred.contains(query)) {
// inferred.add(query);
// inferredList.add(query);
// }
// return true;
// }
// }
// }

// return false;
// }

// @Override
// public boolean verifyFacts() {
// for (String fact : getKB().getFacts()) {
// if (fact.equals(getQuestion())) {
// return true;
// }
// }

// return false;
// }
// }