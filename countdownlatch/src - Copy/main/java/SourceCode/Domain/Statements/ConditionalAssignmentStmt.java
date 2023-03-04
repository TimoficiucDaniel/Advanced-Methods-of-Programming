package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.Expression.Exp;
import SourceCode.Domain.Expression.VariableExp;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.BooleanType;
import SourceCode.Domain.Type.Type;

import java.io.IOException;

public class ConditionalAssignmentStmt implements IStmt{
    private String var;
    private Exp exp1;
    private Exp exp2;
    private Exp exp3;

    public ConditionalAssignmentStmt(String var, Exp exp1, Exp exp2, Exp exp3) {
        this.var = var;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        IStmt temp = new IfStmt(exp1,new AssignStmt(var,exp2),new AssignStmt(var,exp3));
        state.getStk().push(temp);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ConditionalAssignmentStmt(var,exp1.deepCopy(),exp2.deepCopy(),exp3.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type variableType = new VariableExp(var).typecheck(typeEnv);
        Type type1 = exp1.typecheck(typeEnv);
        Type type2 = exp2.typecheck(typeEnv);
        Type type3 = exp3.typecheck(typeEnv);
        if (type1.equals(new BooleanType()) && type2.equals(variableType) && type3.equals(variableType))
            return typeEnv;
        else
            throw new MyException("The conditional assignment is invalid!");
    }

    @Override
    public String toString() {
        return var+"=("+exp1+")?"+exp2+":"+exp3;
    }
}
