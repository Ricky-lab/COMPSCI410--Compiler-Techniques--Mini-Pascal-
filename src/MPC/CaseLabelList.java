package MPC;
import java.util.*;

/**
 * A CaseLabelList contains a list of Expr nodes giving the values that select
 * a particular case of a Pascal CASE statement.
 *
 * @author Eliot Moss
 * @version 1.0
 */
public final class CaseLabelList extends ASTNode implements Iterable<Expr> {

  /**
   * the list of Expr nodes giving the values
   */
  public final Exprs labels;

  /**
   * Creates a new CaseLabelList instance, given a List of
   * Expr nodes for the values that select this case of the CASE statement and
   * the start/end source code positions.
   *
   * @param ll a List holding Expr nodes for the values that select this case
   * @param left an int giving the starting position in the source
   * @param right an int giving the ending position in the source
   */
  public CaseLabelList (List<Expr> ll, int left, int right) {
    super(left, right);
    labels = new Exprs(ll, left, right);
  }

  /**
   * Handles AST visiting for CaseLabelList nodes.
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitCaseLabelList(this);
    super.acceptAfter(v);
  }

  /**
   * Accessor indicating how many labels there are in this list
   *
   * @return an int indicating the number of labels in this list
   */
  public int size () {
    return labels.size();
  }

  /**
   * Makes it easy to iterate over the labels, each of which should be an Expr
   *
   * @return an Iterator that iterates over the Expr nodes that
   * give the values that select this case of the CASE statement
   */
  public Iterator<Expr> iterator () {
    return labels.iterator();
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

