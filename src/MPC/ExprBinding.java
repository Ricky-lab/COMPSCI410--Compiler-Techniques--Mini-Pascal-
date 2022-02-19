package MPC;
/**
 * Representation for an expression binding.  This is used to bind a name or
 * symbol used in an expression to its declaration, for example, the use of a
 * variable or constant.
 *
 * @version 1.0
 */
public class ExprBinding extends Expr {

  /**
   * the Binding of this expression
   */
  public final Binding bind;

  /**
   * Creates a new ExprBinding instance given its Binding
   *
   * @param b the Binding of this expression
   */
  public ExprBinding (Binding b) {
    super(b.pos, b.endPos);
    bind = b;
  }

  /**
   * Handles AST visiting for ExprBinding nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitExprBinding(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

