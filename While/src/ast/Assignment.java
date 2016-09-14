package ast;

import java.util.Random;

/**
 * Representación de las asignaciones de valores a variables.
 */
public class Assignment extends Stmt {

    public final String id;
    public final Exp expression;

    public Assignment(String id, Exp expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public String unparse() {
        return id + " = " + expression.unparse() + "; ";
    }

    @Override
    public String toString() {
        return "Assignment(" + id + ", " + expression + ")";
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 31 + (this.id == null ? 0 : this.id.hashCode());
        result = result * 31 + (this.expression == null ? 0 : this.expression.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Assignment other = (Assignment) obj;
        return (this.id == null ? other.id == null : this.id.equals(other.id))
                && (this.expression == null ? other.expression == null : this.expression.equals(other.expression));
    }

    public static Assignment generate(Random random, int min, int max) {
        String id;
        Exp expression;
        id = "" + "abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26));
        expression = Exp.generate(random, min - 1, max - 1);
        return new Assignment(id, expression);
    }

    @Override
    public State evaluate(State state) throws Exception {
    	Object value = expression.evaluate(state);
        state.set(id, value);
        return state;        
    }

	@Override
	public CheckState check(CheckState cState) throws Exception {
		Tipo tipo = expression.check(cState);
		cState.set(id, tipo, true);
        return cState;
	}

}
