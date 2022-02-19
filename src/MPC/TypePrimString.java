package MPC;
/**
 * Represents the (Mini-)Pascal primitive type for Pascal string constants.
 *
 * @version 1.0
 */
public class TypePrimString extends TypePrim {

  /**
   * the unique Java object for Pascal string constants
   */
  public static final TypePrimString theStringType = new TypePrimString();

  /**
   * Creates the one TypePrimString instance, namely theStringType
   */
  private TypePrimString () {
    super("string");
  }

  /**
   * Handles AST visiting for TypePrimString nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitTypePrimString(this);
    super.acceptAfter(v);
  }

}
// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

