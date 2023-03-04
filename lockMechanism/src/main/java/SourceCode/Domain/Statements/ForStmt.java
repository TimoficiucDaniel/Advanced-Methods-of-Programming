package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.Expression.Exp;
import SourceCode.Domain.Expression.RelationalExp;
import SourceCode.Domain.Expression.VariableExp;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.IntType;
import SourceCode.Domain.Type.Type;

import java.io.IOException;

public class ForStmt implements IStmt{
    private String str;
    private Exp exp1;
    private Exp exp2;
    private Exp exp3;
    private IStmt stmt;

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        IStmt ex = new CompStmt(new AssignStmt(str,exp1),new CompStmt(new WhileStmt(new RelationalExp(new VariableExp(str),exp2,"<"),
                new CompStmt(stmt,new AssignStmt("v",exp3))),new NopStmt()));
        state.getStk().push(ex);
        return null;
    }

    public ForStmt(Exp exp1, Exp exp2, Exp exp3,String str,IStmt stmt) {
        this.str = str;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.stmt = stmt.deepCopy();
    }

    @Override
    public IStmt deepCopy() {
        return new ForStmt(exp1.deepCopy(),exp2.deepCopy(),exp3.deepCopy(),str,stmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1 = exp1.typecheck(typeEnv);
        Type typ2 = exp2.typecheck(typeEnv);
        Type typ3 = exp3.typecheck(typeEnv);

        if(typ1.equals(new IntType()) && typ2.equals(new IntType()) && typ3.equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("invalid\n");
    }

    @Override
    public String toString() {
        return "for("+str+"="+exp1.toString()+";"+str+"<"+exp2.toString()+";"+str+"="+exp3.toString()+")"+stmt.toString();
    }
}
