package MPC;
/**
 * Representation of a FOR statement.
 *
 * <i>
 * The for statement indicates that a statement is to be repeatedly executed
 * while a progression of values is assigned to a variable that is called the
 * control variable of the for statement.
 * </i>
 *
 * Example:
 * <pre>
 *   for I := 1 to 63 do
 *      if I GrayScale[I] &gt; 0.5 then write ('*')
 *      else write (' ')
 * </pre>
 *
 * @version 1.0
 */
public class StmtFor extends Stmt {

  /**
   * Binding (name) of the control variable
   */
  public final Binding name;

  /**
   * the initialization Expr
   */
  public Expr init;

  /**
   * direction of progression
   */
  public final boolean upward;

  /**
   * final (ending value) Expr
   */
  public Expr to;

  /**
   * Stmt to execute
   */
  public final Stmt stmt;

  /**
   * Creates a new StmtFor instance given the Binding (name) of
   * its control variable, the init Expr, direction of progression (up/down),
   * the final/limit Expr, the Stmt for the loop body, and the source
   * (start/end) position
   *
   * @param b     Binding (name) of the control variable
   * @param i     initialization Expr
   * @param up    direction of progression
   * @param t     final Expr
   * @param st    Stmt to execute
   * @param left  starting position in program text
   * @param right ending position in program text
   */
  public StmtFor (Binding b, Expr i, boolean up, Expr t, Stmt st, int left, int right) {
    super(left, right);
    name   = b;
    init   = i;
    upward = up;
    to     = t;
    stmt   = st;
  }

  /**
   * Handles AST visiting of StmtFor nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitStmtFor(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

