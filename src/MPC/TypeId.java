package MPC;
/**
 * Represents a user defined type identifier.
 *
 * @version 1.0
 */
public class TypeId extends Type {

  /**
   * the Binding (name) of this Type
   */
  public final Binding bind;

  /**
   * Creates a new TypeId instance given its Binding (name)
   *
   * @param b the Binding (name) of this Type
   */
  public TypeId (Binding b) {
    super(b.pos, b.endPos);
    bind = b;
  }

  /**
   * Handles AST visiting of TypeId nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitTypeId(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

