program stmt;

var int:integer;
bol:boolean;
d,c:integer;
const r = 2;

begin

case (r) of
	1: ;
	2: bol := ( true <> false ) AND true;
	3: ;
end;

c:= 43;
d:= 1;
repeat 
int:= 234 *c +(c -6) div(d +5*c);
d:= d+1;
until  (d = 4);

end.
