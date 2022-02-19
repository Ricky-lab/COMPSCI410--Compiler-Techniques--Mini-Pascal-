package MPC;
/**
 * Representation of an assignment statement.
 *
 * <i>
 * The assignment statement serves to access the variable or function-activation
 * result and to replace its current value by the value obtained by evaluating
 * the expression.
 * </i>
 *
 * @version 1.0
 */
public class StmtAssign extends Stmt {

  /**
   * left-hand side Expr of an assignment.  The variable or
   * function-activation result to receive the assigned value.
   */
  public Expr var;

  /**
   * right-hand side Expr of an assignment.  The expression whose value will
   * be assigned to the left hand side.
   */
  public Expr expr;

  /**
   * controls evaluation order of subexpressions, i.e., do var first or expr
   * first?
   */
  public boolean evalVarFirst;

  /**
   * Creates a new StmtAssign instance given its var Expr and its
   * expr Expr
   *
   * @param v the left-hand side
   * @param e the right-hand side
   */
  public StmtAssign (Expr v, Expr e) {
    super(v.pos, e.endPos);
    var  = v;
    expr = e;
  }

  /**
   * Handles AST visiting of StmtAssign nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitStmtAssign(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

