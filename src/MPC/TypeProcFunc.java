package MPC;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a (MiniObject-)Pascal procedure/function signature, containing
 * formal parameters, result type, and binding (name).  While the binding is not
 * strictly necessary, it is convenient for identification when printing, etc.
 */
public class TypeProcFunc extends Type {
  /**
   * Declaration list of formal parameters.  Note that methods must have at
   * least one formal parameter, which formal designates the invocant or
   * target of the method and must be the first formal and be of a type
   * compatible with the object type in which this method is defined (in
   * practice, these will always be the same type, as it makes no sense for
   * the formal to be a supertype.
   */
  public final Decls formals;

  /**
   * the Type of the result of this method (null if method is procedural)
   */
  public Type resultType;

  /**
   * the Binding in the containing object type through which this method is
   * called
   */
  public final Binding name;

  /**
   * Creates a new TypeProcFunc given its binding, formals, result type and
   * source (start/end) position.
   *
   * @param binding Binding corresponding to the method name as exposed by the TypeObject
   * @param formals Decls of the formal parameters to the method (used only for the types)
   * @param resultType Type of the result value (if any; specify null for a procedure
   * @param left starting position in program text
   * @param right ending position in program text
   */
  public TypeProcFunc (Binding binding, Decls formals, Type resultType, int left, int right) {
    super(left, right);

    this.name       = binding;
    this.formals    = formals;
    this.resultType = resultType;
  }

  /**
   * the usual visitor machinery
   *
   * @param v an ASTVisitor
   */
  @Override
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitTypeProcFunc(this);
    super.acceptAfter(v);
  }

}
