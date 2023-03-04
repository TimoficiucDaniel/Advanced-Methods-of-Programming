package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.Expression.Exp;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.BooleanType;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.BooleanValue;
import SourceCode.Domain.Value.Value;

public class IfStmt implements IStmt{
    private Exp exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(Exp exp, IStmt thenS, IStmt elseS) {
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value val = exp.eval(state.getSymTable(),state.getHeap());
        if(val.getType() instanceof BooleanType)
        {
            BooleanValue b = (BooleanValue) val;
            if(b.getVal())
                state.getStk().push(thenS);
            else
                state.getStk().push(elseS);
        }
        else throw new MyException("Conditional expression is not a boolean\n");
        return null;
    }

    @Override
    public String toString(){
        return "(IF("+exp.toString()+")THEN("+ thenS.toString()+")ELSE("+elseS.toString()+"))";
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(this.exp.deepCopy(),thenS.deepCopy(),elseS.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new BooleanType())) {
            thenS.typecheck(typeEnv.deepcopy());
            elseS.typecheck(typeEnv.deepcopy());
            return typeEnv;
       }
        else
            throw new MyException("The condition of IF has not the type bool");
    }
    }

