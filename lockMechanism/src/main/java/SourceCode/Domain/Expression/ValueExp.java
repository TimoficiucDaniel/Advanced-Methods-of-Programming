package SourceCode.Domain.Expression;

import SourceCode.Domain.ADT.IHeap;
import SourceCode.Domain.ADT.MyDictionary;
import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.MyException;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.Value;

public class ValueExp implements Exp{

    private Value v;
    public ValueExp(Value val){
        v=val;
    }

    @Override
    public String toString(){
        return v.toString();
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(this.v.deepCopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
       return v.getType();
    }


    @Override
    public Value eval(MyIDictionary<String, Value> symTbl, IHeap<Integer,Value> heap) throws MyException {
        return v;
    }

}
