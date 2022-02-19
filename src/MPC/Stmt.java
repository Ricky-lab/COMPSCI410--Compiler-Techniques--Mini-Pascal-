package MPC;
/**
 * Abstract class for all statements.
 *
 * <i>
 * Statements denote algorithmic actions, and are said to be executable.
 * </i>
 *
 * @version 1.0
 */
public abstract class Stmt extends ASTNode {

  /**
   * Initializes the source (start/end) position of the Stmt
   *
   * @param left  starting position in program text
   * @param right ending position in program text
   */
  public Stmt (int left, int right) {
    super(left, right);
  }

  /**
   * Handles AST visiting actions that come before visiting the Stmt
   * (subclass) node
   *
   * @param v an ASTVisitor
   */
  public void acceptBefore (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitStmtBefore(this);
  }

  /**
   * Handles AST visiting actions that come after visiting the Stmt (subclass)
   * node
   *
   * @param v an ASTVisitor
   */
  public void acceptAfter (ASTVisitor v) {
    v.visitStmtAfter(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

