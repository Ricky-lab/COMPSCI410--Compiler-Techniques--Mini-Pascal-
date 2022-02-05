package MPC;
/**
 * This abstract class provide a standard way of instantiating and invoking a
 * phase of the Mini-Pascal compiler. Each phase is a concrete subclass of
 * Phase. The type variable R is for the result of the perform method, and the
 * type variable A is for the argument to the perform method.
 *
 * @version 1.0
 */
public abstract class Phase<R,A> {

  /**
   * An MPCStream to receive output
   */
  protected MPCStream ps;

  /**
   * The MPCContext (to access compilation flags, etc.)
   */
  protected final MPCContext context;

  /**
   * Constructor; initializes the compilation context
   *
   * @param context the MPCContext to use
   */
  public Phase (MPCContext context) {
    this.context = context;
  }

  /**
   * Helper method: initializes ps
   *
   * @param ps the MPCStream to receive output
   * @param nullOk a boolean indicating whether a null pointer is ok; if it is
   * not, we put in a stream to the null device (i.e., something to which we
   * can send output, avoiding null poiinter or I/O exceptions, but with no
   * actual effect)
   */
  public void setStream (MPCStream ps, boolean nullOk) {
    if (ps == null && !nullOk)
      ps = MPCStream.getNull();
    this.ps = ps;
  }

  /**
   * Helper method:: optionally attempts to instantiate a suitable MPCStream
   * given a base name, suffix, and description
   *
   * @param doIt a boolean indicating whether to make a stream or not
   * @param baseName the base part of the file name to use for the output file
   * @param suffix the filename suffix to use
   * @param describe a short description of the kind of file, in case of a
   * problem
   * @param nullOk a boolean indicating whether a null stream pointer is ok
   */
  public void setStream (boolean doIt, String baseName, String suffix,
                         String describe, boolean nullOk) {
    setStream((doIt ? mpc.makeMPCStream(baseName, suffix, describe) : null),
              nullOk);
  }

  /**
   * Call to invoke the phase, i.e., to perform its actions, with an argument;
   * override this method to support it in a specific subclass.
   *
   * @param arg phase-specific argument (if any)
   * @return an Object giving a phase-specific result (if any)
   */
  public R perform (A arg) {
    throw new UnsupportedOperationException();
  }

  /**
   * Call to invoke the phase, i.e., to perform its actions, with no argument;
   * override this method to support it in a specific subclass.
   *
   * @return an Object giving a phase-specific result (if any)
   */
  public R perform () {
    throw new UnsupportedOperationException();
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

