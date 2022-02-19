package MPC;
import java.util.*;

/**
 * Representation of a special declaration (write, readln, etc.).
 *
 * Note that users cannot create these; we create them all on startup and put
 * them in a scope with the other predefined things.
 *
 * @version 1.0
 */
public abstract class DeclSpecial extends Decl {
  /**
   * Creates a new DeclSpecial instance given its Binding (name)
   *
   * @param b the Binding (name) of this special declaration
   */
  public DeclSpecial (final Binding b) {
    super(b, b.pos, -1);
  }

  /**
   * Handles AST visiting for DeclSpecial nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (final ASTVisitor v) {
    super.acceptBefore(v);
    v.visitDeclSpecial(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

