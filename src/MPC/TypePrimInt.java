package MPC;
/**
 * Represents the (Mini-)Pascal primitive type INTEGER.
 *
 * @version 1.0
 */
public class TypePrimInt extends TypePrim {

  /**
   * unique Java object representing this Type
   */
  public static final TypePrimInt theIntType = new TypePrimInt();

  /**
   * Creates the unique TypePrimInt instance, held in theIntType
   */
  private TypePrimInt () {
    super("integer");
  }

  /**
   * Handles AST visiting for TypeInt nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitTypePrimInt(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

