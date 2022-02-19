package MPC;
import java.util.*;

/**
 * A CaseElement gives one case (labels, represented by a CaseLabelList, and a
 * Stmt) of a Pascal CASE statement
 *
 * @see StmtCase
 * @see CaseLabelList
 * @author Eliot Moss
 * @version 1.0
 */
public final class CaseElement extends ASTNode {

  /**
   * list of case labels (integer constants, either literals or identifiers)
   * for this case
   */
  public final CaseLabelList labels;

  /**
   * the Stmt (actions) for this case
   */
  public final Stmt stmt;

  /**
   * Creates a new CaseElement instance given the component
   * CaseLabelList and Stmt and the source position (start, end) of the
   * construct
   *
   * @param l a CaseLabelList containing the values that select this case
   * @param st a Stmt giving the actions for this case
   * @param left an int giving the starting source position
   * @param right an int giving the ending source position
   */
  public CaseElement (CaseLabelList l, Stmt st, int left, int right) {
    super(left, right);
    labels = l;
    stmt   = st;
  }

  /**
   * Handles AST visiting for CaseElement nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitCaseElement(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

