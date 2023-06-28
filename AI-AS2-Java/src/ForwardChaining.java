import java.util.ArrayList;

public class ForwardChaining extends LogicAlgorithm {

    // Constructor that takes a KnowledgeBase and a question as arguments
    public ForwardChaining(KnowledgeBase kb, String q) {
        super(kb, q);
        setAlgorithmCode("FC");
        setFullName("Forward Chaining");
    }

    // Method that tests if the question can be inferred from the knowledge base
    // using the forward chaining algorithm
    @Override
    public String testQuestion() {

        // Create an agenda containing the known facts from the knowledge base
        ArrayList<String> agenda = new ArrayList<String>(getKB().getFacts());

        // Create an inferred list to keep track of facts that have already been
        // inferred
        ArrayList<String> inferred = new ArrayList<String>();

        // Create a counted array to keep track of which Horn clauses have already been
        // counted
        boolean[] counted = new boolean[getKB().getHornClauses().size()];

        // While the agenda is not empty
        while (!agenda.isEmpty()) {

            // Remove the first fact from the agenda
            String p = agenda.remove(0);

            // If the fact has not already been inferred
            if (!inferred.contains(p)) {

                // Add the fact to the inferred list
                inferred.add(p);

                // Iterate through the Horn clauses in the knowledge base
                for (int i = 0; i < getKB().getHornClauses().size(); i++) {
                    HornClauses hornClause = getKB().getHornClauses().get(i);

                    // If the Horn clause has not already been counted and its literals contain the
                    // fact
                    if (!counted[i] && hornClause.getLiterals().contains(p)) {

                        // Delete the fact from the Horn clause's literals
                        hornClause.deleteLiteral(p);

                        // If the Horn clause has no more literals
                        if (hornClause.countLiterals() == 0) {

                            // If the inferred literal of the Horn clause matches the question being tested
                            if (hornClause.getInferred().equals(getQuestion())) {

                                // Add the inferred literal to the inferred list
                                inferred.add(hornClause.getInferred());

                                // Build and return a string containing "YES" and a comma-separated list of all
                                // literals in the inferred list
                                StringBuilder sb = new StringBuilder();
                                sb.append("YES: ");
                                for (int j = 0; j < inferred.size(); j++) {
                                    sb.append(inferred.get(j));
                                    if (j != inferred.size() - 1) {
                                        sb.append(", ");
                                    }
                                }
                                return sb.toString();
                            } else {
                                // Add the inferred literal to the agenda for further processing
                                agenda.add(hornClause.getInferred());
                            }
                        }
                        // Mark the Horn clause as counted
                        counted[i] = true;
                    }
                }
            }
        }

        // If the question being tested is in the inferred list, return "YES" along with
        // a comma-separated list of all literals in the inferred list
        if (inferred.contains(getQuestion())) {
            StringBuilder sb = new StringBuilder();
            sb.append("YES: ");
            for (int j = 0; j < inferred.size(); j++) {
                sb.append(inferred.get(j));
                if (j != inferred.size() - 1) {
                    sb.append(", ");
                }
            }
            return sb.toString();
        } else {
            // Otherwise, return "NO: it was not possible to prove (query)"
            return "NO: It was not possible to prove " + getQuestion();
        }
    }

    // Method that checks if the question being tested is already a known fact in
    // the knowledge base
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

// public class ForwardChaining extends LogicAlgorithm {
//     public ForwardChaining(KnowledgeBase kb, String q) {
//         super(kb, q);
//         setAlgorithmCode("FC");
//         setFullName("Forward Chaining");
//     }

//     @Override
//     public String testQuestion() {
//         System.out.println("Knowledge base: " + getKB());
//         System.out.println("Question: " + getQuestion());

//         ArrayList<String> agenda = new ArrayList<String>(getKB().getFacts());
//         ArrayList<String> inferred = new ArrayList<String>();
//         boolean[] counted = new boolean[getKB().getHornClauses().size()];

//         System.out.println("Initial agenda: " + agenda);
//         System.out.println("Initial inferred: " + inferred);

//         while (!agenda.isEmpty()) {
//             String p = agenda.remove(0);
//             System.out.println("Processing fact: " + p);
//             if (!inferred.contains(p)) {
//                 inferred.add(p);
//                 for (int i = 0; i < getKB().getHornClauses().size(); i++) {
//                     HornClauses hornClause = getKB().getHornClauses().get(i);
//                     if (!counted[i] && hornClause.getLiterals().contains(p)) {
//                         System.out.println("Updating Horn clause: " + hornClause);
//                         hornClause.deleteLiteral(p);
//                         if (hornClause.countLiterals() == 0) {
//                             if (hornClause.getInferred().equals(getQuestion())) {
//                                 inferred.add(hornClause.getInferred());
//                                 StringBuilder sb = new StringBuilder();
//                                 sb.append("YES: ");
//                                 for (int j = 0; j < inferred.size(); j++) {
//                                     sb.append(inferred.get(j));
//                                     if (j != inferred.size() - 1) {
//                                         sb.append(", ");
//                                     }
//                                 }
//                                 return sb.toString();
//                             } else {
//                                 agenda.add(hornClause.getInferred());
//                             }
//                         }
//                         counted[i] = true;
//                     }
//                 }
//             }
//             System.out.println("Updated agenda: " + agenda);
//             System.out.println("Updated inferred: " + inferred);
//         }

//         if (inferred.contains(getQuestion())) {
//             StringBuilder sb = new StringBuilder();
//             sb.append("YES: ");
//             for (int j = 0; j < inferred.size(); j++) {
//                 sb.append(inferred.get(j));
//                 if (j != inferred.size() - 1) {
//                     sb.append(", ");
//                 }
//             }
//             return sb.toString();
//         } else {
//             return "NO";
//         }
//     }

//     @Override
//     public boolean verifyFacts() {
//         for (String fact : getKB().getFacts()) {
//             if (fact.equals(getQuestion())) {
//                 return true;
//             }
//         }

//         return false;
//     }
// }