package MPC;
import java.util.*;
import MPC.DeclSpecial;

/**
 * The class Decls represents a list of declarations that can
 * occur in a <i>Pascal</i> program.
 *
 * Also deals with setting up the Decl's predefined for all programs.
 *
 * @version 1.0
 */
public final class Decls extends ASTNode implements Iterable<Decl> {

  /**
   * decls holds the list of declarations of this Decls
   */
  public final List<Decl> decls;

  /**
   * Creates a new Decls instance given a List of its Decl
   * nodes and its source (start/end)position
   *
   * @param dd    a List of Decl nodes
   * @param left  starting position of declarations.
   * @param right ending position of declarations.
   */
  public Decls (final List<Decl> dd, final int left, final int right) {
    super(left, right);
    decls  = dd;
  }

  /**
   * Creates a new Decls instance with no specific source code position.
   *
   * @param dd a List of Decl nodes
   */
  public Decls (final List<Decl> dd) {
    this(dd, -1, -1);
  }

  /**
   * Creates a new Decls instance consisting of a single declaration
   *
   * @param d a Decl node
   */
  public Decls (final Decl d) {
    this(new ArrayList<Decl>(1));
    decls.add(d);
    pos    = d.pos;
    endPos = d.endPos;
  }

  /**
   * Handles AST visiting for Decls nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (final ASTVisitor v) {
    super.acceptBefore(v);
    v.visitDecls(this);
    super.acceptAfter(v);
  }

  /**
   * Provides an iterator over the Decl nodes of the Decls
   *
   * @return an Iterator that produces each Decl node of the
   * Decls in turn
   */
  public Iterator<Decl> iterator () {
    return decls.iterator();
  }

  /**
   * Easy way to find out length of list of Decls
   * @return an int giving the number of Decl objects in this Decls
   */
  public int size () {
    return decls.size();
  }
}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

