{ Various syntax errors surrounding objects }

PROGRAM testobject2;

TYPE
    Object1 = OBJECT;
    Object2 = OBJECT a: INTEGER; METHODS END;
    Object3 = OBJECT a: INTEGER; OVERRIDES myprint = obj3print END;
VAR
    o1: Object1;
    o2: Object2;
    o3: Object3;
BEGIN
    o1 := NEW(Object1);
    o2 := NEW(Object2);
    o3 := NEW(Object3);
END.
