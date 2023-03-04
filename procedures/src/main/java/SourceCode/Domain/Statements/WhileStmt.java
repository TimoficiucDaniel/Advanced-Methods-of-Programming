package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.Expression.Exp;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.BooleanType;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.BooleanValue;
import SourceCode.Domain.Value.Value;

import java.io.IOException;

public class WhileStmt implements IStmt{
    private Exp exp;
    private IStmt stmt;

    public WhileStmt(Exp exp, IStmt stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        Value val = exp.eval(state.getSymTable(),state.getHeap());
        if(val.getType() instanceof BooleanType)
        {
            BooleanValue bval = (BooleanValue) val;
            if(bval.getVal())
            {
                state.getStk().push(this);
                state.getStk().push(stmt);
            }
        }
        else
            throw new MyException("While expression is not boolean\n");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(exp.deepCopy(),stmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ = exp.typecheck(typeEnv);
        if(typ.equals(new BooleanType()))
        {
            stmt.typecheck(typeEnv.deepcopy());
            return typeEnv;
        }
        else
            throw new MyException("the condition of while is not of boolean type\n");
    }

    @Override
    public String toString() {
        return "while( " + exp.toString() + "){" + stmt.toString() + "}";
    }
}
