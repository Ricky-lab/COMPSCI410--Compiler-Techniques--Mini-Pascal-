package MPC;
/**
 * Representation for declarations of member-like things (fields, methods)
 *
 * @version 1.0
 */

public abstract class DeclMember extends Decl {

  /**
   * Type of the member
   */
  public Type type;
  // Note: cannot be final because we allocate these objects and set their
  // type later

  /**
   * Creates a new DeclMember instance, given its Binding (name), type, start, and end
   *
   * @param b the binding of this member declaration
   * @param t te type
   * @param left an int giving the starting position of the member declaration
   * @param right an int giving the end position of the declaration
   */
  public DeclMember (Binding b, Type t, int left, int right) {
    super(b, left, right);
    type = t;
  }

  /**
   * Creates a new DeclMember instance, given its Binding (name), start, and end
   *
   * @param b the binding of this field declaration
   * @param left an int giving the starting position of the member declaration
   * @param right an int giving the end position of the declaration
   */
  public DeclMember (Binding b, int left, int right) {
    this(b, Type.theErrorType, left, right);
  }

  /**
   * Creates a new DeclMember instance, given its Binding (name)
   *
   * @param b the binding of this field declaration
   * @param t the type of this field declaration
   */
  public DeclMember (Binding b, Type t) {
    this(b, t, b.pos, -1);
  }

  /**
   * Creates a new DeclMember instance, given its Binding (name)
   *
   * @param b the binding of this field declaration
   */
  public DeclMember (Binding b) {
    this(b, b.pos, -1);
  }

  /**
   * Handles AST visiting for DeclMember nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitDeclMember(this);
    super.acceptAfter(v);
  }

  /**
   * Handle AST visiting work that should happen before visiting each DeclMember
   *
   * @param v an ASTVisitor
   */
  public void acceptBefore (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitDeclMemberBefore(this);
  }

  /**
   * Handle AST visiting work that should happen after visiting each DeclMember
   *
   * @param v an ASTVisitor
   */
  public void acceptAfter (ASTVisitor v) {
    v.visitDeclMemberAfter(this);
    super.acceptAfter(v);
  }
}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

