package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.IntType;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.IntValue;
import com.example.a7.Pair;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Release implements IStmt{
    private String var;
    private Lock lock = new ReentrantLock();

    public Release(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        lock.lock();
        if(state.getSymTable().isDefined(var))
        {
            if(state.getSymTable().lookup(var).getType().equals( new IntType())) {
                IntValue fi = (IntValue) state.getSymTable().lookup(var);
                int found = fi.getVal();
                if (state.getSemTable().containsKey(found))
                {
                    Pair<Integer, List<Integer>> pair = state.getSemTable().getContent(found);
                    if(pair.getSecond().contains(state.getId_thread()))
                        pair.getSecond().remove((Integer) state.getId_thread());
                    state.getSemTable().update(found,new Pair<>(pair.getFirst(),pair.getSecond()));
                }else throw new MyException("not in semaphore table release\n");
            }else throw new MyException("var not of int type release\n");
        } else throw new MyException("not in symtable release\n");
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new Release(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType())) {
            return typeEnv;
        } else {
            throw new MyException(String.format("%s is not int!", var));
        }
    }

    @Override
    public String toString() {
        return "release("+var+")";
    }
}
