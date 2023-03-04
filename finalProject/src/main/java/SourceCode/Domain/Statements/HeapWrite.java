package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.MyDictionary;
import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.Expression.Exp;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.RefType;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.RefValue;
import SourceCode.Domain.Value.Value;

import java.io.IOException;

public class HeapWrite implements IStmt{
    private Exp exp;
    private String varName;

    public HeapWrite(Exp exp,String varName) {
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public String toString() {
        return "wH(" + exp.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        MyIDictionary<String , Value> symTbl = state.getSymTable();
        if(symTbl.isDefined(varName))
        {
            Value val = symTbl.getValue(varName);
            if(val.getType() instanceof RefType)
            {
                RefValue ref = (RefValue) val;
                if(state.getHeap().getContent(ref.getAddress())!=null)
                {
                    Value expVal = exp.eval(symTbl,state.getHeap());
                    if(expVal.getType().equals(ref.getLocationType()))
                    {
                        state.getHeap().update(ref.getAddress(),expVal);
                    }
                    else
                        throw new MyException("Expression val not the same type as location\n");
                }
                else
                    throw new MyException("Not a key in the heap\n");
            }
            else
                throw new MyException("Not of reference type\n");
        }
        else
            throw new MyException("Not defined in symtbl\n");
        return null;
    }


    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.getValue(varName).equals(new RefType(exp.typecheck(typeEnv))))
            return typeEnv;
        throw new MyException("heap write: rhs and lhs have different types\n");
    }
}
