package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.MyDictionary;
import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.ADT.MyIStack;
import SourceCode.Domain.ADT.MyStack;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.Value;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

public class forkStmt implements IStmt{

    private IStmt stmt;

    public forkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        MyIStack<MyIDictionary<String, Value>> copy = new MyStack<>(new Stack<>());
        Iterator<MyIDictionary<String, Value>> i = state.getStackSymAsList().getIter();
        while (i.hasNext())
        {
            copy.push(i.next().deepcopy());
        }
        PrgState newPrg = new PrgState(copy,new MyStack<>(new Stack<>())
                ,state.getOut(),this.stmt,state.getFileTable(),state.getHeap(),state.getLastThreadId()+10,state.getProctable());
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
