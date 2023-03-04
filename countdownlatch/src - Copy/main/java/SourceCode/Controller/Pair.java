package SourceCode.Controller;

import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;

public class Pair {
    final PrgState first;
    final MyException second;

    Pair(PrgState _first, MyException _second) {
        first = _first;
        second = _second;
    }
}