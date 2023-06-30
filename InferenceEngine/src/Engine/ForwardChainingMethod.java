package Engine;

import java.util.ArrayList;

public class ForwardChainingMethod extends LogicAlgorithms
{
    public ForwardChainingMethod(KnowledgeBase kb, String q)
    {
        super(kb, q);
        setShortName("FC");
        setFullName("Forward Chaining");
    }

    @Override
    public String testInput() {

        // Create an agenda containing the known facts from the knowledge base
        ArrayList<String> agenda = new ArrayList<String>(getKnowledgeBase().getFacts());

        // Create an inferred list to keep track of facts that have already been
        // inferred
        ArrayList<String> inferred = new ArrayList<String>();

        // Create a counted array to keep track of which Horn clauses have already been
        // counted
        boolean[] counted = new boolean[getKnowledgeBase().getHornClause().size()];

        // While the agenda is not empty
        while (!agenda.isEmpty()) 
        {

            // Remove the first fact from the agenda
            String p = agenda.remove(0);

            // If the fact has not already been inferred
            if (!inferred.contains(p))
            {

                // Add the fact to the inferred list
                inferred.add(p);

                // Iterate through the Horn clauses in the knowledge base
                for (int i = 0; i < getKnowledgeBase().getHornClause().size(); i++) 
                {
                    HornClause hClause = getKnowledgeBase().getHornClause().get(i);

                    // If the Horn clause has not already been counted and its literals contain the
                    // fact
                    if (!counted[i] && hClause.getLiterals().contains(p)) 
                    {

                        // Delete the fact from the Horn clause's literals
                        hClause.deleteLiteral(p);

                        // If the Horn clause has no more literals
                        if (hClause.countLiterals() == 0) 
                        {

                            // If the inferred literal of the Horn clause matches the question being tested
                            if (hClause.getInferred().equals(getInput())) 
                            {

                                // Add the inferred literal to the inferred list
                                inferred.add(hClause.getInferred());

                                // Build and return a string containing "YES" and a comma-separated list of all
                                // literals in the inferred list
                                StringBuilder sb = new StringBuilder();
                                
                                sb.append("YES: ");
                                for (int j = 0; j < inferred.size(); j++) 
                                {
                                    sb.append(inferred.get(j));

                                    if (j != inferred.size() - 1) 
                                    {
                                        sb.append(", ");
                                    }
                                }
                                return sb.toString();
                            } 
                            else 
                            {
                                // Add the inferred literal to the agenda for further processing
                                agenda.add(hClause.getInferred());
                            }
                        }
                        // Mark the Horn clause as counted
                        counted[i] = true;
                    }
                }
            }
        }

        if (inferred.contains(getInput())) 
        {
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
            return "NO: It was not possible to prove " + getInput();
        }
    }

    // Method that checks if the question being tested is already a known fact in
    // the knowledge base
    @Override
    public boolean verifyFacts() 
    {
        for (String fact : getKnowledgeBase().getFacts()) 
        {
            if (fact.equals(getInput())) 
            {
                return true;
            }
        }

        return false;
}

}
