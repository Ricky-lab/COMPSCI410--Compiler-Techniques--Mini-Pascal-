a { first let's test comments }
b { See if it takes comments over multiple
  lines; This is one such comment
  that should be accepted, including characters such as @ and $ }
c { check the various forms *) d (* another variant }
e (* and another *) f
{ some more challenging ones *** ))) } g { ** *) h { ***} i {*} j (*****) k

{ Checking the reserved identifiers here }
INTEGER BOOLEAN TRUE FALSE string

{ keywords }
AND ARRAY BEGIN CASE CONST DO DOWNTO ELSE END FOR
FUNCTION IF NEW NIL OF PROCEDURE PROGRAM READ READLN 
RECORD REPEAT THEN TO TYPE UNTIL VAR WHILE WRITE WRITELN

{ identifiers other than the reserved one's are checked here }
student minksy coins610 MAXSTUDENTS MaX
max_students a_b_c A_b_C
if read repeat
Mygrade classaverage

OfcourseThereShouldBeAVeryLongIdentifierSinceTheseshouldBeLegalidentifierssoletsSeeIfyoucanscanThistogetyourfullscorerepeatOfcourseThereShouldBeAVeryLongIdentifierSinceTheseshouldBeLegalidentifierssoletsSeeIfyoucanscanThistogetyourfullscorerepeatOhwellthatsenough

{ numbers }
1 999 00010
123456789
10#99 2#1001 8#077 16#beef 36#zyx
2#1000_0001 2_000_000 1_2_3_4

{ strings }
'Hello, COINS 610'
'Special characters here : (:-)=<===<!!!!;<=>?@[]  ^_`{|}~!#$%&  ()*+,-./:"'
''' umatched ''quote'' '
'test'' nested'' string'

{ Arithmetic, logical ops  and others}
+-*.:==<><<=>>=,;:..()[]^

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
  a := 3000 + b;
END.
