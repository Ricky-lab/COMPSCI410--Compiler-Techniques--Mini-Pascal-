package MPC;
/**
 * Representation for a field declaration within a record declaration.
 *
 * @version 1.0
 */
public class DeclField extends DeclMember {

  /**
   * Creates a new DeclField instance, given its Binding (name) and type
   *
   * @param b the binding of this field declaration
   * @param t the type of this field declaration
   */
  public DeclField (Binding b, Type t) {
    super(b, t);
  }

  /**
   * Handles AST visiting for DeclField nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitDeclField(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

