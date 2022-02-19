package MPC;
import java.util.*;

/**
 * Representation forr a procedure/function declaration.
 *
 * @version 1.0
 */
public class DeclProcFunc extends Decl {

  /**
   * type (signature) of the proc/func
   */
  public final TypeProcFunc type;

  /**
   * Block for the procedure/function body
   */
  public final Block block;
  
  /**
   * Creates a new DeclProcFunc instance given its signature (TypeProcFunc,
   * which includes its name), body (a Block), and start/end source code position
   *
   * @param type  a TypeProcFunc giving the signature of the proc/func
   * @param block procedure/function body Block
   * @param left  starting position in program text
   * @param right ending position in program text
   */
  public DeclProcFunc (final TypeProcFunc type, final Block block,
                       final int left, final int right) {
    super(type.name, left, right);
    this.type  = type;
    this.block = block;
  }

  /**
   * Handles AST visiting for DeclProcFunc nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (final ASTVisitor v) {
    super.acceptBefore(v);
    v.visitDeclProcFunc(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

