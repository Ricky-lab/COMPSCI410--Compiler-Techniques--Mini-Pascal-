package MPC;
import java.io.*;
import static MPC.MPCContext.*;
import static MPC.MPCContext.Flag.*;

/**
 * mpc stands for <b>M</b>ini-<b>P</b>ascal <b>C</b>compiler.
 *
 * It is the driver class that parses command line arguments and
 * initializes and invokes the compiler phases.
 * @author Eliot Moss, Copyright 2003-2004
 */
public class mpc {

  /**
   * Flag value for "not yet assigned"
   */
  public static final int NOT_YET_ASSIGNED = -1;

  /**
   * Stream to receive error output; generally System.err.
   */
  public static MPCStream errStream = new MPCStream(new PrintStream(System.err));

  /**
   * Stream to receive verbose output; 
   * generally the same as errStream, which is typically System.err.
   */
  public static MPCStream verbStream = errStream;

  /**
   * The Parser instantiated for this input file.
   */
  public static Parser parser;

  /**
   * Compilation context for this run; generally should not access it as a
   * static, but it should be passed to the phase, etc.
   */
  public static final MPCContext context = new MPCContext(verbStream, errStream);

  /**
   * Convenient short-hand for testing flags
   *
   * @param flag to test
   * @return a boolean indicating whether the flag is set
   */
  public static boolean flag (final Flag flag) {
    return context.flag(flag);
  }

  /**
   * Extracts the root (basename) part of a filename
   *
   * @param base the String from which to extract the root part
   * @return the root part String
   */
  private static String getRootPart (final String base) {
    final int idxDot  = base.lastIndexOf('.');
    final int idxDir  = base.lastIndexOf(File.separatorChar);  // namely '/' under Unix, etc.
    int rootLen = base.length();
    if (idxDir < idxDot) {
      // if there is a dot, and no slash after it, then
      //    the part up to the dot is the root
      // otherwise, the whole name is the root (as in the initialization above)
      rootLen = idxDot;
    }
    return base.substring(0, rootLen);
  }

  /**
   * Constructs a filename given an original name and a new extension.
   * An <i>extension</i> is the part after the dot in a filename; thus
   * pas is the extension in the name "foo.pas".
   * @param base  The original filename String; may or may not have an extension
   * @param ext   The desired new extension String
   * @return      The String giving a filename formed by replacing (or adding)
   *              the new extension to the base filename
   */
  private static String makeFilename (final String base, final String ext) {
    return getRootPart(base) + "." + ext;
  }

  /**
   * Tries to open the file that makeFilename builds; prints error message and
   * returns null if it fails.
   *
   * @param base The original filename String
   * @param ext  The extension for the new file
   * @param which Short description of which output file (for error messages)
   * @return An open MPCStream for output to the file, or null if there is an error
   */
  public static MPCStream makeMPCStream (final String base, final String ext, final String which) {
    final String name = makeFilename(base, ext);
    try {
      return new MPCStream(name);
    } catch (Throwable e) {
      errStream.printf("Could not open %s output file %s%n", which, name);
      e.printStackTrace(errStream);
      return null;
    }
  }

  /**
   * Goes through flag letters, adjusting debug and output controls as appropriate.
   *
   * @param flags Command line argument String containing flags,
   * including leading '-'.
   */
  private static void processCommandLineFlags (final String flags) {
    // start at 1, to skip the leading '-'
    for (int j = 1, n = flags.length(); j < n; ++j) {
      final char c = flags.charAt(j);
      if (letterToFlag.containsKey(c)) {
        letterToFlag.get(c).toggle(context.flags);
      } else {
        errStream.printf("Unrecognized flag %c ignored%n", c);
      }
    }
  }

  /**
   * Prints a "usage" message, describing how to invoke mpc and the available
   * command line arguments and flags.
   * @param ps Where to print the "usage" message.
   */
  private static void printUsage (final PrintStream ps) {
    ps.print("Usage: mpc [-");
    for (final Flag flag : Flag.values()) {
      ps.print(flag.letter);
    }
    ps.println("] infile");
    ps.println("  infile may omit a trailing .p or .pas");
    for (final Flag flag : Flag.values()) {
      ps.printf("  -%c %s%n", flag.letter, flag.help);
    }
  }

  /**
   * Displays (to an output) the current mpc flag settings.
   * @param ps Where to display the flag settings.
   */
  private static void showFlags (final PrintStream ps) {
    ps.println("Flag settings:");
    for (final Flag flag : Flag.values()) {
      ps.printf("  %-28s = %s%n", flag.desc,
                (flag(flag) ? "on" : "off"));
    }
  }

  /**
   * Processes command line argument that gives the input filename. Tries name
   * as is, then with extension p and extension
   * pas. Terminates processing if it cannot find a file and open
   * it successfully.
   * @param inName Initial name for file to try to open.
   * @return An opened input stream to the Pascal input file.
   */
  private static InputStream openInputStream (final String inName) {
    // try name as is, and then with extension "p" and "pas"
    File inFile = new File(inName);
    if (!inFile.canRead()) {
      final String inName2 = makeFilename(inName, "p");
      inFile = new File(inName2);
      if (!inFile.canRead()) {
        final String inName3 = makeFilename(inName, "pas");
        inFile = new File(inName3);
        if (!inFile.canRead()) {
          errStream.printf("Could not open %s, %s, or %s!%n",
                           inName, inName2, inName3);
          System.exit(1);
          // should not get here
          return null;
        }
      }
    }
    context.inFileName = inFile.getName();

    // Now that we have the name, try to open it as a FileInputStream
    InputStream inStream = null;
    try {
      inStream = new FileInputStream(inFile);
    } catch (Throwable e) {
      errStream.printf("Could not open input file %s!%n", inFile);
      e.printStackTrace(errStream);
      System.exit(1);
      // should not get here
    }
    return inStream;
  }

  /**
   * This is the "main" method of the Mini-Pascal Compiler. If invoked with an
   * empty command line, it will offer help describing its command line format
   * and available flags.
   * @param args The command line arguments String[]
   */
  public static void main (final String args[]) {
    // Step 1: go through initial command line arguments and set flags, etc.
    int argNum;

    // Step 1a: go through any initial command line arguments that start
    // with a hyphen, to process flags
    for (argNum = 0;
         argNum < args.length && args[argNum].startsWith("-");
         ++argNum) {
      processCommandLineFlags(args[argNum]);
    }

    // Step 1b: ignore any empty args (convenience for build.xml)
    while (argNum < args.length && args[argNum].length() == 0) {
      ++argNum;
    }

    // Step 1c:
    // if there is no input file to process, give "usage" message
    if (argNum >= args.length) {
      printUsage(errStream);
      System.exit(1);
      return; // should not get here ...
    }

    // Step 1c: show flag settings, if requested
    if (flag(Verbose)) {
      showFlags(verbStream);
    }

    // Step 2: process command line arguments that follow any initial ones
    // that start with a hyphen: these result in the setting up of input
    // and output filenames and streams

    // Step 2a: Open the input file
    final String inName = args[argNum++];
    final InputStream inStream = openInputStream(inName);

    if (argNum < args.length) {
      errStream.println("Extra command line argument(s) ignored.");
    }
    final String baseName = context.baseName = getRootPart(inName);

    // Step 3: parsing

    try {
      // quit if there was an error
      if ((new Parse(context)).perform(inStream)) {
        System.exit(1);
      }

    } catch (SemanticException e) {
      if (flag(Debug)) {
        e.printStackTrace(errStream);
      }
      mpc.ShowError(e.pos, e.getMessage());
      return;

    } catch (Exception e) {
      errStream.println("Exception occurred:");
      e.printStackTrace(errStream);
      errStream.println("Aborting ...");
      System.exit(20);
      // cannot get here
      return;

    } catch (Error e) {
      errStream.println("Error occurred:");
      e.printStackTrace(errStream);
      errStream.println("Aborting ...");
      System.exit(21);
      // cannot get here
      return;
    }

    // Done
    if (flag(Verbose)) verbStream.println("Done.");
    System.exit(0);
    // should not get here
    return;
  }

  /**
   * Prints an error message given also a line number in the input file.
   * @param pos The input position of the start of the error; first line is 1
   * @see InputPos
   * @param message The error message to print
   */
  public static void ShowError (final int pos, final String message) {
    errStream.printf("line %d: %s%n", InputPos.lineOf(pos), message);
    errStream.flush();
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

