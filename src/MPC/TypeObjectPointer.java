package MPC;

/**
 * Representation of a Mini-Object-Pascal pointer-to-object type.
 *
 * We could represent pointers to objects as TypePointers, however, because
 * it's impossible to refer to the contained TypeObject in Mini-Object-Pascal,
 * the type checking ends up being slightly different.
 */
public class TypeObjectPointer extends TypePointer {
  /**
   * Creates a new TypeObjectPointer instance given its associated TypeObject
   *
   * @param t the referent TypeObject
   */
  public TypeObjectPointer (final TypeObject t) {
    super(t);
  }

  /**
   * Handles AST visiting for TypeObjectPointer nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (final ASTVisitor v) {
    super.acceptBefore(v);
    v.visitTypeObjectPointer(this);
    super.acceptAfter(v);
  }

  public boolean isObjectPointerType () {
    assert element instanceof TypeObject;
    return true;
  }

}
