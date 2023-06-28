package Engine;
import java.util.List;

public class HornClause {
    private List<String> literals;
    private String inferred;

    public HornClause(List<String> literals, String inferred) {
        this.literals = literals;
        this.inferred = inferred;
    }

    public List<String> getLiterals() {
        return literals;
    }

    public String getInferred() {
        return inferred;
    }

    public int countLiterals() {
        return literals.size();
    }

    public void deleteLiteral(String literal) {
    literals.remove(literal);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String literal : literals) {
            sb.append(literal).append(" ");
        }
        sb.append("=> ").append(inferred);
        return sb.toString();
    }
}
