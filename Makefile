# these are useful in Make functions below
empty :=
space := $(empty) $(empty)

# PHASE is the compiler phase (10, 15, 20, 25, etc.)
PHASE := $(subst p,,$(notdir $(shell pwd)))

# JCLASSP gives the classpath to use with java and javac
# We invoke a shell script to compute it since its syntax varies
# according to the operating system on which we are running, etc.
JCLASSP = -classpath $(shell bash jclassp.bash . java_cup.jar)

# set up paths for accessing java, javac, and javadoc

# Given JAVA_VERSION X_Y_Z, tries environment variables
# JAVA_HOME_X_Y_Z, JAVA_HOME_X_Y, and JAVA_HOME, and
# similarly for JDK_HOME (below). This allows for fairly
# easy change to different versions and for the presence
# of multiple versions on the same machine.

-include ../Makefile.ver
# Makefile.var can override the two variables below
JAVA_VERSION ?= 1.5.0
JAVA_SOURCE_VERSION ?= 1.5

JV1 := _$(subst .,_,$(JAVA_VERSION))
JV2 := _$(subst $(space),_,$(wordlist 1,2,$(subst .,$(space),$(JAVA_VERSION))))
JVER := $(JV1)
JAVAHOME = $(JAVA_HOME$(JVER))
ifeq ($(JAVAHOME),)
  JVER := $(JV2)
endif
ifeq ($(JAVAHOME),)
  JVER :=
endif
ifeq ($(JAVAHOME),)
  JAVA = java
else
  JAVA = $(JAVAHOME)/bin/java
endif

JDKHOME = $(JDK_HOME$(JVER))
JVER := $(JV1)
ifeq ($(JDKHOME),)
  JVER := $(JV2)
endif
ifeq ($(JDKHOME),)
  JVER :=
endif
ifeq ($(JDKHOME),)
  JAVAC   = javac
  JAVADOC = javadoc
else
  JAVAC   = $(JDKHOME)/bin/javac
  JAVADOC = $(JDKHOME)/bin/javadoc
endif

# The "mess" below is to deal with paths that have spaces
# in them (these must be quoted with a backslash). This
# is common on cygwin ("Program Files/Java/...")
JAVA    := $(subst $(space),\$(space),$(JAVA))
JAVAC   := $(subst $(space),\$(space),$(JAVAC))
JAVADOC := $(subst $(space),\$(space),$(JAVADOC))

# defaults for the various Java command flags
JFLAGBASE = -ea
JFLAGS = $(JFLAGBASE) $(JCLASSP) $(XJFLAGS)
JSOURCE = -source $(JAVA_SOURCE_VERSION)
JCFLAGS = -g $(JSOURCE) -d . $(JCLASSP) $(XJCFLAGS)
JDFLAGS = $(JSOURCE) $(JCLASSP)

# extensions of output files built by this phase
OUTEXTS := token
RPTEXTS := $(OUTEXTS)

# insures that make does not delete outputs
.PRECIOUS: %.err %.out $(addprefix %.,$(OUTEXTS)) \
           $(addprefix %.,$(RPTEXTS)) $(addprefix regression/%.,$(RPTEXTS))

# defines source files for this phase
javas =     mpc.java Phase.java MPCContext.java Parse.java \
            MPCScanner.java ScanInStream.java IntMap.java StringMap.java \
            Token.java TokenId.java TokenInt.java TokenKey.java \
              TokenOp.java TokenString.java \
            MPCStream.java InputPos.java \
            ASTNode.java Program.java \
# must have this comment line here, to terminate above definition

srcs = $(addprefix MPC/,$(javas))

cupgens = $(addprefix MPC/,Parser.java sym.java)

cupclass = $(subst java,class,$(cupgens))

# defines Pascal test files for this phase
testfiles = gcd.pas testbranch.pas testfor.pas testmin.pas testmisc2.pas testprocdecl.pas \
            testread.pas \
            scancomment.pas scaneof1.pas scaneof2.pas scangood.pas scanmin.pas \
# must have this comment line here, to terminate above definition

# defines the spim command to use (xspim for the X window version,
# just spim for the text interface version)
spim = xspim


# Rules for the various targets we can build, though the default
# target/outputs vary according to phase

%.token: %.pas mpcr
	-mpcr $<


# default target is the mpc driver file, a trivial script
# building it is useful in that it includes the right
# flags to java, etc.
mpcr: MPC/mpc.class $(cupclass) jclassp.bash
	echo "$(JAVA) $(JFLAGS) MPC.mpc \$$*" > mpcr
	chmod 775 mpcr

# writes file for running the reference compiler
ref:
	echo "$(JAVA) $(JFLAGBASE) -classpath $(shell bash jclassp.bash ref.jar java_cup.jar) MPC/mpc \$$*" > ref
	chmod 775 ref

# Triggers Java compilation
# We list Parser.java and sym.java explicitly since java_cup builds them
MPC/mpc.class $(cupclass): $(srcs) $(cupgens) \
# Note: javac rebuilds all .class files automatically
	$(JAVAC) $(JCFLAGS) $(srcs) $(cupgens)

# Rule for building Parser and sym Java source using java_cup
$(cupgens): mpc.cup java_cup.jar build.xml
	$(JAVA) $(JCLASSP) java_cup.Main -package MPC -parser Parser -expect 0 mpc.cup
	mv -f Parser.java sym.java MPC

# builds javadoc documentation
doc: docdone

docdone: $(srcs) $(cupgens)
	$(JAVADOC) $(JDFLAGS) -sourcepath MPC \
        -private                              \
        -exclude java_cup.runtime             \
        -overview overview.html               \
        -d ./doc                              \
        -use                                  \
        -splitIndex                           \
        -windowtitle '610 Pascal Compiler'    \
        -doctitle '610 Pascal Compiler'       \
        -header '610 Pascal Compiler'         \
        -bottom '610 Pascal Compiler'	      \
        $(srcs) $(cupgens)
	touch docdone

docclean:
	rm -f docdone *.html doc/*.html

# for testing
paths:
	@echo "JAVA_VERSION        = $(JAVA_VERSION)"
	@echo "JAVA_SOURCE_VERSION = $(JAVA_SOURCE_VERSION)"
	@echo "JV1     = $(JV1)"
	@echo "JV2     = $(JV2)"
	@echo "JVER    = $(JVER)"
	@echo "JAVA    = $(JAVA)"
	@echo "JAVAC   = $(JAVAC)"
	@echo "JAVADOC = $(JAVADOC)"

# cleans out everything, including source
veryclean:  oclean docclean
	echo -n "REALLY delete all .java and .cup source files!!? ['yes' to confirm] " ; \
	read ask ; \
        if [ "x$$ask" = xyes ]; then rm -f MPC/*.java *.cup *.html doc/*.html; fi

# forces recompilation and rerunning of java_cup
oclean:     clean
	rm -f MPC/*.class $(cupgens)

# cleans up output from running mpc
clean:
	rm -f reports *.out *.err *.report *.token \
# must have this comment line here, to terminate above definition

# variable defining output filenames
outfiles = $(subst pas,out,$(testfiles))

# rule for running mpc to save standard output and standard error
# useful for regression testing
%.err %.out $(addprefix %.,$(OUTEXTS)): %.pas mpcr
	-mpcr $< 1> $*.out 2> $*.err

# fill in things if they fail to be created otherwise
$(addprefix %.,$(RPTEXTS)) $(addprefix regression/%.,$(RPTEXTS)):
	@for ff in $(addprefix $(*F).,$(RPTEXTS)) $(addprefix regression/$(*F).,$(RPTEXTS)) ; do \
	  [ -r $$ff ] || { echo "Creating $$ff" ; touch $$ff ; } ; \
	done

# rule to run all tests
tests: $(outfiles)

# variable for all reports
reportfiles = $(subst pas,report,$(testfiles))

# rule for generating a regression test report for a test program
$(reportfiles): %.report: %.out %.err $(addprefix %.,$(RPTEXTS)) \
                          $(addprefix regression/%.,$(RPTEXTS))
	@echo -n "" > $@
	@-for n in $(foreach fn,out err $(RPTEXTS),$*.$(fn)) ; do \
	  [ -r $$n ] || { \
	    echo "File $$n does not exist" ; \
	    continue; \
	  } ; \
	  [ -r regression/$$n ] || { \
	    echo "File regression/$$n does not exist" ; \
	    continue; \
	  } ; \
	  diff -q -w $$n regression/$$n > /dev/null || { \
	    echo "Adding diffs for $$n" ; \
	    echo "==========" >> $@ ; \
	    echo "$$n:" >> $@ ; \
	    diff -w $$n regression/$$n >> $@ ; \
	    echo "----------" >> $@ ; \
	  } ; \
	done

# rule to get all reports
reports: $(reportfiles)
	cat $(reportfiles) > reports.txt

# puts compiled mpc .class files together
ref.jar: MPC/mpc.class
	jar cvf ref.jar MPC/*.class

# allows for additional definitions across all phases
-include ../Makefile.gen
