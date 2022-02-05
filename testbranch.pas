PROGRAM testbranch;

PROCEDURE reached;
VAR
   x, y: integer;
BEGIN
   x := 1;
   y := 2 - x;
   IF x <> y THEN BEGIN
      unreached;
      WRITE ('bar');
   END;
   WRITELN ('foo');
END; { reached }

PROCEDURE unreached;
BEGIN
END; { unreached }

PROCEDURE branchingForms;
VAR
   x, y, z, a, b, c : integer;
   bb               : boolean;
BEGIN
   IF (x < y) AND (y < z) THEN bb := true;
   WHILE (a <> b) OR (z = 0) DO x := x + 1;
   REPEAT z := z + 2; UNTIL NOT ((a = b) OR (y <> z) OR bb);
END;

VAR
   z: integer;

BEGIN
   z := 0;
   reached;
   IF z <> 0
      THEN IF z <> 1 THEN z := 2 ELSE z := 3
      ELSE IF z <> 4 THEN z := 5 ELSE z := 6;
   WHILE FALSE DO
      z := z + 1;
   z := z + 100;
END.
