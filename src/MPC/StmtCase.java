package MPC;
import java.util.*;

/**
 * Representation of a CASE statement.
 *
 * <i>
 * The case statement consists of an ordinal expression (the case index)
 * and a list of statements, each being prefixed by one or more constants
 * of the type of the case index.  It specifies that the one statement be
 * executed that is prefixed by the value of the case index; it is an error
 * if no constant denoting that value prefixes any statement.  Each
 * value must be specified by at most one case constant.
 * </i>
 *
 * Example:
 * <pre>
 *    case Operator of
 *      Plus:   W := X+Y;
 *      Minus:  W := X-Y;
 *      Times:  W := X*Y
 *    end
 * </pre>
 *
 * @version 1.0
 */
public class StmtCase extends Stmt {

  /**
   * the ordinal expression (case index) Expr
   */
  public Expr expr;

  /**
   * List of case elements
   * @see CaseElement
   */
  public final List<CaseElement> list;

  /**
   * Creates a new StmtCase instance given its case index Expr,
   * List of CaseElement's, and source (start/end) position
   *
   * @param e     case index Expr (expression being tested)
   * @param l     List of CaseElement objects
   * @param left  starting position in program text
   * @param right ending position in program text
   */
  public StmtCase (Expr e, List<CaseElement> l, int left, int right) {
    super(left, right);
    expr = e;
    list = l;
  }

  /**
   * Handles AST visiting of StmtCase nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitStmtCase(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

