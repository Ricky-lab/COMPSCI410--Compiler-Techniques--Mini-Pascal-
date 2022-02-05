PROGRAM testfor;
VAR
   i : integer;
   m, n : 1 .. 10;
   x : integer;
BEGIN
   x := 0;
   FOR i := 1 TO 10 DO
      x := x + i;
   x := 0;
   FOR n := 1 TO 10 DO
      x := x + 1;
   FOR n := 1 TO m DO
      x := x + 1;
   FOR n := m DOWNTO m DO
      x := x - 1;
   FOR n := i TO i DO
      x := x + 2;
END.
