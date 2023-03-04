package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.Type;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException, IOException;
    String toString();

    IStmt deepCopy();

    MyIDictionary<String,Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
