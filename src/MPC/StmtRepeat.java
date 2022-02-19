package MPC;
/**
 * Representation of a REPEAT statement.
 *
 * <i>
 * The statement sequence is repeatedly executed (and at least once)
 * until the expression becomes true.
 * </i>
 *
 * Example:
 * <pre>
 *   repeat
 *     K := I mod J;
 *     I := J;
 *     J := K
 *   until J = 0
 * </pre>
 *
 * @version 1.0
 */
public class StmtRepeat extends Stmt {

  /**
   * the statements to execute; a Stmts containing a sequence of Stmt nodes
   */
  public final Stmts stmts;

  /**
   * the until Expr
   */
  public Expr expr;

  /**
   * Creates a new StmtRepeat instance given its Stmts, until
   * Expr, and source (start/end) position
   *
   * @param ss    Stmts (sequence of Stmt nodes) to be executed
   * @param e     until Expr
   * @param left  starting position in program text
   * @param right ending position in program text
   */
  public StmtRepeat (Stmts ss, Expr e, int left, int right) {
    super(left, right);
    stmts = ss;
    expr  = e;
  }

  /**
   * Handles AST visiting for StmtRepeat nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitStmtRepeat(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

