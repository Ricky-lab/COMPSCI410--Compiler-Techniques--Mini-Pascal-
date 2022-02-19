package MPC;
/**
 * Representation of NIL in a <i>Pascal</i> program.
 *
 * @version 1.0
 */
public class ExprNil extends Expr {

  /**
   * Creates a new ExprNil instance given its source (start/end)
   * position
   *
   * @param left  starting position in program text
   * @param right ending position in program text
   */
  public ExprNil (int left, int right) {
    super(left, right);
    type   = Type.theNilType;
    object = MPCObject.theNilObject;
  }

  /**
   * Handles AST visiting for ExprNil nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitExprNil(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

