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

public class UnlockStmt implements IStmt{

    private String str;
    private Lock lock  = new ReentrantLock();

    public UnlockStmt(String str) {
        this.str = str;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        lock.lock();
        if(state.getSymTable().isDefined(str)){
            if(state.getSymTable().lookup(str).getType().equals(new IntType()))
            {
                IntValue i = (IntValue) state.getSymTable().getValue(str);
                int found = i.getVal();
                if(state.getLocktable().contains(found))
                    if(state.getLocktable().getContent(found)== state.getId_thread())
                        state.getLocktable().update(found,-1);

            }}
        else
            throw new MyException("Var not in symtable\n");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new UnlockStmt(str);
    }

    @Override
    public String toString() {
        return "unlock("+str+")";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.lookup(str).equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("Var not of intType!\n");
    }
}
