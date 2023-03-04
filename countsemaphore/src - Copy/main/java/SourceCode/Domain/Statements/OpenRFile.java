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
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements IStmt{
    private Exp exp;

    public OpenRFile(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, FileNotFoundException {
        Value val = exp.eval(state.getSymTable(),state.getHeap());
        if(!val.getType().equals(new StringType()))
            throw new MyException("Not string type\n");
        StringValue s = (StringValue) val;
        if(!state.getFileTable().verify(s))
            throw new MyException("File already added\n");
        BufferedReader buff = new BufferedReader(new FileReader(s.toString()));
        state.getFileTable().put(s,buff);
        return null;
    }

    @Override
    public IStmt deepCopy(){
        return new OpenRFile(exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(exp.typecheck(typeEnv).equals(new StringType()))
            return typeEnv;
        throw new MyException("openrfile requires string exp\n");
    }

    @Override
    public String toString(){
        return "open (" + exp +") ";
    }
}
