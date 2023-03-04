package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.IntType;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.IntValue;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class await implements IStmt{
    private String str;
    private Lock lock = new ReentrantLock();

    public await(String str) {
        this.str = str;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        lock.lock();
        if(state.getSymTable().isDefined(str)){
            IntValue in = (IntValue) state.getSymTable().lookup(str);
            int found = in.getVal();
            if(state.getLatch().containtsKey(found)){
                if(state.getLatch().getContent(found)!= 0)
                    state.getStk().push(this);}
            else
                throw new MyException("index not found in latch table await\n");}
        else
            throw new MyException("variable not defined await\n");

        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new await(str);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookup(str).equals(new IntType()))
            return typeEnv;
        else
            throw new MyException(String.format("%s is not of int type!\n", str));
    }

    @Override
    public String toString() {
        return "await("+str+")";
    }
}
