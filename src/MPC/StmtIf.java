package MPC;
/**
 * Representation of an IF statement.
 *
 * <i>
 * The 'if' statement specifies that the statement following the symbol 'then' be
 * executed only if the Boolean expression yields true.  If it is false, then
 * the statement following the symbol 'else', if any, is to be executed.
 * </i>
 *
 * @version 1.0
 */
public class StmtIf extends Stmt {

  /**
   * the boolean Expr controlling the IF statement
   */
  public Expr expr;

  /**
   * the then Stmt
   */
  public final Stmt stmtThen;

  /**
   * the else Stmt (may be null if no else clause)
   */
  public final Stmt stmtElse;

  /**
   * Creates a new StmtIf instance given its controlling boolean
   * Expr, then Stmt, else Stmt (which may be null), and source (start/end)
   * position
   *
   * @param e     boolean Expr
   * @param st    then Stmt
   * @param se    else Stmt
   * @param left  starting position in the program text
   * @param right ending position in the program text
   */
  public StmtIf (Expr e, Stmt st, Stmt se, int left, int right) {
    super(left, right);
    expr     = e;
    stmtThen = st;
    stmtElse = se;
  }

  /**
   * Handles AST visiting of StmtIf nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitStmtIf(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

