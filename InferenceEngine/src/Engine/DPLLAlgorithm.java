package Engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DPLLAlgorithm extends LogicAlgorithms {

    public DPLLAlgorithm(KnowledgeBase kb, String q) {
        super(kb, q);
        setShortName("DPLL");
        setFullName("Davis-Putnam-Logemann-Loveland (DPLL) Algorithm");
    }

    @Override
    public String testInput() {
        if (satisfiable(getKnowledgeBase().getHornClauses())) {
            return "YES: " + getInput();
        } else {
            return "NO: It was not possible to prove " + getInput();
        }
    }

    @Override
    public boolean verifyFacts() {
        List<String> queryClauses = convertToClauses(getInput());
        List<HornClause> hornClauses = getKnowledgeBase().getHornClauses();

        // Add the negation of the query clauses to the knowledge base clauses
        for (String queryClause : queryClauses) {
            HornClause negatedClause = negateClause(queryClause);
            hornClauses.add(negatedClause);
        }

        return satisfiable(hornClauses);
    }

    private boolean satisfiable(List<HornClause> clauses) {
        Map<String, Boolean> model = new HashMap<>();
        return dpll(clauses, model);
    }

    private boolean dpll(List<HornClause> clauses, Map<String, Boolean> model) {
        if (clauses.isEmpty()) {
            return true; // All clauses are satisfied
        }

        if (containsEmptyClause(clauses)) {
            return false; // Found an empty clause, the formula is unsatisfiable
        }

        String literal = findPureLiteral(clauses);
        if (literal != null) {
            model.put(literal, true);
            List<HornClause> simplifiedClauses = simplifyClauses(clauses, literal);
            return dpll(simplifiedClauses, model);
        }

        literal = findUnitClause(clauses);
        if (literal != null) {
            model.put(literal, true);
            List<HornClause> simplifiedClauses = simplifyClauses(clauses, literal);
            return dpll(simplifiedClauses, model);
        }

        literal = selectLiteral(clauses);
        if (literal != null) {
            model.put(literal, true);
            List<HornClause> simplifiedClauses = simplifyClauses(clauses, literal);
            if (dpll(simplifiedClauses, model)) {
                return true;
            }

            model.put(literal, false);
            simplifiedClauses = simplifyClauses(clauses, negateLiteral(literal));
            return dpll(simplifiedClauses, model);
        }

        return false; // No literal found, the formula is unsatisfiable
    }

    private boolean containsEmptyClause(List<HornClause> clauses) {
        for (HornClause clause : clauses) {
            if (clause.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private String findPureLiteral(List<HornClause> clauses) {
        Map<String, Boolean> literalMap = new HashMap<>();

        for (HornClause clause : clauses) {
            List<String> literals = clause.getLiterals();
            for (String literal : literals) {
                if (literal.startsWith("~")) {
                    literal = literal.substring(1);
                    literalMap.put(literal, false);
                } else {
                    literalMap.put(literal, true);
                }
            }
        }

        for (Map.Entry<String, Boolean> entry : literalMap.entrySet()) {
            String literal = entry.getKey();
            boolean polarity = entry.getValue();
            if (clauses.stream().noneMatch(c -> c.containsLiteral(literal) == polarity)) {
                return literal;
            }
        }

        return null;
    }

    private String findUnitClause(List<HornClause> clauses) {
        for (HornClause clause : clauses) {
            if (clause.isUnitClause()) {
                return clause.getLiterals().get(0);
            }
        }
        return null;
    }

    private List<HornClause> simplifyClauses(List<HornClause> clauses, String literal) {
        List<HornClause> simplifiedClauses = new ArrayList<>();
        for (HornClause clause : clauses) {
            if (!clause.containsLiteral(literal) && !clause.containsLiteral(negateLiteral(literal))) {
                simplifiedClauses.add(clause);
            }
        }
        return simplifiedClauses;
    }

    private String selectLiteral(List<HornClause> clauses) {
        for (HornClause clause : clauses) {
            List<String> literals = clause.getLiterals();
            if (!literals.isEmpty()) {
                return literals.get(0);
            }
        }
        return null;
    }

    private HornClause negateClause(String clause) {
        String[] literals = clause.split("\\|\\|");
        List<String> literalList = new ArrayList<>();
        for (String literal : literals) {
            literalList.add(negateLiteral(literal.trim()));
        }
        return new HornClause(literalList);
    }

    private String negateLiteral(String literal) {
        if (literal.startsWith("~")) {
            return literal.substring(1);
        } else {
            return "~" + literal;
        }
    }

    private List<String> convertToClauses(String input) {
        String[] clauses = input.split("&&");
        List<String> clauseList = new ArrayList<>();
        for (String clause : clauses) {
            clauseList.add(clause.trim());
        }
        return clauseList;
    }
}
