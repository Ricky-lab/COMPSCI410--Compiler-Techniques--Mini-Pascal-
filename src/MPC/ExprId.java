package MPC;
/**
 * Represents an identifier expression.  This is used to hold a field
 * identifier, which denotes a field of a record variable.
 *
 * @version 1.0
 */
public class ExprId extends Expr {

  /**
   * the identifier (TokenId)
   */
  public final TokenId id;

  /**
   * Creates a new ExprId instance given the TokenId and source
   * (start/end) position
   *
   * @param i     the identifier (TokenId)
   * @param left  starting position in the program text
   * @param right ending position in the program text
   */
  public ExprId (TokenId i, int left, int right) {
    super(left, right);
    id = i;
  }

  /**
   * Handles AST visiting for ExprId nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitExprId(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

