package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.Value;

public class VarDecStmt implements IStmt{
    private String name;
    Type typ;

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        if(symTbl.isDefined(name))
            throw new MyException("Variable already declared\n");
        else
            symTbl.put(name,typ.defaultValue());
        return null;
    }

    @Override
    public String toString(){
        return typ.toString()+" "+name;
    }

    public VarDecStmt(String name, Type typ) {
        this.name = name;
        this.typ = typ;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDecStmt(this.name,this.typ);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.put(name,typ);
        return typeEnv;
    }


}
