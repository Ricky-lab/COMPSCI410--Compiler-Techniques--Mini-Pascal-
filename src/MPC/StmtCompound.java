package MPC;
/**
 * Representation of a compound statement.
 *
 * <i>
 * A compound statement specifies the execution of the statement
 * sequence.  The symbols begin and end
 * act as statement brackets.
 * </i>
 *
 * Examples:
 * <pre>
 *   begin W := X; X := Y; Y := W end
 *   begin    end
 * </pre>
 *
 * @version 1.0
 */
public class StmtCompound extends Stmt {

  /**
   * the sequences of Stmt nodes in the StmtCompound
   */
  public final Stmts stmts;

  /**
   * Creates a new StmtCompound instance given its Stmts and
   * source (start/end) position
   *
   * @param ss    list of statements (a Stmts object)
   * @param left  starting position in program text
   * @param right ending position in program text
   */
  public StmtCompound (Stmts ss, int left, int right) {
    super(left, right);
    stmts = ss;
  }

  /**
   * Handles AST visiting for StmtCompound nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitStmtCompound(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

