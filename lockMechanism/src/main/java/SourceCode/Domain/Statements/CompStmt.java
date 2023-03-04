package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.ADT.MyIStack;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.Type;

public class CompStmt implements IStmt{
    private IStmt first;
    private IStmt snd;

    public CompStmt(IStmt first, IStmt snd) {
        this.first = first;
        this.snd = snd;
    }

    @Override
    public String toString(){
        return "(  "+first.toString()+"  ;  "+snd.toString()+"  )";
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(this.first.deepCopy(),this.snd.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        MyIDictionary<String,Type> typEnv1 = first.typecheck(typeEnv);
        MyIDictionary<String,Type> typEnv2 = snd.typecheck(typEnv1);
        return typEnv2;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk =state.getStk();
        stk.push(snd);
        stk.push(first);
        return null;
    }


}
