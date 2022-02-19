package MPC;
import static MPC.TokenOp.*;
/**
 * Representation of a unary expression.
 *
 * Examples:
 * <pre>
 *   +5
 *   -foo
 * </pre>
 *
 * @version 1.0
 */
public class ExprUnary extends Expr {

  /**
   * the operator token (TokenOp); cannot be final because sometimes we insert
   * operations into the tree
   */
  public final Token op;

  /**
   * the Expr upon which the operator operates; cannot be final because
   * sometimes we insert operations into the tree
   */
  public Expr expr;

  /**
   * Creates a new ExprUnary instance given the TokenOp
   * (operator), Expr (subexpression), and the source (start/end) position
   *
   * @param o     the operator token (TokenOp)
   * @param e     the operand expression (Expr)
   * @param left  starting position in program text
   * @param right ending position in program text
   */
  public ExprUnary (Token o, Expr e, int left, int right) {
    super(left, right);
    op   = o;
    expr = e;
  }

  /**
   * Handles AST visiting of ExprUnary nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitExprUnary(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

