package SourceCode.Domain.Expression;

import SourceCode.Domain.ADT.IHeap;
import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.MyException;
import SourceCode.Domain.Type.BooleanType;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.BooleanValue;
import SourceCode.Domain.Value.Value;

public class LogicExp implements Exp{
    private Exp left;
    private Exp right;
    private String operator;

    public LogicExp(Exp left, Exp right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return left.toString()+operator+right.toString();
    }

    @Override
    public Exp deepCopy() {
        return new LogicExp(this.left.deepCopy(),this.right.deepCopy(),this.operator);
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTbl, IHeap<Integer,Value> heap) throws MyException {
        Value v1 = left.eval(symTbl,heap);
        if(v1.getType() instanceof BooleanType) {
            Value v2 = right.eval(symTbl,heap);
            if(v2.getType() instanceof BooleanType){
                BooleanValue b1 = (BooleanValue) v1;
                BooleanValue b2 = (BooleanValue) v2;
                if(operator=="&&")
                    return new BooleanValue(b1.getVal()&&b2.getVal());
                else if(operator=="||")
                    return new BooleanValue(b1.getVal()||b2.getVal());
                else throw new MyException("Invalid operator\n");
            }
            else throw new MyException("Operand2 is not a boolean\n");
        }
        else throw new MyException("Operand1 is not a boolean\n");
    }
    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = left.typecheck(typeEnv);
        type2 = right.typecheck(typeEnv);
        if (type1.equals(new BooleanType())) {
            if (type2.equals(new BooleanType()))
                return new BooleanType();
            else
                throw new MyException(" The second operand is not a bool\n");
        }else
            throw new MyException(" The first operand is not a bool\n");
    }
}
