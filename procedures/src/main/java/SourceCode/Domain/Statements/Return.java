package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.Type;

import java.io.IOException;

public class Return implements IStmt{
    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        state.getSymTblStack().pop();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new Return();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "return";
    }
}
