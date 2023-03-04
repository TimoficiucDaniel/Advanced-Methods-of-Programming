package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.ADT.MyStack;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.Type;

import java.io.IOException;
import java.util.Stack;

public class forkStmt implements IStmt{

    private IStmt stmt;

    public forkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        PrgState newPrg = new PrgState(state.getSymTable().deepcopy(),new MyStack<>(new Stack<>())
                ,state.getOut(),this.stmt,state.getFileTable(),state.getHeap(),state.getLastThreadId()+10);
        state.setLastThreadId(state.getLastThreadId()+10);
        return newPrg;
    }

    @Override
    public IStmt deepCopy() {
        return new forkStmt(stmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        stmt.typecheck(typeEnv.deepcopy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork ("  + stmt +
                ')';
    }
}
