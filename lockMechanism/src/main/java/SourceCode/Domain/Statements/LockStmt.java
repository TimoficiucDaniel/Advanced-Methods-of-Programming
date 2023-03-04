package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.IntType;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.IntValue;
import SourceCode.Domain.Value.Value;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockStmt implements IStmt{

    private Lock lock = new ReentrantLock();
    private String str;

    public LockStmt(String str) {
        this.str = str;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        lock.lock();
        if(state.getSymTable().isDefined(str))
            if(state.getSymTable().lookup(str).getType().equals(new IntType()))
            {
                IntValue i = (IntValue) state.getSymTable().getValue(str);
                int found = i.getVal();
                if(state.getLocktable().contains(found)){
                    if(state.getLocktable().getContent(found) == -1)
                        state.getLocktable().update(found,state.getId_thread());
                    else
                        state.getStk().push(this);
                }
                else
                    throw new MyException("Index not in locktable\n");
            }else throw new MyException("Var not of int type\n");
        else throw new MyException("Var not defined\n");
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new LockStmt(str);
    }

    @Override
    public String toString() {
        return "lock("+str+")";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.lookup(str).equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("Var is not of int type\n");
    }
}
