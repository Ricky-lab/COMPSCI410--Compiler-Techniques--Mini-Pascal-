{ first let's test comments }
{ See if it takes comments over multiple
  lines; This is one such comment
  that should be accepted }


{ Checking the reserved identifiers here }
INTEGER BOOLEAN TRUE FALSE string

{ keywords }
AND ARRAY BEGIN CASE CONST DO DOWNTO ELSE END FOR
FUNCTION IF NEW NIL OF PROCEDURE PROGRAM READ READLN 
RECORD REPEAT THEN TO TYPE UNTIL VAR WHILE WRITE WRITELN

{ identifiers other than the reserved one's are checked here }
student minksy coins610 MAXSTUDENTS MaX
if read repeat
Mygrade classaverage

OfcourseThereShouldBeAVeryLongIdentifierSinceTheseshouldBeLegalidentifierssoletsSeeIfyoucanscanThistogetyourfullscorerepeatOfcourseThereShouldBeAVeryLongIdentifierSinceTheseshouldBeLegalidentifierssoletsSeeIfyoucanscanThistogetyourfullscorerepeatOhwellthatsenough

{ numbers }
1 999 00010
123456789

{ string }

{ Arithmetic, logical ops  and others}
.:==<><<=>>=,;:..()

{ whitespace test: before the identifiers and numbers are unprintable
 characters }
	x
w
 1

{ a simple pascal subset program here! }

program scantest;
{ simple variable declaration }
VAR a,b : INTEGER;
BEGIN
  a := 3000 AND b;
END.
