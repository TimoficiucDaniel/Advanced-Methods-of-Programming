package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.ILock;
import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.IntType;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.IntValue;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLock implements IStmt{


    private Lock lock = new ReentrantLock();
    private String str;

    public NewLock(String str) {
        this.str = str;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        lock.lock();
        ILock<Integer,Integer> locktable = state.getLocktable();
        int freeAddr = locktable.getCurrentAddress();
        locktable.put(freeAddr,-1);
        if(state.getSymTable().isDefined(str))
            state.getSymTable().update(str,new IntValue(freeAddr));
        else
            state.getSymTable().put(str,new IntValue(freeAddr));
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewLock(str);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.lookup(str).equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("var is not of type int\n");
    }

    @Override
    public String toString() {
        return "newLock("+str+")";
    }
}
