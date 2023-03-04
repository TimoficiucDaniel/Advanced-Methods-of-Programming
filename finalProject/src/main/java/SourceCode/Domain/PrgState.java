package SourceCode.Domain;

import SourceCode.Domain.ADT.*;
import SourceCode.Domain.Expression.ValueExp;
import SourceCode.Domain.Statements.IStmt;
import SourceCode.Domain.Value.StringValue;
import SourceCode.Domain.Value.Value;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class PrgState {
    private MyIDictionary<String, Value> symTbl;
    private MyIStack<IStmt> execution;
    private MyIList<Value> out;
    private IStmt originalProgram;
    private IHeap<Integer,Value> heap;

    private Integer id_thread;
    private Integer LastThreadId=1;

    private MyIDictionary<StringValue, BufferedReader> fileTable;

    public PrgState(MyIDictionary<String, Value> symTbl, MyIStack<IStmt> execution, MyIList<Value> out, IStmt originalProgram
                    , MyIDictionary<StringValue,BufferedReader> fileTable, IHeap<Integer,Value> heap,Integer id) {
        this.symTbl = symTbl;
        this.execution = execution;
        this.out = out;
        this.originalProgram = originalProgram.deepCopy();
        this.fileTable = fileTable;
        this.heap = heap;
        this.id_thread = id;
        execution.push(originalProgram);

    }

    public IHeap<Integer, Value> getHeap() {
        return heap;
    }

    public boolean isNotCompleted(){
        return !(execution.isEmpty());
    }

    public Integer getId_thread(){
        return id_thread;
    }

    public Integer getLastThreadId() {
        return LastThreadId;
    }
    public void setLastThreadId(Integer id){
        LastThreadId = id;
    }

    public PrgState oneStep() throws MyException, IOException {
        if(execution.isEmpty()) throw new MyException("prgstate stack is empty");
        IStmt crtStmt = execution.pop();
        return crtStmt.execute(this);
    }

    @Override
    public String toString() {
        return "Thread:\n"+ id_thread.toString()+"\nExeStack:\n"+execution.toString()+"SymTable:\n"+symTbl.toString()+"Out:\n"+out.toString()+"FileTable:\n"
                +fileTable.toStringFileTable()+"Heap:\n"+heap.toString()+"\n\n";
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTbl;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIStack<IStmt> getStk() {
        return execution;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public MyIList<IStmt> getStackAsList() {
        MyIList<IStmt> stack = new MyList<>(new ArrayList<>());
        Stack<IStmt> helper = new Stack<>();
        while(!execution.isEmpty()) {
            IStmt crtStmt = execution.pop();
            stack.add(crtStmt);
            helper.push(crtStmt);
        }
        while(!helper.isEmpty())
        {
            IStmt crtStmt = helper.pop();
            execution.push(crtStmt);
        }

        return stack;
    }
}
