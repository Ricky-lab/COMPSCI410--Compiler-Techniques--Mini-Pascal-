package MPC;
/**
 * Representation for a type declaration.
 *
 * @version 1.0
 */
public class DeclType extends Decl {
  
  /**
   * Type being declared
   */
  public Type type;

  /**
   * Creates a new DeclType instance given its Binding (name),
   * Type, and source (start/end) position
   *
   * @param b     Binding (name) of this type declaration
   * @param t     Type declared by this declaration
   * @param left  starting position in program text
   * @param right ending position in program text
   */
  public DeclType (Binding b, Type t, int left, int right) {
    super(b, left, right);
    type = t;
    if (t instanceof TypeObjectPointer) {
      // we want this recorded early in the compiler, but it's hard to
      // do directly in the parser
      final TypeObject to = (TypeObject)((TypeObjectPointer)t).element;
      to.id = b.id;
    }
    b.decl = this;
  }

  /**
   * Handles AST visiting for DeclType nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitDeclType(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

