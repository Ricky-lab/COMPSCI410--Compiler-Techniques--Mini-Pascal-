PROGRAM TestProcDecl;
TYPE
   t1 = integer;
PROCEDURE p1 (a: a); BEGIN END;
PROCEDURE p2 (t1: t1); BEGIN END;
PROCEDURE p3 (x: y); BEGIN END;
PROCEDURE p4 (x: t2); BEGIN END;
FUNCTION f1 (a: a): integer; BEGIN END;
FUNCTION f2 (t1: t1): integer; BEGIN END;
FUNCTION f3 (x: y): integer; BEGIN END;
FUNCTION f4 (x: t2): integer; BEGIN END;
FUNCTION f5 (x: integer): x; BEGIN END;
FUNCTION f6 (x: integer): y; BEGIN END;
FUNCTION f7 (x: integer): t1; BEGIN END;
FUNCTION f8 (t1: integer): t1; BEGIN END;
FUNCTION f9 (x: integer): t2; BEGIN END;
TYPE
   t2 = boolean;
BEGIN
END.
