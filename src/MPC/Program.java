package MPC;
/**
 * The class Program represents a <i>Pascal</i> program.
 *
 * Every <i>Pascal</i> program consists of a list of declarations and a block.
 * The declarations include all those objects local to the program block and
 * the block includes those statements intended to execute upon those objects.
 * This information is maintained by member variables of type
 * Decls and Block.
 *
 * @version 1.0
 */
public final class Program extends ASTNode {

  /**
   * a Decls containing Decl nodes for the top-level declarations of the
   * program
   */
  public final Decls decls;

  /**
   * Block containing the top-level statements of the Program
   */
  public final Block block;


  /**
   * Creates a new Program instance given the top-level Decls, Block, and
   * source (start/end) position
   *
   * @param dd     declarations for the program block.
   * @param blk    code block of the program.
   * @param left   starting position of program declaration.
   * @param right  ending position of program declaration.
   */
  public Program (Decls dd, Block blk, int left, int right) {
    super(left, right);
    decls  = dd;
    block  = blk;
  }

  /**
   * Handles AST visiting for Program nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    if (v == null) return;  // allows more graceful handling of some error conditions
    super.acceptBefore(v);
    v.visitProgram(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:
