package MPC;
import java.util.*;

/**
 * Representation of a function or procedure call.
 *
 * Examples:
 * <pre>
 *    Next
 *    Transpose(A,N,N)
 *    Bisect(Fct, -1.0, +1.0, X)
 *    Writeln(Output, ' Title')
 * </pre>
 *
 * @version 1.0
 */
public class ExprCall extends Expr {

  /**
   * the Binding (name) of the function/procedure to call
   */
  public final Binding bind;

  /**
   * a list (Exprs) of the actual parameters being passed to this
   * procedure/function in this call
   */
  public final Exprs actuals;

  /**
   * Whether or not this is a method call.
   *
   * Method calls pass a this/self object reference as their first argument,
   *  and the function to invoke is dynamically dispatched based on that
   *  argument.
   */
  public final boolean isMethodCall;

  /**
   * Creates a new ExprCall instance which could be a method call, given
   * whether or not it is a method call, its Binding (name), actuals (an
   * Exprs), and source position.  In a method call, the 'invocant' ("self")
   * will be the first actual parameter.
   *
   * @param isMethodCall whether or not this is a method call
   * @param bind the Binding (name) of the function/procedure to call
   * @param actuals list (Exprs) of the actual parameters
   * @param left starting position in program text
   * @param right ending position in program text
   */
  public ExprCall (final boolean isMethodCall, final Binding bind, final Exprs actuals,
                   final int left, final int right) {
    super(left, right);
    this.bind         = bind;
    this.actuals      = actuals;
    this.isMethodCall = isMethodCall;
  }

  /**
   * Handle AST visiting for ExprCall nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (final ASTVisitor v) {
    super.acceptBefore(v);
    v.visitExprCall(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

