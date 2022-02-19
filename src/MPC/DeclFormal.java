package MPC;
/**
 * Representation of a formal parameter declaration within a function or 
 * procedure declaration.
 *
 * @version 1.0
 */
public class DeclFormal extends DeclMember {

  /**
   * type for this formal declaration
   */
  public Type type;
  // Note: cannot be final because we allocate DeclFormal objects and set
  // their type later

  /**
   * indicates the parameter mode (is it VAR?)
   */
  public boolean isVar;  // parameter mode: is it VAR?
  // Cannot be final for same reason

  /**
   * Creates a new DeclFormal instance given its Binding (name)
   *
   * @param isVar true for VAR parameters, false for by-value ones
   * @param b the binding to this formal declaration
   * $param t the type (always a TypeId)
   */
  public DeclFormal (boolean isVar, Binding b, Type t, int endPos) {
    super(b, b.pos, endPos);
    this.isVar = isVar;
    this.type  = t;
  }

  /**
   * Handles AST visiting for DeclFormal nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitDeclFormal(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

