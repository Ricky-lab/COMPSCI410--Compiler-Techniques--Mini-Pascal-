package MPC;
/**
 * Representation of a literal string expression.
 *
 * @version 1.0
 */
public class ExprString extends Expr {

  /**
   * the String value for the Pascal string constant that this expression
   * represents
   */
  public final String value;

  /**
   * Creates a new ExprString instance given the TokenString and
   * its source (start/end) position
   *
   * @param tok   the string token (TokenString)
   * @param left  starting position in program text
   * @param right ending position in program text
   */
  public ExprString (TokenString tok, int left, int right) {
    super(left, right);
    type   = Type.theStringType;
    value  = tok.string;
    object = new ObjectValueString (tok);
  }

  /**
   * Handles AST visiting for ExprString nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitExprString(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

