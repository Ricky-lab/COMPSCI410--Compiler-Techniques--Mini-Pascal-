package MPC;

/**
 * Representation of a method override
 */
public class Overriding extends ASTNode {

  /**
   * The Binding corresponding to the name of the superclass method being
   * overridden.
   */
  public Binding overridden;

  /**
   * The Binding corresponding to the new implementation.
   */
  public Binding with;

  /**
   * The DeclMethod in the superclass we are overriding
   */
  public DeclMethod ovmeth;

  /**
   * The DeclProcFunc of the new implementation
   */
  public DeclProcFunc newImpl;

  /**
   * Creates a new Overriding given the Binding of the overridden method, the
   * Binding of the new implementation, and the source (start/end) position
   *
   * @param overridden supertype method to override
   * @param newImplementation the implementation which will replace the
   *        supertype's implementation in this type
   * @param left starting position in source
   * @param right ending position in source
   */
  public Overriding (Binding overridden, Binding newImplementation, int left, int right) {
    super(left, right);

    this.overridden = overridden;
    this.with       = newImplementation;
    this.ovmeth     = null;
    this.newImpl    = null;
  }

  @Override
  public void accept(ASTVisitor v) {
    super.acceptBefore(v);
    v.visitOverriding(this);
    super.acceptAfter(v);
  }
}
