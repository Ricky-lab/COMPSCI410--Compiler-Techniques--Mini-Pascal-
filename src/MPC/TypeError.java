package MPC;
/**
 * TypeError represents the result of certain parsing or semantic errors,
 * particularly errors in type checking a construct; it has a single instance,
 * theErrorType
 *
 * @author Eliot Moss
 * @version 1.0
 */
public class TypeError extends Type {

  /**
   * this is the unique instance of this Type
   */
  public static final TypeError theErrorType = new TypeError();

  /**
   * The obvious constructor; private to insure we have just the single
   * instance, theErrorType
   *
   */
  private TypeError () {
    super(-1, -1);
  }

  /**
   * Handles AST visiting for TypeError nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitTypeError(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

