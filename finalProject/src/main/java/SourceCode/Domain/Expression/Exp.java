package SourceCode.Domain.Expression;

import SourceCode.Domain.ADT.IHeap;
import SourceCode.Domain.ADT.MyDictionary;
import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.MyException;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.Value;

public interface Exp {
    Value eval(MyIDictionary<String,Value> symTbl, IHeap<Integer,Value> heap ) throws MyException;
    String toString();

    Exp deepCopy();

    Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException;
}
