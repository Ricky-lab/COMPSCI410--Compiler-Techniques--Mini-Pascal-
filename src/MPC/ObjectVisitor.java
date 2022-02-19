package MPC;
/**
 * This abstract class defines things common to all MPCObject visitor classes.
 *
 * Mostly we omit javadoc for the methods since they are obvious
 *
 * @version 1.0
 */
public interface ObjectVisitor {

  // methods for work related to every kind of MPCObject
  public void visitEveryBefore (MPCObject n);
  public void visitEveryAfter  (MPCObject n);
  
  // methods for work related to each subclass
  public void visitObjectValueError (ObjectValueError e);

  public void visitObjectValueInteger (ObjectValueInteger i);

  public void visitObjectValueString (ObjectValueString s);

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:
