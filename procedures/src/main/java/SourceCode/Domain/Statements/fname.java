package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.MyDictionary;
import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.Expression.Exp;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class fname implements IStmt{

    private String name;
    private List<Exp> expressions;

    public fname(String name, List<Exp> expressions) {
        this.name = name;
        this.expressions = expressions;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        if(state.getProctable().isDefined(name))
        {
            List<String> names = state.getProctable().getContent(name).getFirst();
            IStmt stmt= state.getProctable().getContent(name).getSecond();
            MyIDictionary<String, Value> newSym = new MyDictionary<>(new HashMap<>());
            for(String var: names)
            {
                int id = names.indexOf(var);
                newSym.put(var,expressions.get(id).eval(state.getSymTable(), state.getHeap()));
            }
            state.getSymTblStack().push(newSym);
            state.getStk().push(new Return());
            state.getStk().push(stmt);
        }
        else throw new MyException("proc not defined\n");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        List<Exp> copy = new ArrayList<>();
        for(Exp f:expressions)
            copy.add(f.deepCopy());
        return new fname(name,copy);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("call "+name+"(");
        for(Exp f:expressions)
            string.append(f.toString());
        string.append(")");
        return string.toString();
    }
}
