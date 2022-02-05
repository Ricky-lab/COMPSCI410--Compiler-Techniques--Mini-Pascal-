package MPC;
import java.util.*;
import java.io.*;
import static MPC.MPCContext.Flag.*;

/**
 * This class drives the semantic checking of the AST.
 *
 * @version 1.0
 */
public class Parse extends Phase<Boolean,InputStream> {

  /**
   * Obvious constructor
   *
   * @param context the compilation context
   */
  public Parse (MPCContext context) {
    super(context);
  }

  /**
   * perform runs the phase
   *
   * @param inStream an InputStream for the file to parse
   * @return a boolean indicating whether there was an error
   */
  public Boolean perform (InputStream inStream) {
    setStream(context.flag(TokenPrint), context.baseName, "token", "token", true);
    if (context.flag(Verbose)) {
      context.verbStream.printf("Parsing input file \"%s\".%n", context.inFileName);
      if (ps != null) {
        context.verbStream.printf("Writing tokens to file \"%s\".%n", ps.name);
      }
    }

    // create the Scanner for the parser
    context.scanner = new MPCScanner(inStream, ps, context.errStream);

    // create the Parser
    context.parser = new Parser(context.scanner, context.errStream);

    // parse!
    try {
      context.program = (Program)(context.parser.parse().value);
    } catch (Exception e) {
      context.errStream.println("Exception occurred:");
      e.printStackTrace(context.errStream);
      context.errStream.println("Aborting ...");
      context.parser.error = true;
    }

    if (context.parser.error) return true;
    return false;
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

