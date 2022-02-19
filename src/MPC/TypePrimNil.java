package MPC;
/**
 * Represents the (Mini-)Pascal primitive type for Pascal NIL.
 *
 * @version 1.0
 */
public class TypePrimNil extends TypePrim {

  /**
   * unique Java object representing this Type
   */
  public static final TypePrimNil theNilType = new TypePrimNil();

  /**
   * Creates the unique TypePrimNil instance, namely theNilType
   */
  private TypePrimNil () {
    super("<NIL>");
  }

  /**
   * Handles AST visiting for TypePrimNil nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitTypePrimNil(this);
    super.acceptAfter(v);
  }

}
// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

