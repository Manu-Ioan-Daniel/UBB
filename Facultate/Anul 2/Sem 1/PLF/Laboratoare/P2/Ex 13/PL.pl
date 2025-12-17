listaDivizori(E,E,[E]):-!.
listaDivizori(E,D,[D|RezT]):-
    E mod D =:= 0,
    D2 is D+1,
    listaDivizori(E,D2,RezT),!.
listaDivizori(E,D,Rez):-
    D2 is D+1,
    listaDivizori(E,D2,Rez).
append([],L,L):-!.
append([H|T],L,[H|RezT]):-
    append(T,L,RezT).
adaugaDiv([],[]).
adaugaDiv([H|T],[H|Rez]):-
    adaugaDiv(T,RezT),
    listaDivizori(H,1,L),
    append(L,RezT,Rez).
