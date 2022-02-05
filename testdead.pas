program test;

type
   tabs1 = abstract object x: integer; end;  (* vtable should be kept *)
   tconc11 = tabs1 object y: integer; end;   (* vtable should be kept *)
   tconc12 = tabs1 object z: integer; end;   (* vtable should not be kept *)
   tabs2 = abstract object b: boolean; end;  (* vtable should not be kept *)
var
   p1 : tabs1;

begin
   p1 := new(tconc11);
   writeln('Got here!');
   while true do
      writeln('Hello World');
   while false do
      writeln('Not more?');
   writeln('More?');
end.
