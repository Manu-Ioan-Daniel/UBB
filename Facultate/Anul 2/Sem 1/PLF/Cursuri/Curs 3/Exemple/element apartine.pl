%member(e:element,L:list)
%(i,i)-determinist,(o,i)-nedeterminist
member1(E,[E|_]).
member1(E,[_|T]):-member1(E,T).
go1:-member1(1,[1,2,1,3,1,4]).

