program test;

const n=true;

var b:boolean;
    w1,w2,w3 : boolean;
begin b:=false; 
      while b=n do
          begin
             w1:=w1 AND true;
             w2:=w2 OR  FALSE;
             w3:=NOT (w3 or w2 and w1);
          end;
end.
