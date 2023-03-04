package SourceCode.Domain.Expression;

import SourceCode.Domain.ADT.IHeap;
import SourceCode.Domain.ADT.MyDictionary;
import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.MyException;
import SourceCode.Domain.Type.IntType;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.IntValue;
import SourceCode.Domain.Value.Value;

public class ArithmeticExp implements Exp {

    private Exp left;
    private Exp right;
    private char operator;

    public ArithmeticExp(char otherOperator,Exp otherLeft, Exp otherRight) {
        left = otherLeft;
        right = otherRight;
        operator = otherOperator;
    }

    @Override
    public String toString() {
        return left.toString() + operator + right.toString();
    }

    @Override
    public Exp deepCopy() {
        return new ArithmeticExp(this.operator,this.left.deepCopy(),this.right.deepCopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1,typ2;
        typ1= left.typecheck(typeEnv);
        typ2= right.typecheck(typeEnv);
        if(typ1.equals(new IntType())){
            if(typ2.equals(new IntType())){
                return new IntType();
            }
            else
                throw new MyException("second operand is not an integer\n");
        }
        else throw new MyException("first operand is not an integer\n");
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTbl, IHeap<Integer,Value> heap) throws MyException {
        Value nr1 = left.eval(symTbl,heap);
        if (nr1.getType().equals(new IntType())) {
            Value nr2 = right.eval(symTbl,heap);
            if (nr2.getType().equals(new IntType())) {
                IntValue v1 = (IntValue) nr1;
                IntValue v2 = (IntValue) nr2;
                if (operator == '+')
                    return new IntValue(v1.getVal() + v2.getVal());
                else if (operator == '-')
                    return new IntValue(v1.getVal() - v2.getVal());
                else if (operator == '*')
                    return new IntValue(v1.getVal() * v2.getVal());
                else if (operator == '/') {
                    if (v2.getVal() == 0)
                        throw new MyException("Cannot divide by zero\n");
                    else
                        return new IntValue(v1.getVal() + v2.getVal());
                }
                else
                    throw new MyException("Invalid operator\n");
            } else throw new MyException("Operand2 is not int\n");
        } else throw new MyException("Operand1 is not int\n");
    }
}
