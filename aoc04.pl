file_lines(File, Lines) :-
    setup_call_cleanup(open(File, read, In), stream_lines(In, Lines), close(In)).

stream_lines(In, Lines) :-
    read_string(In, _, Str),
    split_string(Str, "\n", " ", Lines).

removeCardNum(Line, NewLine) :-
    split_string(Line, ":", " ", S),
    nth0(1, S, NewLine).

splitCards(Card, NewCard) :-
    split_string(Card, "|", " ", NewCard).

splitValsHelper(Vals, NewVals) :-
    split_string(Vals, " ", " ", S),
    maplist(atom_number, S, NewVals).

splitVals(Vals, NewVals) :-
    maplist(splitValsHelper, Vals, NewVals).

getOverlaps(Card, Overlap) :-
    nth0(0, Card, Winning),
    nth0(1, Card, Mine),
    intersection(Winning, Mine, Overlap).

pow2(N, NewN) :-
    NewN is floor(2^(N-1)).

processInput(File, Out) :-
    file_lines(File, L),
    maplist(removeCardNum, L, L2),
    maplist(splitCards, L2, L3),
    maplist(splitVals, L3, L4),
    maplist(getOverlaps, L4, L5),
    maplist(length, L5, Out).

addCounters(Val, [Val, 1]).

filterCounters([_, C], C).

addN(N, [Val, C], [Val, C2]) :-
    C2 is C+N.

replaceAt(I, L, Val, NewL) :-
    nth0(I, L, _, Rest),        
    nth0(I, NewL, Val, Rest). 


loop(_, 0, L, L).
loop(OrigI, I, L, LL) :-
    nth0(OrigI, L, [_, C]),
    CurrI is OrigI + I,
    addTo(C, CurrI, L, L2),
    NextI is I - 1,
    loop(OrigI, NextI, L2, LL), !.

addTo(C, I, L, LL) :-
    nth0(I, L, Val),
    addN(C, Val, X2),
    replaceAt(I, L, X2, LL).

doTheThing(I, L, LL) :-
    nth0(I, L, [Val, _]),
    loop(I, Val, L, LL).

forEach([], L, L).
forEach([I|II], L, LL) :-
    doTheThing(I, L, L2),
    forEach(II, L2, LL), !.

first(A) :-
    processInput("aoc04.txt", L),
    maplist(pow2, L, L2),
    sum_list(L2, A).

second(A) :-
    processInput("aoc04.txt", L),
    maplist(addCounters, L, L2),
    length(L2, Len),
    MaxI is Len - 1,
    numlist(0, MaxI, Indices),
    forEach(Indices, L2, L3),
    maplist(filterCounters, L3, L4),
    sum_list(L4, A).