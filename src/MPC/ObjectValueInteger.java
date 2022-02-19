package MPC;
/**
 * An ObjectValueInteger describes a run-time value that is an integer
 * constant
 *
 * @author Eliot Moss
 * @version 1.0
 */
public class ObjectValueInteger extends MPCObject {

  /**
   * unique TokenInt giving the value of the integer constant
   */
  public final TokenInt value;

  /**
   * Creates a new ObjectValueInteger instance given its TokenInt
   * value
   *
   * @param v a TokenInt giving the value of the integer constant
   * value
   */
  public ObjectValueInteger (TokenInt v) {
    this(v, Type.theIntType);
  }

  /**
   * Creates a new ObjectValueInteger instance with the given
   * mathematical integer constant value and the given Mini-Pascal Type
   *
   * @param v a TokenInt giving the value
   * @param t a Type giving the Mini-Pascal type of the constant
   */
  public ObjectValueInteger (TokenInt v, Type t) {
    value = v;
    type  = t;
  }

  /**
   * Handles MPCObject visiting for ObjectValueInteger nodes
   *
   * @param v an ObjectVisitor
   */
  public void accept (ObjectVisitor v) {
    super.acceptBefore(v);
    v.visitObjectValueInteger(this);
    super.acceptAfter(v);
  }

  /**
   * getValue returns the integer value of this ObjectValueInteger.
   *
   * @return the integer value of this value object
   */
  public int getValue () {
    return value.value;
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

