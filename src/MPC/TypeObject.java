package MPC;

import java.util.*;

/**
 * Representation of a (MiniObject-)Pascal object type.
 */
public class TypeObject extends Type {

  /**
   * The name of the object type (they are unique and top-level)
   */
  public TokenId id;

  /**
   * The fields of an instance of this object type.
   */
  public final List<DeclField> fields;

  /**
   * The methods defined by this object type
   */
  public final List<DeclMethod> methods;

  /**
   * The supertype methods overridden by this object type
   */
  public final List<Overriding> overrides;

  /**
   * The supertype of this object type
   */
  public Type supertype;  // can't be final because type checking updates it

  /**
   * is this object type declared ASBTRACT?
   */
  public final boolean isAbstract;

  /**
   * Creates a new TypeObject instance given its supertype; lists of its
   * fields, methods, and overrides; and its source (start/end) position
   * @param supertype a Type giving the supertype of this OBJECT type
   * @param fields a List&lt;DeclField&gt; giving the fields declared with this type
   * @param methods a List&lt;DeclMethod&gt; giving the methods declared with this type
   * @param overrides a List&lt;Overriding&gt; giving the overrides declared with this type
   * @param isAbstract a boolean indicating whether this type is declared ASBTRACT
   * @param left an int giving the starting position of this type's declaration
   * @param right an int giving the ending position of this type's declaration
   */
  public TypeObject (final Type supertype,
		     final List<DeclField> fields,
		     final List<DeclMethod> methods,
		     final List<Overriding> overrides,
		     final boolean isAbstract,
		     final int left,
		     final int right) {
    super(left,right);

    this.supertype  = supertype;
    this.fields     = fields;
    this.methods    = methods;
    this.overrides  = overrides;
    this.isAbstract = isAbstract;

  }

  /**
   * Handles AST visiting for TypeObject nodes
   */
  @Override
  public void accept (final ASTVisitor v) {
    super.acceptBefore(v);
    v.visitTypeObject(this);
    super.acceptAfter(v);
  }

}
