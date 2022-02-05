package MPC;
public class SemanticException extends Error {

  // We make this a subclass of Error so that we do not need to put
  // it in throws clauses everywhere. Most routines should not try
  // to catch it -- we catch it in specific places. Its purpose
  // is to indicate a semantic error in the program being compiled,
  // hence it normally includes a source position.

  /**
   * Source position (-1 if none) of the semantic problem
   */
  public int pos;

  /**
   * 
   */
  public SemanticException () {
    super();
  }

  /**
   * Constructs a SemanticException with "unknown" position (-1);
   * simply calls the two argument constructor with that default value.
   *
   * @param message the error message to return and print for the user
   */
  public SemanticException (String message) {
    this(-1, message);
  }

  /**
   * Constructs a SemanticException with given source input position
   * (@see InputPos) and error message string.
   *
   * @param posn    source position of the input error (@see InputPos)
   * @param message the error message to return and print for the user
   */
  public SemanticException (int posn, String message) {
    super(message);
    pos = posn;
  }

  /**
   * Constructs a SemanticException with a given error message and
   * original triggering exception (Throwable) object; this constructor
   * gives no source position (i.e., -1).
   *
   * @param message the error message to return and print for the user
   * @param cause   the original triggering exception (Throwable)
   */
  public SemanticException (String message, Throwable cause) {
    super(message, cause);
    pos = -1;
  }

  /**
   * Constructs a SemanticException with a given triggering exception
   * (Throwable) object, but no error message or source position.
   *
   * @param cause The original triggering exception (Throwable) object
   */
  public SemanticException (Throwable cause) {
    super(cause);
    pos = -1;
  }
}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:
