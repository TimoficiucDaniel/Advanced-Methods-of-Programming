package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.Expression.Exp;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.IntValue;
import com.example.a7.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewBarrier implements IStmt{
    private String var;
    private Exp exp;
    private Lock lock = new ReentrantLock();

    @Override
    public String toString() {
        return "newBarrier(" + var + ',' + exp + ')';
    }

    public NewBarrier(String var, Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        lock.lock();
        if(exp.eval(state.getSymTable(), state.getHeap()) instanceof IntValue) {
            IntValue in = (IntValue) exp.eval(state.getSymTable(), state.getHeap());
            int nr = in.getVal();
            int free = state.getBarrier().getCurrentAddress();
            state.getBarrier().IncAddr();
            state.getBarrier().put(free,new Pair<>(nr,new ArrayList<>()));
            if(state.getSymTable().isDefined(var))
                state.getSymTable().update(var,new IntValue(free));
            else
                throw new MyException("var not in sym table newbarrier error\n");
        }
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewBarrier(var,exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return null;
    }
}
