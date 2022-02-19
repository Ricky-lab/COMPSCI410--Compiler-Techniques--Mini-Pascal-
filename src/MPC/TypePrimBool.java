package MPC;
/**
 * Represents the (Mini-)Pascal primitive type Boolean.
 *
 * @version 1.0
 */
public class TypePrimBool extends TypePrim {

  /**
   * unique Java object representing this primitive Type
   */
  public static final TypePrimBool theBoolType = new TypePrimBool();

  /**
   * Creates a new TypePrimBool instance; private since we allow
   * just the one instance theBoolType
   */
  private TypePrimBool () {
    super("boolean");
  }

  /**
   * Handles AST visiting for TypePrimBool nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitTypePrimBool(this);
    super.acceptAfter(v);
  }

}
// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

