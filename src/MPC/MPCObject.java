package MPC;
/**
 * An MPCObject represent the location (or, for constants, the
 * value) of an entity at run time. There are several kinds, each with their
 * own subclass.
 *
 * @author Eliot Moss
 * @version 1.0
 */
public abstract class MPCObject {

  /**
   * built-in primitive <i>Mini-Pascal</i> TRUE object
   */
  public static final MPCObject theTrueObject;

  /**
   * built-in primitive <i>Mini-Pascal</i> FALSE object
   */
  public static final MPCObject theFalseObject;

  /**
   * built-in primitive <i>Mini-Pascal</i> NIL object
   */
  public static final MPCObject theNilObject;

  /**
   * unique special error object
   */
  public static final MPCObject theErrorObject;

  public static boolean Ok (MPCObject obj) {
    if (obj == null)
      return false;
    else if (obj == theErrorObject)
      return false;
    else
      return true;
  }

  static {
    /* initialize built-in <i>Mini-Pascal</i> objects */
    theTrueObject  = new ObjectValueInteger(TokenInt.get(1), Type.theBoolType);
    theFalseObject = new ObjectValueInteger(TokenInt.get(0), Type.theBoolType);
    theNilObject   = new ObjectValueInteger(TokenInt.get(0), Type.theNilType );
    theErrorObject = ObjectValueError.theErrorObject;
  }

  /**
   * Type of the object
   */
  public Type type;

  /**
   * Handles MPCObject visiting work done before visiting the MPCObject
   *
   * @param v an ObjectVisitor
   */
  public void acceptBefore (ObjectVisitor v) {
    v.visitEveryBefore(this);
  }

  /**
   * Handles MPCObject visiting for MPCObject nodes
   *
   * @param v an ObjectVisitor
   */
  public abstract void accept (ObjectVisitor v);

  /**
   * Handles MPCObject visiting work done after visiting the MPCObject
   *
   * @param v an ObjectVisitor
   */
  public void acceptAfter (ObjectVisitor v) {
    v.visitEveryAfter(this);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

