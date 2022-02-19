PROGRAM testrecord;
TYPE
  Rec = RECORD a: integer; b: integer; END;
  PRec = ^Rec;
  Rec2 = RECORD x: integer; r: Rec; END;
VAR
  i: integer;
  r: Rec;
  p: PRec;
  r2: Rec2;

PROCEDURE foo (VAR x: integer);
BEGIN
END;

PROCEDURE bar (r: Rec; VAR rr: Rec);
BEGIN
END;

BEGIN
  new (p);
  r.a := i;
  r.b := i;
  i := r.a;
  i := r.b;
  p^.a := i;
  p^.b := i;
  i := p^.a;
  i := p^.b;
  foo (i);
  foo (r.a);
  foo (r.b);
  foo (p^.a);
  foo (p^.b);
  bar (r, r);
  bar (r2.r, r2.r);
  bar (p^, p^);
END.
