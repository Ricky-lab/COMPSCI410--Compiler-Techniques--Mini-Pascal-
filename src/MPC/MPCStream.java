package MPC;
import java.io.*;

/**
 * Adds indentation support to a PrintStream; helpful for nicely formatted AST
 * output, etc.
 */
public class MPCStream extends PrintStream {

  /**
   * The name of the file associated with this MPCStream, if any.
   */
  public final String name;

  /**
   * Handy for directing output to nowhere
   */
  public static final MPCStream Null = getNull();

  public static MPCStream getNull () {
    try {
      try {
        return new MPCStream("/dev/null");
      } catch (Exception e) {
        return new MPCStream("nul:");
      }
    } catch (Exception e) {
      System.err.println("Exception occurred:");
      e.printStackTrace(System.err);
      System.err.println("Aborting ...");
      System.exit(19);
      // cannot get here
    }
    return null;  // make compiler happy
  }

  /**
   * builds an MPCStream given an underlying OutputStream
   *
   * @param os the OutputStream to receive our output
   */
  public MPCStream (OutputStream os) {
    this(os, "");
  }

  /**
   * builds an MPCStream given an underlying OutputStream
   *
   * @param os the OutputStream to receive our output
   * @param nm the filename to remember for this MPCStream
   */
  public MPCStream (OutputStream os, String nm) {
    super(os);
    name = nm;
  }

  /**
   * as a convenience, will build an MPCStream given a filename
   *
   * @param nm the name of the file to open
   * @throws FileNotFoundException when nm cannot be opened for output
   */
  public MPCStream (String nm) throws FileNotFoundException {
    this(new PrintStream(new FileOutputStream(nm)), nm);
  }

  /**
   * indentation, express as number of columns of spaces to print when
   * requested to do so
   */
  private int indent = 0;

  /**
   * the amount, in columns, by which to increase/decrease indentation when we
   * change by one indentation step
   */
  public static final int indentStep = 2;

  /**
   * for starting to print an indented/nested construct; outputs an open
   * parenthesis, then the given String, then increases indentation by one
   * step.
   *
   * @param s String to output as we start a new construct
   */
  public void printStart (String s) {
    this.printf("(%s", s);
    indent += indentStep;
  }

  /**
   * concludes the current output line and prints spaces to indent the next
   * line
   */
  public void printIndent () {
    this.println();
    for (int n = indent; n > 0; --n) {
      this.print(" ");
    }
  }

  /**
   * finish a construct by print a close parenthesis and decreasing
   * indentation by one step.
   */
  public void printFinish () {
    this.print(")");
    indent -= indentStep;
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

