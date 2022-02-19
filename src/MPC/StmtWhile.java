package MPC;
/**
 * Representation of a WHILE statement.
 *
 * <i>
 * The statement is repeatedly executed until the expression becomes
 * false.  If its value is false at the beginning, the statement is
 * not executed at all.
 * </i>
 *
 * Example:
 * <pre>
 *   while I &gt; 0 do
 *     if odd(I) then Y := Y*X;
 *     I := I div 2;
 *     X := sqr(x)
 *   end
 * </pre>
 *
 * @version 1.0
 */
public class StmtWhile extends Stmt {

  /**
   * the boolean while Expr
   */
  public Expr expr;

  /**
   * the Stmt to execute
   */
  public final Stmt stmt;

  /**
   * Creates a new StmtWhile instance given its while Expr, its
   * Stmt body, and its source (start/end) position
   *
   * @param e     boolean while Expr
   * @param s     Stmt to execute
   * @param left  starting position in program text
   * @param right ending position in program text
   */
  public StmtWhile (Expr e, Stmt s, int left, int right) {
    super(left, right);
    expr = e;
    stmt = s;
  }

  /**
   * Handles AST visiting of StmtWhile nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitStmtWhile(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

