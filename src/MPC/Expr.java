package MPC;
import MPC.ExprBinary;
import static MPC.TokenOp.*;
import java.util.*;

/**
 * Representation for expressions in <i>Pascal</i>.
 *
 * An <i>expression</i> denotes a rule of computation that yields a value when
 * the expression is evaluated.  The value that is yielded depends upon the
 * values of the constants, bindings, and variables in the expression and also
 * upon the operators and functions that the expression invokes. 
 * (Pascal Report, page 165)
 *
 * @version 1.0
 */
public abstract class Expr extends ASTNode {

  /**
   * built-in primitive <i>Mini-Pascal</i> TRUE expression
   */
  public static final Expr theTrueExpr;

  /**
   * built-in primitive <i>Mini-Pascal</i> FALSE expression
   */
  public static final Expr theFalseExpr;

  /**
   * built-in primitive <i>Mini-Pascal</i> NIL expression
   */
  public static final Expr theNilExpr;

  /**
   * sets up the (unique) TRUE, FALSE, and NIL Expr nodes
   */
  static {
    /* initialize built-in <i>Mini-Pascal</i> expressions */
    theTrueExpr         = new ExprInt(TokenInt.get(1), -1, -1);
    theTrueExpr.type    = Type.theBoolType;
    theTrueExpr.object  = MPCObject.theTrueObject;

    theFalseExpr        = new ExprInt(TokenInt.get(0), -1, -1);
    theFalseExpr.type   = Type.theBoolType;
    theFalseExpr.object = MPCObject.theFalseObject;
    
    theNilExpr          = new ExprNil(-1, -1);
    theNilExpr.type     = Type.theNilType;
    theNilExpr.object   = MPCObject.theNilObject;
  }

  /**
   * Type of this expression
   */
  public Type type;

  /**
   * MPCObject for address/value of expr
   */
  public MPCObject object;

  /**
   * Creates a new Expr instance; since the class in abstract,
   * this handles just the saving of the start/end source code positions
   *
   * @param left  starting position in program text
   * @param right ending position in program text
   */
  public Expr (final int left, final int right) {
    super(left, right);
  }

  /**
   * Static factory method for creating proper trees for
   * subscripted expressions
   *
   * @param e1 the Expr for the subscripted variable
   * @param ee a List with an Expr for each index experssion
   * @return the Expr for the whole expression
   */
  public static Expr subscriptedExprOf (Expr e1, List<Expr> ee) {
    assert(ee.size() > 0);
    for (Expr idx: ee) {
      e1 = new ExprBinary(TokenOp.LBRACK, e1, idx);
    }
    return e1;
  }

  /**
   * Handles AST visiting work to occur before visiting every Expr (or
   * subclass) node
   *
   * @param v an ASTVisitor
   */
  public void acceptBefore (final ASTVisitor v) {
    super.acceptBefore(v);
    v.visitExprBefore(this);
  }

  /**
   * Handles AST visiting work to occur after visiting every Expr (or
   * subclass) node
   *
   * @param v an ASTVisitor
   */
  public void acceptAfter (final ASTVisitor v) {
    v.visitExprAfter(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

