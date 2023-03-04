package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.IntType;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.IntValue;
import com.example.a7.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class await implements IStmt{

    private String var;

    public await(String var) {
        this.var = var;
    }

    private Lock lock  = new ReentrantLock();

    @Override
    public String toString() {
        return "await(" + var + ')';
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        lock.lock();
        if(state.getSymTable().isDefined(var))
        {
            IntValue i = (IntValue) state.getSymTable().lookup(var);
            int found = i.getVal();
            if(state.getBarrier().containsKey(found))
            {
                Pair<Integer, List<Integer>> foundBarr = state.getBarrier().getContent(found);
                int nl= foundBarr.getSecond().size();
                int n1= foundBarr.getFirst();
                ArrayList<Integer> list = (ArrayList<Integer>) foundBarr.getSecond();
                if(n1>nl)
                    if(list.contains(state.getId_thread()))
                        state.getStk().push(this);
                    else {
                        list.add(state.getId_thread());
                        state.getBarrier().update(found,new Pair<>(n1,list));
                    }
            }
            else throw new MyException("index not in barrier table\n");
        }
        else throw new MyException("var not defined awaitstmt\n");
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new await(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.lookup(var).equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("var is not of type int await\n");
    }
}
