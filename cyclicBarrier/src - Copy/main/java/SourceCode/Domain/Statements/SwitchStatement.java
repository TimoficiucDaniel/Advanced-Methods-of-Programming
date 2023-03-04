package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.ADT.MyIStack;
import SourceCode.Domain.Expression.Exp;
import SourceCode.Domain.Expression.RelationalExp;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.Type;

import java.io.IOException;

public class SwitchStatement implements IStmt{
    private Exp mainexp;
    private Exp exp1;
    private IStmt stmt1;
    private Exp exp2;
    private IStmt stmt2;
    private IStmt defaultstmt;

    public SwitchStatement(Exp mainexp, Exp exp1, IStmt stmt1, Exp exp2, IStmt stmt2, IStmt defaultstmt) {
        this.mainexp = mainexp;
        this.exp1 = exp1;
        this.stmt1 = stmt1;
        this.exp2 = exp2;
        this.stmt2 = stmt2;
        this.defaultstmt = defaultstmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        IStmt converted = new IfStmt(new RelationalExp( mainexp, exp1,"=="),
                stmt1, new IfStmt(new RelationalExp( mainexp, exp2,"=="), stmt2, defaultstmt));
        state.getStk().push(converted);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new SwitchStatement(mainexp.deepCopy(),exp1.deepCopy(),stmt1.deepCopy(),exp2.deepCopy(),stmt2.deepCopy(),defaultstmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return null;
    }

    @Override
    public String toString() {
        return String.format("switch(%s){(case(%s): %s)(case(%s): %s)(default: %s)}", mainexp, exp1, stmt1, exp2, stmt2, defaultstmt);
    }
}
