package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.Expression.Exp;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.IntType;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.IntValue;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class newLatch implements IStmt{
    private String var;
    private Exp exp;
    private Lock lock = new ReentrantLock();

    public newLatch(String var, Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        lock.lock();
        if(exp.eval(state.getSymTable(), state.getHeap()).getType().equals(new IntType())) {
            IntValue nr = (IntValue) exp.eval(state.getSymTable(), state.getHeap());
            int i = nr.getVal();
            int free = state.getLatch().getFree();
            state.getLatch().incFree();
            state.getLatch().put(free, i);
            if (state.getSymTable().isDefined(var))
                state.getSymTable().update(var, new IntValue(free));
            else
                throw new MyException("var is not defined in the symbol table newlatch\n");
        }else throw new MyException("var is not of int type new latch");
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new newLatch(var,exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType())) {
            if (exp.typecheck(typeEnv).equals(new IntType())) {
                return typeEnv;
            } else {
                throw new MyException("Expression doesn't have the int type!");
            }
        } else {
            throw new MyException(String.format("%s is not of int type!", var));
        }
    }

    @Override
    public String toString() {
        return "newLatch(" + var + ',' + exp +')';
    }
}
