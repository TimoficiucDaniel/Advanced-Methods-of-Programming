package SourceCode.Domain.Expression;

import SourceCode.Domain.ADT.IHeap;
import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.MyException;
import SourceCode.Domain.Type.RefType;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.RefValue;
import SourceCode.Domain.Value.Value;

public class ReadHeap implements Exp{
    Exp exp;

    @Override
    public Value eval(MyIDictionary<String, Value> symTbl, IHeap<Integer,Value> heap) throws MyException {
    Value val = exp.eval(symTbl,heap);
    if(val instanceof RefValue)
    {
           RefValue ref = (RefValue) val;
           Value asoc = heap.getContent(ref.getAddress());
           if(asoc!=null)
               return asoc;
           else
               throw new MyException("Nothing at this address\n");
    }else
        throw new MyException("Value is not a reference value");
    }

    public ReadHeap(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String toString(){
        return "rH(" + exp.toString() + ")";
    }

    @Override
    public Exp deepCopy() {
        return null;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ=exp.typecheck(typeEnv);
        if(typ instanceof RefType){
            RefType reft = (RefType) typ;
            return reft.getInner();
        }
        else throw new MyException("the rh argument is not a ref type\n");
    }
}
