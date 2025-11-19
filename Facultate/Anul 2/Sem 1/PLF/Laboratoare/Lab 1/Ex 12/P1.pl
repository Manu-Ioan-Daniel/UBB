substituie([],_,_,[]).
substituie([H|T],H,E,[E|RezT]):-
    substituie(T,-1,E,RezT),!.
substituie([H|T],EE,E,[H|RezT]):-
    substituie(T,EE,E,RezT).
%punctul b e usor
