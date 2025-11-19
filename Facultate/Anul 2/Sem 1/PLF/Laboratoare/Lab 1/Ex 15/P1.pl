nrPar([]).
nrPar([_,_|T]):-
    nrPar(T).
