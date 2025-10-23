perechiElement([],_,[]).
perechiElement([H|T],E,Rez):-
    perechiElement(T,Rez2),
    Rez=[[E,H]|Rez2].
perechi([],[]).
perechi([H|T],Rez):-

