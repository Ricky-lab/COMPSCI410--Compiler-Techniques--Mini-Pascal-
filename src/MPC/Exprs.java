package MPC;
import java.util.*;

/**
 * Representation of a list of expressions
 *
 * @version 1.0
 */
public final class Exprs extends ASTNode implements Iterable<Expr> {

  /**
   * a List holding the Expr nodes
   */
  public final List<Expr> exprs;

  /**
   * Handy for empty argument lists in calls
   */
  public static final Exprs emptyList = new Exprs(new ArrayList<Expr>(0), -1, -1);

  /**
   * Creates a new Exprs instance given the List of Expr
   * nodes and the source (start/end) position
   *
   * @param exprs List of Expr nodes
   * @param left  starting position in program text
   * @param right ending position in program text
   */
  public Exprs (List<Expr> exprs, int left, int right) {
    super(left, right);
    this.exprs = exprs;
  }

  /**
   * Handles AST visiting for Exprs nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitExprs(this);
    super.acceptAfter(v);
  }

  /**
   * Provides a convenient iterator over the Expr nodes in the list
   *
   * @return an Iterator that returns each Expr node in turn
   */
  public Iterator<Expr> iterator () {
    return exprs.iterator();
  }

  /**
   * Accessor that indicates how many Expr nodes are in the list
   *
   * @return an int giving the number of Expr nodes in the list
   */
  public int size () {
    return exprs.size();
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

