package MPC;
/**
 * Representation for a literal integer expression.
 *
 * @version 1.0
 */
public class ExprInt extends Expr {

  /**
   * the value of the Pascal integer literal
   */
  public final int value;

  /**
   * Creates a new ExprInt instance given the TokenInt of the
   * value and its source (start/end) position
   *
   * @param tok   the integer token (TokenInt)
   * @param left  starting position in program text
   * @param right ending position in program text
   */
  public ExprInt (TokenInt tok, int left, int right) {
    super(left, right);
    type   = Type.theIntType;
    value  = tok.value;
    object = new ObjectValueInteger(tok);
  }

  /**
   * Handles AST visiting for ExprInt nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitExprInt(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

