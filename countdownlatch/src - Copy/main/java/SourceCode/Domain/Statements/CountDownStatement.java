package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.Expression.ValueExp;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.IntType;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.IntValue;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CountDownStatement implements IStmt{
    private String var;
    private Lock lock  = new ReentrantLock();

    public CountDownStatement(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        lock.lock();
        if(state.getSymTable().isDefined(var)) {
            IntValue fi = (IntValue) state.getSymTable().lookup(var);
            int found = fi.getVal();
            if (state.getLatch().containtsKey(found)) {
                if (state.getLatch().getContent(found) > 0) {
                    state.getLatch().update(found, state.getLatch().getContent(found) - 1);}
                    state.getStk().push(new PrintStmt(new ValueExp(new IntValue(state.getId_thread()))));

            }
            else throw new MyException("index not found in the latch table\n");
        }
        else throw new MyException("variable not defined");
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CountDownStatement(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType()))
            return typeEnv;
        else
            throw new MyException(String.format("%s is not of int type!", var));
    }

    @Override
    public String toString() {
        return "CountDown("+var+")";
    }
}
