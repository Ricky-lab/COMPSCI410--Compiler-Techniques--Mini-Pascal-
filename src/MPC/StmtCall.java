package MPC;
/**
 * Representation of a call statement.
 *
 * <i>
 * A call or procedure statement serves to activate the procedure denoted by the
 * procedure identifier.
 * </i>
 *
 * @version 1.0
 */
public class StmtCall extends Stmt {

  /**
   * The call Expr containing the procedure name and a possibly empty list of
   * <i>actual parameters</i>
   */
  public Expr call;

  /**
   * Creates a new StmtCall instance given its call Expr
   *
   * @param c the call expression
   */
  public StmtCall (Expr c) {
    super(c.pos, c.endPos);
    call = c;
  }

  /**
   * Handles AST visiting of StmtCall nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitStmtCall(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

