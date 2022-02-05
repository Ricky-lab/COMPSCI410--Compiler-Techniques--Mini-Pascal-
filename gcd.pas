PROGRAM gcd;

VAR x, y: INTEGER;

BEGIN
   READLN (x, y);
   WHILE x <> y DO BEGIN
      IF x > y THEN x := x - y ELSE y := y - x;
   END;
   WRITELN ('GCD = ', x);
END.
