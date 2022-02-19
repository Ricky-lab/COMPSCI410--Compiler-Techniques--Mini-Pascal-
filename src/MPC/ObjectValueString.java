package MPC;
/**
 * An ObjectValueString describes a run-time value that is a string constant
 *
 * @author Eliot Moss
 * @version 1.0
 */
public class ObjectValueString extends MPCObject {

  /**
   * unique TokenString giving the value of the string constant
   */
  public TokenString text;

  /**
   * Creates a new ObjectValueString instance given its TokenString value
   *
   * @param t a TokenString giving the value of the string constant value
   */
  public ObjectValueString  (TokenString t) {
    text = t;
    type = Type.theStringType;
  }

  /**
   * Handles MPCObject visiting for ObjectValueString nodes
   *
   * @param v an ObjectVisitor
   */
  public void accept (ObjectVisitor v) {
    super.acceptBefore(v);
    v.visitObjectValueString(this);
    super.acceptAfter(v);
  }

  /**
   * getStringValue returns the string value of this
   * ObjectValueString.
   *
   * @return the string value of this value object
   */
  public String getStringValue () {
    return text.string;
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

