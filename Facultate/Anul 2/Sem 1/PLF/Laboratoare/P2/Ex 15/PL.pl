secv([],0,[],0,[]):-!.
secv([H|T],0,[],LungimeMax,ListaMax):-
    H mod 2 =:= 1,
    secv(T,_,_,LungimeMax,ListaMax),!.
secv([H|T],LungimeCur,[H|ListaCurT],LungimeMax,ListaMax):-
     secv(T,LungimeCurT,ListaCurT,LungimeMaxT,ListaMaxT),
     LungimeCur is LungimeCurT + 1,
    (LungimeCur>LungimeMaxT -> LungimeMax=LungimeCur,ListaMax=[H|ListaCurT];LungimeMax=LungimeMaxT,ListaMax=ListaMaxT).
secventa([],[]):-!.
secventa(L,Rez):-
    secv(L,_,_,_,Rez).

