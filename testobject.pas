{ The "good" test case }

PROGRAM testobject;

TYPE
    AnyObject = ABSTRACT OBJECT
                METHODS
                    PROCEDURE print (self: AnyObject);
                END;

    Month = 1 .. 12;
    DayOfMonth = 1 .. 31;

    Date = AnyObject OBJECT
            m: Month;
            d: DayOfMonth;
            y: INTEGER;
        METHODS
            FUNCTION dayOfYear (self: Date): INTEGER = dateDayOfYear;
            PROCEDURE increment (self: Date) = dateIncrement;
            FUNCTION equalsDate (self, other: Date): BOOLEAN = dateEqualsDate;
        OVERRIDES
            print = datePrint;
    END;
VAR
    d : Date;
    a : AnyObject;
    r : ROOT;

{ Note that because OBJECTs are exclusively heap-allocated, a Date is
    really a Date^, and the . operator implicitly dereferences (somewhat
    like Java...) }

PROCEDURE datePrint (self: Date);
    BEGIN
    writeln('Entered datePrint');
    write(self.m);
    write('-');
    write(self.d);
    write('-');
    write(self.y);
END;

FUNCTION mkDate (m: Month; d: DayOfMonth; y: INTEGER): Date;
    VAR
        ret: Date;
    BEGIN
    writeln('Entered mkDate');
    
    ret := NEW(Date);
    ret.m := m;
    ret.d := d;
    ret.y := y;

    mkDate := ret;
END;

FUNCTION isLeapYear (y: INTEGER): BOOLEAN;
    VAR
        remainder: INTEGER;
        ret: BOOLEAN;
    BEGIN
    writeln('Entered isLeapYear');

    remainder := y MOD 4;
    IF remainder <> 0 THEN
        ret := false
    ELSE BEGIN
        remainder := y MOD 400;
        IF 0 = remainder THEN
            ret := true
        ELSE BEGIN
            remainder := y MOD 100;
            IF 0 = remainder THEN
                ret := false
            ELSE
                ret := true
        END
    END;

    isLeapYear := ret
END;

FUNCTION daysInMonth (m: Month; y: INTEGER): DayOfMonth;
    VAR
        ret: INTEGER;
    BEGIN
    writeln('Entered daysInMonth');

    CASE m OF
        { Thirty days hath September... }
        9, 4, 6, 11:
            ret := 30;
        2:
            IF isLeapYear(y) THEN
                ret := 29
            ELSE
                ret := 28;
        1, 3, 5, 7, 8, 10, 12:
            ret := 31
    END;
    daysInMonth := ret
END;

FUNCTION dateDayOfYear (self: Date): INTEGER;
    VAR
        m, ret: INTEGER;
    BEGIN
    writeln('Entered dateDayOfYear');

    m := self.m - 1;
    ret := self.d;

    FOR m := m DOWNTO 1 DO
        ret := ret + daysInMonth(self.m, self.y);

    dateDayOfYear := ret
END;

PROCEDURE dateIncrement (self: Date);
    VAR
        dim: DayOfMonth;
    BEGIN
    writeln('Entered dateIncrement');

    dim := daysInMonth(self.m, self.y);
    IF self.d = dim THEN BEGIN
        self.d := 1;
        IF self.m = 12 THEN BEGIN
            { New Years Eve... }
            self.y := self.y + 1;
            self.m := 1
            END
        ELSE
            self.m := self.m + 1;
        END
    ELSE
        self.d := self.d + 1
END;

FUNCTION dateEqualsDate (self, other: Date): BOOLEAN;
    VAR
        ret: BOOLEAN;
    BEGIN
    writeln('Entered dateEqualsDate');
    
    IF self.y = other.y THEN
        IF self.m = other.m THEN
            IF self.d = other.d THEN
                ret := true
            ELSE
                ret := false
        ELSE
            ret := false
    ELSE
        ret := false;
    dateEqualsDate := ret
END;

BEGIN
    d := mkDate(2, 9, 1985);
    d.increment;
    d.y := d.y + 10;
    d.print;
    writeln;
    writeln(d.dayOfYear);
    writeln(d.equalsDate(mkDate(2, 10, 1995)));
    a := d;
    d := NARROW(a, Date);
    write('ISTYPE ', 1);
    IF ISTYPE(d  , Root     ) THEN writeln(' ok') ELSE writeln(' bad');
    write('ISTYPE ', 2);
    IF ISTYPE(d  , Date     ) THEN writeln(' ok') ELSE writeln(' bad');
    write('ISTYPE ', 3);
    IF ISTYPE(d  , AnyObject) THEN writeln(' ok') ELSE writeln(' bad');


    write('ISTYPE ', 4);
    IF ISTYPE(NIL, ROOT     ) THEN writeln(' ok') ELSE writeln(' bad');
    write('ISTYPE ', 5);
    IF ISTYPE(NIL, Date     ) THEN writeln(' ok') ELSE writeln(' bad');
    write('ISTYPE ', 6);
    IF ISTYPE(NIL, AnyObject) THEN writeln(' ok') ELSE writeln(' bad');

    a := NARROW(NIL, AnyObject);
    writeln('should get here: NARROW(NIL, AnyObject)');
    a := NARROW(NIL, Date);
    writeln('should get here: NARROW(NIL, Date)');
    r := NEW(ROOT);

    write('ISTYPE ', 7);
    IF ISTYPE(r, ROOT     ) THEN writeln(' ok')  ELSE writeln(' bad');
    write('ISTYPE ', 8);
    IF ISTYPE(r, Date     ) THEN writeln(' bad') ELSE writeln(' ok');
    write('ISTYPE ', 9);
    IF ISTYPE(r, AnyObject) THEN writeln(' bad') ELSE writeln(' ok');
    writeln('got here, now should fail at NARROW(r, Date)');
    d := NARROW(r, Date);
    writeln('should not get here');
END.
