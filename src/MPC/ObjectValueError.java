package MPC;
  /**
   * ObjectValueError is the class of the (unique) error MPCObject
   *
   * @author Eliot Moss
   * @version 1.0
   */
public class ObjectValueError extends MPCObject {

  /**
   * the unique error MPCObject
   */
  public static final ObjectValueError theErrorObject = new ObjectValueError();

  /**
   * Creates a new ObjectValueError instance; private since we
   * create a unique one on class initialization
   */
  private ObjectValueError () {
    // written to insure there is only one instance
    type = Type.theErrorType;
  }

  /**
   * Handles MPCObject visiting for ObjectValueError nodes
   *
   * @param v an ObjectVisitor
   */
  public void accept (ObjectVisitor v) {
    super.acceptBefore(v);
    v.visitObjectValueError(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

