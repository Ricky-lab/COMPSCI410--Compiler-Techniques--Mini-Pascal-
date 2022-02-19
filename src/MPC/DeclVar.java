package MPC;
/**
 * Representation of a variable declaration.
 *
 * @version 1.0
 */
public class DeclVar extends Decl {

  /**
   * Type of this variable
   */
  public Type type;
  // Note: cannot be final because we allocate DeclVar objects and set their
  // type later (see Decls.parseVarDecls)

  /**
   * Creates a new DeclVar instance given its Binding (name)
   *
   * @param b the binding (name) of this variable declaration
   * @param t the type of this variable declaration
   */
  public DeclVar (Binding b, Type t) {
    super(b, b.pos, t.endPos);
    this.type = t;
  }

  /**
   * Handles AST visiting for DeclVar nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitDeclVar(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

