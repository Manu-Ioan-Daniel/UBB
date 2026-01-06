### 1.Perechi

``` prolog
% pair_with(+H, +List, -Result)
pair_with(_, [], []).
pair_with(H, [X|T], [[H, X] | R]) :-
    pair_with(H, T, R).

% pair_list(+List, -Result)
pair_list([], []).
pair_list([H|T], R) :-
    pair_with(H, T, R1),
    pair_list(T, R2),
    append_manual(R1, R2, R).

% append_manual(+A, +B, -R)
append_manual([], B, B).
append_manual([H|T], B, [H|R]) :-
    append_manual(T, B, R).
```

### 2.Numararea aparitiilor

``` prolog
% count(+Elem, +List, -Count)
count(_, [], 0).
count(E, [E|T], R) :-
    count(E, T, R1),
    R is R1 + 1.
count(E, [_|T], R) :-
    count(E, T, R).

% remove_all(+Elem, +List, -Result)
remove_all(_, [], []).
remove_all(E, [E|T], R) :-
    remove_all(E, T, R).
remove_all(E, [X|T], [X|R]) :-
    X \= E,
    remove_all(E, T, R).

% count_occurrences(+List, -Result)
count_occurrences([], []).
count_occurrences([H|T], [[H, C] | R]) :-
    count(H, [H|T], C),
    remove_all(H, T, T2),
    count_occurrences(T2, R).
```

### 3.Combinari

```prolog
% combinations_aux(+K, +List, -OneCombination)
combinations_aux(0, _, []).
combinations_aux(K, [H|T], [H|C]) :-
    K > 0,
    K1 is K - 1,
    combinations_aux(K1, T, C).
combinations_aux(K, [_|T], C) :-
    K > 0,
    combinations_aux(K, T, C).

% combinations(+K, +List, -Result)
combinations(K, L, R) :-
    findall(C, combinations_aux(K, L, C), R).
```

### 4.Permutari

```prolog
% pick(+Elem, +List, -Rest)
pick(H, [H|T], T).
pick(E, [H|T], [H|Rest]) :-
    pick(E, T, Rest).

% permutation_aux(+List, -OnePermutation)
permutation_aux([], []).
permutation_aux(L, [H|P]) :-
    pick(H, L, Rest),
    permutation_aux(Rest, P).

% permutations(+List, -Result)
permutations(L, R) :-
    findall(P, permutation_aux(L, P), R).
```

### 5.Subliste/ Subseturi

``` prolog
% subset_aux(+List, -OneSubset)
subset_aux([], []).
subset_aux([H|T], [H|S]) :-
    subset_aux(T, S).
subset_aux([_|T], S) :-
    subset_aux(T, S).

% subset_list(+List, -Result)
subset_list(L, R) :-
    findall(S, subset_aux(L, S), R).
```

### 6.Aranjamente

```prolog
% arrangement_aux(+K, +List, -OneArrangement)
arrangement_aux(0, _, []).
arrangement_aux(K, L, [H|A]) :-
    K > 0,
    pick(H, L, Rest),
    K1 is K - 1,
    arrangement_aux(K1, Rest, A).

% arrangements(+K, +List, -Result)
arrangements(K, L, R) :-
    findall(A, arrangement_aux(K, L, A), R).
```

