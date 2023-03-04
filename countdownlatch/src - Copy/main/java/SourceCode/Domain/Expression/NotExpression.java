package SourceCode.Domain.Expression;

import SourceCode.Domain.ADT.IHeap;
import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.MyException;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.BooleanValue;
import SourceCode.Domain.Value.Value;

public class NotExpression implements Exp{
    private Exp exp;

    public NotExpression(Exp exp) {
        this.exp = exp;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTbl, IHeap<Integer, Value> heap) throws MyException {
        BooleanValue b = (BooleanValue) exp.eval(symTbl,heap);
        if(!b.getVal())
            return new BooleanValue(true);
        else
            return new BooleanValue(false);
    }

    @Override
    public Exp deepCopy() {
        return new NotExpression(exp.deepCopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return exp.typecheck(typeEnv);
    }

    @Override
    public String toString() {
        return "!"+exp.toString();
    }
}
