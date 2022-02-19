package MPC;
/**
 * Representation for a binary expression.
 *
 * @version 1.0
 */
public class ExprBinary extends Expr {

  /**
   * the operator in the binary expression; cannot be final because sometimes
   * we insert operations into the tree
   */
  public Token op;

  /**
   * the first (left hand) expression upon which the binary operatory
   * operates; cannot be final because sometimes we insert operations into the
   * tree
   */
  public Expr expr1;

  /**
   * the second (right hand) expression upon which the binary operatory
   * operates; cannot be final because sometimes we insert operations into the
   * tree
   */
  public Expr expr2;

  /**
   * Creates a new ExprBinary instance given the operator and
   * left and right subexpressions
   *
   * @param o  the operator token
   * @param e1 the left hand (sub)expression
   * @param e2 the right hand (sub)expression
   */
  public ExprBinary (Token o, Expr e1, Expr e2) {
    super(e1.pos, e2.endPos);
    op    = o;
    expr1 = e1;
    expr2 = e2;
  }

  /**
   * Handles AST visiting for ExprBinary nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitExprBinary(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

