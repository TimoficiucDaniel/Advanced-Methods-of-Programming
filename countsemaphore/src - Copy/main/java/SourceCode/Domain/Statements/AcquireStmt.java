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

public class AcquireStmt implements IStmt{
    private String var;
    private Lock lock = new ReentrantLock();

    public AcquireStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        lock.lock();
        if(state.getSymTable().isDefined(var))
        {
            if(state.getSymTable().lookup(var).getType().equals(new IntType()))
            {
                IntValue fi = (IntValue) state.getSymTable().lookup(var);
                int found = fi.getVal();
                if(state.getSemTable().containsKey(found)){
                    Pair<Integer, List<Integer>> foundSem = state.getSemTable().getContent(found);
                    int nl = foundSem.getSecond().size();
                    int n1 = foundSem.getFirst();
                    if(n1>nl)
                    {
                        if(!foundSem.getSecond().contains(state.getId_thread())){
                            foundSem.getSecond().add(state.getId_thread());
                            state.getSemTable().update(found,new Pair<>(n1,foundSem.getSecond()));
                        }
                    }else state.getStk().push(this);
                } else throw new MyException("index is not a key in the sem table\n");
            } else throw new MyException("var not of intType\n");
        }else throw new MyException("var not in sym table\n ");
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AcquireStmt(var);
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
        return "aqcuire("+var+")";
    }
}
