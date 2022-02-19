package MPC;

/**
 * Representation of a (MiniObject-)Pascal method.  A method can be abstract
 * (have no defined implementation).
 */
public class DeclMethod extends DeclMember {

  /**
   * The Binding naming the implementing proc/func (may be null)
   */
  public Binding implBind;

  /**
   * The DeclProcFunc of the implementing procedure/function
   */
  public DeclProcFunc implementation;

  /**
   * Creates a new DeclMethod instance given a signature, a binding for its
   * implementation, and its source (start/end) position.
   *
   * @param signature the DeclMethod's signature
   * @param implBind the Binding for the implementation, or null for an abstract method
   * @param left start position in program text
   * @param right end position in program text
   */
  public DeclMethod (TypeProcFunc signature, Binding implBind, int left, int right) {
    super(signature.name, left, right);
    this.type           = signature;
    this.implBind       = implBind;
    this.implementation = null;
  }

  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitDeclMethod(this);
    super.acceptAfter(v);
  }
}
