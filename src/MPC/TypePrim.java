package MPC;
/**
 * Represents a (Mini-)Pascal primitive type.
 *
 * @version 1.0
 */
public abstract class TypePrim extends Type {

  /**
   * String name of this primitive type
   */
  public final String name;

  /**
   * Creates a new TypePrim instance given its String name
   *
   * @param n String name of this primitive type
   */
  protected TypePrim (String n) {
    super(-1, -1);
    name = n;
  }

  /**
   * Handles AST visiting work that comes before visiting a TypePrim node
   *
   * @param v an ASTVisitor
   */
  public void acceptBefore (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitTypePrimBefore(this);
  }

  /**
   * Handles AST visiting work that comes after visiting a TypePrim node
   *
   * @param v an ASTVisitor
   */
  public void acceptAfter (ASTVisitor v) {
    v.visitTypePrimAfter(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

