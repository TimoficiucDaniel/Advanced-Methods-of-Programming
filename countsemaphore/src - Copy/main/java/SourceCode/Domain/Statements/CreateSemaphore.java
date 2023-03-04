package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.Expression.Exp;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.IntType;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.IntValue;
import com.example.a7.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CreateSemaphore implements IStmt{
    private String var;
    private Exp exp;
    private Lock lock = new ReentrantLock();

    public CreateSemaphore(String var, Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        lock.lock();
        if(exp.eval(state.getSymTable(), state.getHeap()) instanceof IntValue) {
            IntValue nr = (IntValue) exp.eval(state.getSymTable(), state.getHeap());
            int number = nr.getVal();
            int free = state.getSemTable().getFree();
            state.getSemTable().incFree();
            state.getSemTable().put(free,new Pair<>(number,new ArrayList<>()));
            if(state.getSymTable().isDefined(var)) {
                if (state.getSymTable().lookup(var).getType() instanceof IntType) {
                    state.getSymTable().update(var, new IntValue(free));
                } else throw new MyException("var is not an instance of intType crSem\n");
            } else throw new MyException("var not declared in Symtable crSem\n");
        }
        else throw new MyException("not an instance of intvalue crSem\n");
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CreateSemaphore(var,exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType())) {
            if (exp.typecheck(typeEnv).equals(new IntType()))
                return typeEnv;
            else
                throw new MyException("Expression is not of int type!");
        } else {
            throw new MyException(String.format("%s is not of type int!", var));
        }
    }

    @Override
    public String toString() {
        return "createSemaphore("+var+","+exp.toString()+")";
    }
}
