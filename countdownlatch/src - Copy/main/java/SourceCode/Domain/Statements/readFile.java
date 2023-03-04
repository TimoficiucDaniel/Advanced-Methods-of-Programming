package SourceCode.Domain.Statements;

import SourceCode.Domain.ADT.MyIDictionary;
import SourceCode.Domain.Expression.Exp;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.IntType;
import SourceCode.Domain.Type.StringType;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.IntValue;
import SourceCode.Domain.Value.StringValue;
import SourceCode.Domain.Value.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class readFile implements IStmt{
    private Exp exp;
    private StringValue var_name;


    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        if(state.getSymTable().isDefined(var_name.getVal()) && state.getSymTable().lookup(var_name.getVal()).getType().equals(new IntType()))
        {
            Value val = exp.eval(state.getSymTable(),state.getHeap());
            if(val.getType().equals(new StringType()))
            {
                StringValue s = (StringValue) val;
                BufferedReader buff = state.getFileTable().lookup(s);
                String line = buff.readLine();
                if(line == null)
                {
                    state.getSymTable().put(var_name.getVal(),new IntValue(0));
                }
                else
                {
                    state.getSymTable().put(var_name.getVal(),new IntValue(Integer.parseInt(line)));
                }
            }else throw new MyException("Not a string type\n");
        }
        else throw new MyException("Not an int\n");
        return null;
    }

    public readFile(Exp exp, StringValue var_name) {
        this.exp = exp;
        this.var_name = var_name;
    }

    @Override
    public IStmt deepCopy() {
        return new readFile(exp.deepCopy(),var_name);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (!exp.typecheck(typeEnv).equals(new StringType()))
            throw new MyException(" ReadFile requires a string as expression parameter\n");
        if (!typeEnv.getValue(var_name.getVal()).equals(new IntType()))
            throw new MyException(" ReadFile requires an int as variable parameter\n");
        return typeEnv;
}

    @Override
    public String toString(){
        return "read (" +var_name+ ", "+ exp +") ";
    }
}
