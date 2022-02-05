{ Object test cases which parse but do not successfully check }

PROGRAM testobject3;

TYPE
    Object1 = INTEGER OBJECT METHODS PROCEDURE add (self: Object1; x: INTEGER); END;
    Object2 = Object3 OBJECT OVERRIDES method = object2Method END;
    Object3 = OBJECT
                x: INTEGER
              METHODS
                PROCEDURE method (self: Object3) = object3Method;
              END;
    Object4 = OBJECT
                x: INTEGER;
              METHODS
                FUNCTION multiply (self: Object4; x: INTEGER): Object4;
              END;
    Object5 = Object4 OBJECT
              OVERRIDES
                multiply = object5Multiply
              END;
    Object6 = Object4 ABSTRACT OBJECT
              OVERRIDES
                multiply = object6Multiply
              END;
    Object7 = Object6 OBJECT
              METHODS
                FUNCTION add (self: Object7; VAR x: INTEGER): Object7 = object7Add;
              END;
VAR
    o1: Object1;
    o2: Object2;
    o3: Object3;
    o4: Object4;
    o5: Object5;
    o6: Object6;
    o7: Object7;

PROCEDURE object2Method (self: Object2);
    BEGIN
    writeln(self.x);
END;

FUNCTION object3Method (x: INTEGER): INTEGER;
    BEGIN
       object3Method := 2 * x;
    END;

FUNCTION object5Multiply (self: Object5; x:INTEGER): INTEGER;
    BEGIN
       object5Multiply := self.x * x;
    END;

FUNCTION object6Multiply (self: Object7; x: INTEGER): Object6;
    VAR
        obj: Object6;
    BEGIN
    obj := NEW(Object7);
    obj.x := self.x * x;
    object6Multiply := obj;
END;

FUNCTION object7Add (self: Object7; x: INTEGER): Object7;
    VAR
        obj: Object7;
    BEGIN
    obj := NEW(Object7);
    obj.x := self.x + x;
    object7Add := obj
END;
BEGIN
    o4 := NEW(Object4);   (* ok *)
    writeln(o4^.x);       (* cannot ^ an object reference *)
    o6 := NEW(Object6);   (* cannot NEW an ABSTRACT type *)
    NEW(o6);              (* cannot NEW an ABSTRACT type *)
    o6 := o4;             (* illegal downcast *)
    o4 := o6;             (* ok (upcast) *)
    o4 := o4;             (* ok (same type) *)
    IF ISTYPE(o4) THEN writeln;              (* bad: only one arg *)
    IF ISTYPE(o4, Object4, 3) THEN writeln;  (* bad: more than two args *)
    IF ISTYPE(o4, INTEGER) THEN writeln;     (* bad: not an OBJECT type *)
    IF ISTYPE(o4, Object4) THEN writeln;     (* ok *)
    IF ISTYPE(35, Object4) THEN writeln;     (* bad: arg not of OBJECT type *)
    IF ISTYPE(NIL, Object4) THEN writeln;    (* ok *)
    o4 := NARROW(o4);                        (* bad: only one arg *)
    o4 := NARROW(o4, Object4, 3);            (* bad: more than two args *)
    o4 := NARROW(o4, INTEGER);               (* bad: not an OBJECT type *)
    o4 := NARROW(o4, Object4);               (* ok *)
    o4 := NARROW(35, Object4);               (* bad: arg not of OBJECT type *)
    o4 := NARROW(NIL, Object4);              (* ok *)
    o4 := NIL;                               (* ok *)
END.
