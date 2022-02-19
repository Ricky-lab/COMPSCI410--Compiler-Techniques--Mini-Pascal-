package MPC;
import java.util.List;
/**
 * Abstract Representation of a type in the <i>Pascal</i> language as defined
 * by the <i>Pascal User Manual and Report</i>.
 *
 * @version 1.0
 */
public abstract class Type extends ASTNode {

  /**
   * special error Type
   */
  public static final TypeError theErrorType = TypeError.theErrorType;

  /**
   * built-in primitive Pascal integer type
   */
  public static final TypePrim theIntType = TypePrimInt.theIntType;

  /**
   * built-in primitive Pascal string type (for sring constants)
   */
  public static final TypePrim theStringType = TypePrimString.theStringType;

  /**
   * built-in primitive Mini-Pascal boolean type
   */
  public static final TypePrim theBoolType = TypePrimBool.theBoolType;

  /**
   * built-in primitive type for Pascal NIL (matches all pointer types)
   */
  public static final TypePrim theNilType = TypePrimNil.theNilType;

  /**
   * Creates a new Type instance.
   *
   * @param left  starting position in program text
   * @param right ending position in program text
   */
  protected Type (final int left, final int right) {
    super(left, right);
  }

  /**
   * Static factory method to create an array type given index
   * types and the element type.
   *
   * @param idxs  List of index types
   * @param elt   Type of the elements
   * @param left  starting position in program text
   * @param right ending positin in program text
   */
  public static Type arrayTypeOf (List<Type> idxs, Type elt) {
    assert(idxs.size() > 0);
    for (int i = idxs.size(); --i >= 0; ) {
      Type idx = idxs.get(i);
      elt = new TypeArray(idx, elt, idx.pos, elt.endPos);
    }
    return elt;
  }

  /**
   * Handles AST visiting work to occur before visiting each Type node
   *
   * @param v an ASTVisitor
   */
  public void acceptBefore (final ASTVisitor v) {
    super.acceptBefore(v);
    v.visitTypeBefore(this);
  }

  /**
   * Handles AST visiting work to occur after visiting each Type node
   *
   * @param v an ASTVisitor
   */
  public void acceptAfter (final ASTVisitor v) {
    v.visitTypeAfter(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// indent-tabs-mode: nil
// c-basic-offset: 2
// End:

