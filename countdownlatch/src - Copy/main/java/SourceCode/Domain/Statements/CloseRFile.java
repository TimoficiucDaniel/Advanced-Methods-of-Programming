package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.Expression.Exp;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.StringType;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.StringValue;
import SourceCode.Domain.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt{
    private Exp exp;

    public CloseRFile(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        Value val = exp.eval(state.getSymTable(),state.getHeap());
        if(val.getType().equals(new StringType()))
        {
            StringValue s = (StringValue) val;
            BufferedReader buff = state.getFileTable().lookup(s);
            buff.close();
            state.getFileTable().deleteEntry(s);
        }else throw new MyException("Not a string\n");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFile(exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(!exp.typecheck(typeEnv).equals(new StringType()))
            throw new MyException("CloseRfile requires string type\n");
        return typeEnv;
    }

    @Override
    public String toString(){
        return "close (" + exp +") ";
    }
}
