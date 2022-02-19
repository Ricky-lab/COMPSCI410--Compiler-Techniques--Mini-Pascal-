package MPC;
/**
 * Representation of an array type.
 *
 * @version 1.0
 */
public class TypeArray extends Type {

  /**
   * the index Type of this array Type
   */
  public Type indexType;

  /**
   * the element Type of this array Type
   */
  public Type elementType;

  /**
   * Creates a new TypeArray instance given its index Type,
   * element Type, and source (start/end) position.
   *
   * @param idx   The index type
   * @param elt   Type of the elements
   * @param left  starting position in program text
   * @param right ending positin in program text
   */
  public TypeArray (Type idx, Type elt, int left, int right) {
    super(left, right);
    indexType   = idx;
    elementType = elt;
  }

  /**
   * Handles AST visiting for TypeArray nodes
   *
   * @param v an ASTVisitor value
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitTypeArray(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

