package MPC;
/**
 * Represents a parse error.
 *
 * @version 1.0
 */
public class ExprError extends Expr {

  /**
   * Creates a new ExprError instance given a source (start/end)
   * position
   *
   * @param left  starting position in program text
   * @param right ending position in program text
   */
  public ExprError (int left, int right) {
    super(left, right);
    type = Type.theErrorType;
  }

  /**
   * Handles AST visiting for ExprError nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitExprError(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

