package MPC;
/**
 * The class Block represents the declaration part of a 
 * <i>Pascal</i> program/function/procedure, in which all objects local 
 * to the program/function/procedure are defined, and a statement part, 
 * which specifies the actions to be executed upon these objects.
 *
 * @version 1.0
 */
public final class Block extends ASTNode {

  /**
   * decls contains all declarations local to the block.
   */
  public final Decls decls;

  /**
   * stmts contains all statements local to the block.
   */
  public final Stmts stmts;

  /**
   * Creates a new Block instance.
   *
   * @param dd declarations found in the program/function/procedure.
   * @param ss statements found in the program/function/procedure.
   * @param left starting position of program declaration.
   * @param right ending position of program declaration.
   */
  public Block (Decls dd, Stmts ss, int left, int right) {
    super(left, right);
    decls  = dd;
    stmts  = ss;
  }

  /**
   * Handles AST visiting for Block nodes.
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitBlock(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

