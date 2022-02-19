package MPC;
import java.util.*;

/**
 * Representation of a list of statements.
 *
 * @version 1.0
 */
public final class Stmts extends ASTNode implements Iterable<Stmt> {

  /**
   * a List of Stmt nodes
   */
  public final List<Stmt> stmts;

  /**
   * Creates a new Stmts instance given the List of Stmt
   * nodes and source (start/end) position
   *
   * @param stmts List of Stmt nodes
   * @param left  starting position in program text
   * @param right ending position in program text
   */
  public Stmts (List<Stmt> stmts, int left, int right) {
    super(left, right);
    this.stmts = stmts;
  }

  /**
   * Handles AST visiting of Stmts nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitStmts(this);
    super.acceptAfter(v);
  }

  /**
   * Convenience iterator that produces the constituent Stmt nodes in turn
   *
   * @return an Iterator that produces the Stmt nodes in turn
   */
  public Iterator<Stmt> iterator () {
    return stmts.iterator();
  }

  /**
   * Accessor indicating the number Stmt nodes of this Stmts
   *
   * @return an int giving the number of Stmt nodes in this Stmts
   */
  public int size () {
    return stmts.size();
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

