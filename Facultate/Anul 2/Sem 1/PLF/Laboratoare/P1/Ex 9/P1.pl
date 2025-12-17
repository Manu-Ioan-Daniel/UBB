apartine([H|_],H):-!.
apartine([_|T],E):-
    apartine(T,E).
intersectie([],_,[]):-!.
intersectie([H|T],B,[H|RezT]):-
   apartine(B,H),
   intersectie(T,B,RezT),!.
intersectie([_|T],B,Rez):-
    intersectie(T,B,Rez).
listaInterval(M,N,[M|RezT]):-
    M=<N,
    M2 is M+1,
    listaInterval(M2,N,RezT),!.
listaInterval(_,_,[]).
