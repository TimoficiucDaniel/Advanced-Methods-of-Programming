package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.Type;

import java.io.IOException;

public class Sleep implements IStmt{
    private int number;

    public Sleep(int number) {
        this.number = number;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        if(number>0)
            state.getStk().push(new Sleep(number-1));
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return null;
    }

    @Override
    public String toString() {
        return "sleep("+number +
                ')';
    }
}
