package MPC;
/**
 * Represents an empty statement.  Used for a statement consisting of no
 * action. (Also used for representing error cases during parse.)
 *
 * @version 1.0
 */
public class StmtEmpty extends Stmt {

  /**
   * Creates a new StmtEmpty instance.
   */
  public StmtEmpty () {
    // has no interior tokens from which to get position
    super(-1, -1);
  }

  /**
   * Handles AST visiting for StmtEmpty nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitStmtEmpty(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

