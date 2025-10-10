%sterge(L,E,Rez)
%model de flux (i,i,o)
%semnificatia param:L este lista de elemente
%E este elementul pe care vrem sa il stergem
%Rez este lista obtinuta prin stergerea tuturor aparitiilor lui E
sterge(L,E,Rez):-L=[],Rez=[].
sterge(L,E,Rez):-L=[H|T],H=E,sterge(T,E,Rez).
sterge(L,E,Rez):-L=[H|T],H\=E,sterge(T,E,Rez2),Rez=[H|Rez2].
