intercalare([],_,_,[]).
intercalare([H|T],E,N,[H|RezT]):-
    N>1,
    N2 is N-1,
    intercalare(T,E,N2,RezT),!.
intercalare([H|T],E,1,[E,H|RezT]):-
    intercalare(T,E,0,RezT),!.
intercalare([H|T],E,0,[H|RezT]):-
    intercalare(T,E,0,RezT).
cmmdc(A,0,A):-!.
cmmdc(A,B,Rez):-
    R is A mod B,
    cmmdc(B,R,Rez).
