package MPC;
/**
 * Represents a Pascal subrange type.
 *
 * @version 1.0
 */
public class TypeRange extends Type {

  /**
   * Expr giving smallest value of subrange type; should be a constant
   */
  public final Expr lo;

  /**
   * Expr giving largest value of subrange type; should be a constant
   */
  public final Expr hi;

  /**
   * base Type (type of values) of the subrange
   */
  public Type base;

  /**
   * Creates a new TypeRange instance given its lo and hi Expr
   * nodes
   *
   * @param le smallest value
   * @param he largest value
   */
  public TypeRange (Expr le, Expr he) {
    super(le.pos, he.endPos);
    lo = le;
    hi = he;
  }

  /**
   * Handles AST visiting of typeRange nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitTypeRange(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

