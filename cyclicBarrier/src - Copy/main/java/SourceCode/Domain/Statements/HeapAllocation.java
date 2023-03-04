package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.IHeap;
import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.Expression.Exp;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.IntType;
import SourceCode.Domain.Type.RefType;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.RefValue;
import SourceCode.Domain.Value.Value;

import java.io.IOException;

public class HeapAllocation implements IStmt{
    private String name;
    private Exp exp;

    public HeapAllocation(String name, Exp exp) {
        this.name = name;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        IHeap<Integer,Value> heap = state.getHeap();
        if(symTbl.isDefined(name)){
            Type var = symTbl.lookup(name).getType();
            if(var instanceof RefType)
            {
                Value val = exp.eval(symTbl,state.getHeap());
                if(val.getType().equals(((RefType)symTbl.lookup(name).getType()).getInner())){
                    RefValue ref = new RefValue(heap.getCurrentAddress(),val.getType());
                    heap.put(heap.getCurrentAddress(),val);
                    symTbl.update(name,ref);
                }
                else
                    throw new MyException("Location type does not have the same type as given value\n");
            }
            else
                throw new MyException("Must be a reference type\n");
        }
        else
            throw new MyException("Variable is not defined\n");
        return null;
    }

    @Override
    public String toString() {
        return "new(" + name + ", " + exp.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        //check deepcopy
        return new HeapAllocation(name,exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.getValue(name);
        Type typexp = exp.typecheck(typeEnv);
        if(!typevar.equals(new RefType(typexp)))
            throw new MyException("heap alloc: right hand side and left hand side have different types\n");
        return typeEnv;
    }
}
