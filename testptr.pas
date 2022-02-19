PROGRAM testptr;
TYPE
  r1 = RECORD x, y: INTEGER; END;
  p1 = ^r1;
  p2 = ^r2;
  r2 = RECORD this: INTEGER; rest: p2; END;
VAR
  p: p1;
  q, qq: p2;
  i: INTEGER;
BEGIN
  NEW (p);
  p^.x := 1;
  p^.y := 2;
  WRITELN (p^.x, p^.y);
  q := NIL;
  FOR i := 10 DOWNTO 1 DO BEGIN
    NEW (qq);
    qq^.this := i;
    qq^.rest := q;
    q := qq;
  END;
  qq := q;
  WHILE qq <> NIL DO BEGIN
    WRITELN (qq^.this);
    qq := qq^.rest;
  END;
END.
