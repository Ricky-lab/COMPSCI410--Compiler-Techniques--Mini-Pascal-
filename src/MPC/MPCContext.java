package MPC;
import java.util.*;

/**
 * This class maintains flags and similar context for the Mini-Pascal compiler
 *
 * @version 1.0
 */
public class MPCContext {

  /**
   * Base name for forming filenames
   */
  public String baseName;

  /**
   * Stream for verbose output
   */
  public final MPCStream verbStream;

  /**
   * Stream for error output
   */
  public final MPCStream errStream;

  /**
   * Input file name
   */
  public String inFileName;

  /**
   * The scanner instance for this compilation
   */
  public MPCScanner scanner;

  /**
   * The parser instance for this compilation
   */
  public Parser parser;

  /**
   * The Program built by the parser
   */
  public Program program;

  /**
   * The obvious constructor that initializes the two output streams
   *
   * @param verbStream stream for verbose output
   * @param errStream stream for error output
   */
  public MPCContext (final MPCStream verbStream, final MPCStream errStream) {
    this.verbStream = verbStream;
    this.errStream  = errStream;
  }

  /**
   * Maps flag letters to flags; also helps insure no duplicate letters
   */
  public static final Map<Character,Flag> letterToFlag = new HashMap<Character,Flag>(25);

  /**
   * Flags for this context object
   */
  public final EnumSet<Flag> flags = EnumSet.copyOf(DefaultFlags);

  /**
   * Accessor method to see if a particular flag is set
   *
   * @param flag the Flag to test
   * @return a boolean, true iff that flag is set
   */
  public boolean flag (final Flag flag) {
    return flags.contains(flag);
  }

  /**
   * Default flags
   */
  public static final EnumSet<Flag> DefaultFlags = EnumSet.of(
    Flag.ParsePrint
    );

  /**
   * Enumeration for the command flags
   */
  public static enum Flag {
    Verbose   ('v', "verbose output",
               "to request verbose output"),
    TokenPrint('t', "token output",
               "to control output of tokens to a .token file"),
    ParsePrint('p', "parse tree output",
               "to control printing of AST to a .parse file"),
    Debug('d', "debugging output",
          "for debugging output") {
      void toggle (EnumSet<Flag> flags) { flags.add(this); }
    },
    ParserDebug('P', "parser debugging output",
                "for parser debugging output") {
      void toggle (EnumSet<Flag> flags) { flags.add(this); Parser.debug = true; }
    };

    /**
     * Flag letter on the command line that sets/toggles this flag
     */
    public final Character letter;

    /**
     * Short description of flag for verbose output
     */
    public final String desc;

    /**
     * Command line help string for the flag
     */
    public final String help;

    /**
     * Constructor that sets the flag's command line letter
     *
     * @param letter the Character on the command line that will set/toggle
     * this flag
     * @param desc a String describing the flag for verbose output
     * @param help a help String describing the flag
     */
    Flag (final Character letter, final String desc, final String help) {
      this.letter = letter;
      this.desc   = desc;
      this.help   = help;
      assert !letterToFlag.containsKey(letter);
      letterToFlag.put(letter, this);
    }

    void toggle (final EnumSet<Flag> flags) {
      if (flags.contains(this)) {
        flags.remove(this);
      } else {
        flags.add(this);
      }
    }

  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

