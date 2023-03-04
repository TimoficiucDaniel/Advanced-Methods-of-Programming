package SourceCode.Domain.Expression;

import SourceCode.Domain.ADT.IHeap;
import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.MyException;
import SourceCode.Domain.Type.BooleanType;
import SourceCode.Domain.Type.IntType;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.BooleanValue;
import SourceCode.Domain.Value.IntValue;
import SourceCode.Domain.Value.Value;

public class RelationalExp implements Exp{
    private Exp exp1;
    private Exp exp2;
    private String op;

    public RelationalExp(Exp exp1, Exp exp2, String op) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    @Override
    public String toString(){
        return exp1.toString()+op+exp2.toString();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTbl, IHeap<Integer,Value> heap) throws MyException {
        Value v1 = exp1.eval(symTbl,heap);
        Value v2 = exp2.eval(symTbl,heap);
        if(v1.getType().equals(new IntType()) && v2.getType().equals(new IntType()))
        {
            IntValue i1 = (IntValue) v1;
            IntValue i2 = (IntValue) v2;
            if (op.equals("<") && i1.getVal() < i2.getVal()) return new BooleanValue(true);
            else if (op.equals("<=") && i1.getVal() <= i2.getVal()) return new BooleanValue(true);
            else if (op.equals(">") && i1.getVal() > i2.getVal()) return new BooleanValue(true);
            else if (op.equals(">=") && i1.getVal() >= i2.getVal()) return new BooleanValue(true);
            else if (op.equals("!=") && i1.getVal() != i2.getVal()) return new BooleanValue(true);
            else if (op.equals("==") && i1.getVal() == i2.getVal()) return new BooleanValue(true);
        }
        else
            throw new MyException("Int values for relational expression are needed!\n");
        return new BooleanValue(false);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeTable) throws MyException {
        Type type1, type2;
        type1 = exp1.typecheck(typeTable);
        type2 = exp2.typecheck(typeTable);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType()))
                return new BooleanType();
            else
                throw new MyException(" The second operand is not an integer\n");
        }else
            throw new MyException(" The first operand is not an integer\n");
    }

    @Override
    public Exp deepCopy() {
        return null;
    }
}
