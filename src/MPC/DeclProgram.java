package MPC;
/**
 * Representation of a program declaration; note that this is just the NAME of
 * the program, and is of little use
 *
 * @version 1.0
 */
public class DeclProgram extends Decl {

  /**
   * Creates a new DeclProgram instance given the Binding (name)
   *
   * @param b a Binding for this program declaration
   */
  public DeclProgram (Binding b) {
    super(b, b.pos, b.endPos);
  }

  /**
   * Handles AST visiting for DeclProgram nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitDeclProgram(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

