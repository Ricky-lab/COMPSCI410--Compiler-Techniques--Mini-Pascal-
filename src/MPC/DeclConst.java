package MPC;
/**
 * Represents a constant declaration in <i>Pascal</i>.
 * Example of a series of constant declarations:
 *
 * <pre>
 *   const
 *      Avocado    = 6.023E23;
 *      PageLength = 60;
 *      Border     = '# * ';
 *      MyMove     = True;
 * </pre>
 *
 * @version 1.0
 */
public class DeclConst extends Decl {

  /**
   * The right-hand side of the constant declaration
   */
  public Expr expr;

  /**
   * Creates a new DeclConst instance given its Binding (name)
   * and Expr (value). Derives source position from subcomponents.
   *
   * @param b the Binding (name) of this declaration
   * @param e the Expr giving the constant value
   */
  public DeclConst (Binding b, Expr e) {
    super(b, b.pos, e.endPos);
    expr = e;
  }

  /**
   * Handles AST visiting for DeclConst nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitDeclConst(this);
    super.acceptAfter(v);
  }


}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

