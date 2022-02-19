package MPC;
/**
 * Representation of a Pascal pointer type.
 *
 * @version 1.0
 */
public class TypePointer extends Type {
  /**
   * element (referent) Type: Type of variables to which instances of this
   * pointer Type refer
   */
  public Type element;

  /**
   * Binding (name) of the referent/element Type
   */
  public Binding bind;

  /**
   * Creates a new TypePointer instance given its referent Type
   *
   * @param t the referent Type
   */
  public TypePointer (Type t) {
    super(t.pos, t.endPos);
    element = t;
  }

  /**
   * Handles AST visiting for TypePointer nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitTypePointer(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

