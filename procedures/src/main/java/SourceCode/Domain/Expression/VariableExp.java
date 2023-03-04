package SourceCode.Domain.Expression;

import SourceCode.Domain.ADT.IHeap;
import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.MyException;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.Value;

public class VariableExp implements Exp{
    private String id;

    public VariableExp(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public Exp deepCopy() {
        return new VariableExp(this.id);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.lookup(id);
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTbl, IHeap<Integer,Value> heap) throws MyException {
        return symTbl.lookup(id);
    }
}
