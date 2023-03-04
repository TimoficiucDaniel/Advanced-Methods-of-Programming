package SourceCode.View;

import SourceCode.Controller.Controller;
import SourceCode.Domain.ADT.*;
import SourceCode.Domain.Expression.*;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Statements.*;
import SourceCode.Domain.Type.*;
import SourceCode.Domain.Value.BooleanValue;
import SourceCode.Domain.Value.IntValue;
import SourceCode.Domain.Value.StringValue;
import SourceCode.Domain.Value.Value;
import SourceCode.Repository.IRepo;
import SourceCode.Repository.Repo;
import com.example.a7.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

class Interpreter {
    public static void main(String[] args) {

        IProcTable<String, Pair<List<String>,IStmt>> procTable= new ProcTable<>(new HashMap<>());

        IStmt sumProc = new CompStmt(new VarDecStmt("v",new IntType()),new CompStmt(new AssignStmt("v",new ArithmeticExp(
                '+',new VariableExp("a"),new VariableExp("b"))),new PrintStmt(new VariableExp("v"))));
        List<String> varSum = new ArrayList<>();
        varSum.add("a");
        varSum.add("b");
        try {
            procTable.put("sum",new Pair<>(varSum,sumProc));
        } catch (MyException e) {
            throw new RuntimeException(e);
        }

        IStmt prodProc = new CompStmt(new VarDecStmt("v",new IntType()),new CompStmt(new AssignStmt("v",new ArithmeticExp(
                '*',new VariableExp("a"),new VariableExp("b"))),new PrintStmt(new VariableExp("v"))));
        List<String> varProd = new ArrayList<>();
        varProd.add("a");
        varProd.add("b");
        try {
            procTable.put("product",new Pair<>(varProd,prodProc));
        } catch (MyException e) {
            throw new RuntimeException(e);
        }


        IStmt ex1 = new CompStmt(new VarDecStmt("v", new IntType()), new CompStmt(new AssignStmt
                ("v", new ValueExp(new IntValue(2))), new PrintStmt(new VariableExp("v"))));
        MyIStack<IStmt> exeStk = new MyStack<IStmt>(new Stack<>());
        MyIStack<MyIDictionary<String, Value>> symTbl = new MyStack<MyIDictionary<String, Value>>(new Stack<>());
        MyIList<Value> out = new MyList<>(new ArrayList<>());
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>(new HashMap<>());
        IHeap<Integer,Value> heap1 = new Heap<>(new HashMap<>());
        PrgState prg1 = new PrgState(symTbl, exeStk, out, ex1, fileTable,heap1,1,procTable);
        IRepo repo1 = new Repo(prg1, "log1.txt");
        Controller ctr1 = new Controller(repo1);

        IStmt ex2 = new CompStmt(new VarDecStmt("a", new IntType()), new CompStmt(new VarDecStmt
                ("b", new IntType()), new CompStmt(new AssignStmt("a", new ArithmeticExp(
                '+', new ValueExp(new IntValue(2)), new ArithmeticExp('*',
                new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))), new CompStmt(new
                AssignStmt("b", new ArithmeticExp('+', new VariableExp("a"), new ValueExp
                (new IntValue(1)))), new PrintStmt(new VariableExp("b"))))));
        MyIStack<IStmt> exeStk1 = new MyStack<IStmt>(new Stack<>());
        MyIStack<MyIDictionary<String, Value>> symTbl1 = new MyStack<MyIDictionary<String, Value>>(new Stack<>());
        MyIList<Value> out1 = new MyList<>(new ArrayList<>());
        MyIDictionary<StringValue, BufferedReader> fileTable1 = new MyDictionary<>(new HashMap<>());
        IHeap<Integer,Value> heap2 = new Heap<>(new HashMap<>());
        PrgState prg2 = new PrgState(symTbl1, exeStk1, out1, ex1, fileTable1,heap2,1,procTable);
        IRepo repo2 = new Repo(prg2, "log2.txt");
        Controller ctr2 = new Controller(repo2);

        IStmt ex3 = new CompStmt(new VarDecStmt("a", new BooleanType()), new CompStmt(new VarDecStmt
                ("v", new IntType()), new CompStmt(new AssignStmt("a", new ValueExp(new BooleanValue
                (true))), new CompStmt(new IfStmt(new VariableExp("a"), new AssignStmt("v", new
                ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))),
                new PrintStmt(new VariableExp("v"))))));
        MyIStack<IStmt> exeStk2 = new MyStack<IStmt>(new Stack<>());
        MyIStack<MyIDictionary<String, Value>> symTbl2 = new MyStack<MyIDictionary<String, Value>>(new Stack<>());
        MyIList<Value> out2 = new MyList<>(new ArrayList<>());
        MyIDictionary<StringValue, BufferedReader> fileTable2 = new MyDictionary<>(new HashMap<>());
        IHeap<Integer,Value> heap3 = new Heap<>(new HashMap<>());
        PrgState prg3 = new PrgState(symTbl2, exeStk2, out2, ex1, fileTable2,heap3,1,procTable);
        IRepo repo3 = new Repo(prg3, "log3.txt");
        Controller ctr3 = new Controller(repo3);

        IStmt ex4 = new CompStmt(new VarDecStmt("varf",new StringType()),new CompStmt(new AssignStmt("varf",new
                ValueExp(new StringValue("D:\\projects\\A3cred\\src\\Repository\\test.in"))),new CompStmt(new OpenRFile(new VariableExp("varf")),new
                CompStmt(new VarDecStmt("varc",new IntType()),new CompStmt(new readFile(new VariableExp("varf")
                ,new StringValue("varc")),new CompStmt(new PrintStmt(new VariableExp("varc")),new CompStmt(new readFile(new
                VariableExp("varf"),new StringValue("varc")),new CompStmt(new PrintStmt(new VariableExp("varc")),new
                CloseRFile(new VariableExp("varf"))))))))));
        MyIStack<IStmt> exeStk3 = new MyStack<IStmt>(new Stack<>());
        MyIStack<MyIDictionary<String, Value>> symTbl3 = new MyStack<MyIDictionary<String, Value>>(new Stack<>());
        MyIList<Value> out3 = new MyList<>(new ArrayList<>());
        MyIDictionary<StringValue, BufferedReader> fileTable3 = new MyDictionary<>(new HashMap<>());
        IHeap<Integer,Value> heap4 = new Heap<>(new HashMap<>());
        PrgState prg4 = new PrgState(symTbl3, exeStk3, out3, ex4, fileTable3,heap4,1,procTable);
        IRepo repo4 = new Repo(prg4, "log4.txt");
        Controller ctr4 = new Controller(repo4);


        IStmt ex5 = new CompStmt(new VarDecStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new
                ValueExp(new IntValue(4))), new CompStmt(new WhileStmt(new RelationalExp(new VariableExp("v"),
                new ValueExp(new IntValue(0)), ">"), new CompStmt(new PrintStmt(new VariableExp("v")),
                new AssignStmt("v", new ArithmeticExp('-', new VariableExp("v"), new ValueExp(new IntValue(1)))))),
                new CompStmt(new PrintStmt(new VariableExp("v")), new NopStmt()))));
        MyIStack<IStmt> exeStk4 = new MyStack<IStmt>(new Stack<>());
        MyIStack<MyIDictionary<String, Value>> symTbl4 = new MyStack<MyIDictionary<String, Value>>(new Stack<>());
        MyIList<Value> out4 = new MyList<>(new ArrayList<>());
        MyIDictionary<StringValue, BufferedReader> fileTable4 = new MyDictionary<>(new HashMap<>());
        IHeap<Integer,Value> heap5 = new Heap<>(new HashMap<>());
        PrgState prg5 = new PrgState(symTbl4, exeStk4, out4, ex5, fileTable4,heap5,1,procTable);
        IRepo repo5 = new Repo(prg5, "log5.txt");
        Controller ctr5 = new Controller(repo5);

        IStmt ex6 = new CompStmt(new VarDecStmt("v", new RefType(new IntType())), new CompStmt(new HeapAllocation
                ("v", new ValueExp(new IntValue(20))), new CompStmt(new VarDecStmt("a", new RefType(new
                RefType(new IntType()))), new CompStmt(new HeapAllocation("a", new VariableExp("v")), new
                CompStmt(new PrintStmt(new VariableExp("v")), new CompStmt(new PrintStmt(new VariableExp("a")),
                new NopStmt()))))));
        MyIStack<IStmt> exeStk6 = new MyStack<IStmt>(new Stack<>());
        MyIStack<MyIDictionary<String, Value>> symTbl6 = new MyStack<MyIDictionary<String, Value>>(new Stack<>());
        MyIList<Value> out6 = new MyList<>(new ArrayList<>());
        MyIDictionary<StringValue, BufferedReader> fileTable6 = new MyDictionary<>(new HashMap<>());
        IHeap<Integer,Value> heap6 = new Heap<>(new HashMap<>());
        PrgState prg6 = new PrgState(symTbl6, exeStk6, out6, ex6, fileTable6,heap6,1,procTable);
        IRepo repo6 = new Repo(prg6, "log6.txt");
        Controller ctr6 = new Controller(repo6);

        IStmt ex7 = new CompStmt(new VarDecStmt("v", new RefType(new IntType())), new CompStmt(new
                HeapAllocation("v", new ValueExp(new IntValue(20))), new CompStmt(new VarDecStmt("a",
                new RefType(new RefType(new IntType()))), new CompStmt(new HeapAllocation("a", new VariableExp("v")),
                new CompStmt(new PrintStmt(new ReadHeap(new VariableExp("v"))), new CompStmt(new PrintStmt(new ArithmeticExp
                        ('+', new ValueExp(new IntValue(5)), new ReadHeap(new ReadHeap(new VariableExp("a"))))
                ), new NopStmt()))))));
        MyIStack<IStmt> exeStk7 = new MyStack<IStmt>(new Stack<>());
        MyIStack<MyIDictionary<String, Value>> symTbl7 = new MyStack<MyIDictionary<String, Value>>(new Stack<>());
        MyIList<Value> out7 = new MyList<>(new ArrayList<>());
        MyIDictionary<StringValue, BufferedReader> fileTable7 = new MyDictionary<>(new HashMap<>());
        IHeap<Integer,Value> heap7 = new Heap<>(new HashMap<>());
        PrgState prg7 = new PrgState(symTbl7, exeStk7, out7, ex7, fileTable7,heap7,1,procTable);
        IRepo repo7 = new Repo(prg7, "log7.txt");
        Controller ctr7 = new Controller(repo7);

        IStmt ex8 =new CompStmt(new VarDecStmt("v", new RefType(new IntType())), new CompStmt(new HeapAllocation
                ("v", new ValueExp(new IntValue(20))), new CompStmt(new PrintStmt(new ReadHeap(new VariableExp("v"))),
                new CompStmt(new HeapWrite(new ValueExp(new IntValue(30)), "v"), new CompStmt(new PrintStmt(
                        new ArithmeticExp('+', new ReadHeap(new VariableExp("v")), new ValueExp(new IntValue(5))
                        )), new NopStmt())))));
        MyIStack<IStmt> exeStk8 = new MyStack<IStmt>(new Stack<>());
        MyIStack<MyIDictionary<String, Value>> symTbl8 = new MyStack<MyIDictionary<String, Value>>(new Stack<>());
        MyIList<Value> out8 = new MyList<>(new ArrayList<>());
        MyIDictionary<StringValue, BufferedReader> fileTable8 = new MyDictionary<>(new HashMap<>());
        IHeap<Integer,Value> heap8 = new Heap<>(new HashMap<>());
        PrgState prg8= new PrgState(symTbl8, exeStk8, out8, ex8, fileTable8,heap8,1,procTable);
        IRepo repo8 = new Repo(prg8, "log8.txt");
        Controller ctr8 = new Controller(repo8);

        IStmt ex9 =new CompStmt(new VarDecStmt("v", new RefType(new IntType())), new CompStmt(
                new HeapAllocation("v", new ValueExp(new IntValue(20))), new CompStmt(new VarDecStmt("a",
                new RefType(new RefType(new IntType()))), new CompStmt(new HeapAllocation("a", new VariableExp("v")),
                new CompStmt(new HeapAllocation("v", new ValueExp(new IntValue(30))), new CompStmt(new PrintStmt(
                        new ReadHeap(new ReadHeap(new VariableExp("a")))), new NopStmt()))))));
        MyIStack<IStmt> exeStk9 = new MyStack<IStmt>(new Stack<>());
        MyIStack<MyIDictionary<String, Value>> symTbl9 = new MyStack<MyIDictionary<String, Value>>(new Stack<>());
        MyIList<Value> out9 = new MyList<>(new ArrayList<>());
        MyIDictionary<StringValue, BufferedReader> fileTable9 = new MyDictionary<>(new HashMap<>());
        IHeap<Integer,Value> heap9 = new Heap<>(new HashMap<>());
        PrgState prg9= new PrgState(symTbl9, exeStk9, out9, ex9, fileTable9,heap9,1,procTable);
        IRepo repo9 = new Repo(prg9, "log9.txt");
        Controller ctr9 = new Controller(repo9);

        IStmt ex10 = new CompStmt(new VarDecStmt("v",new IntType()),new CompStmt(new VarDecStmt("a",new RefType(new IntType()))
                ,new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(10))),new CompStmt(new HeapAllocation("a"
                ,new ValueExp(new IntValue(22))),new CompStmt(new forkStmt(new CompStmt(new HeapWrite(new ValueExp(new
                IntValue(30)), "a"),new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(32))),new CompStmt(new
                PrintStmt(new VariableExp("v")),new PrintStmt(new ReadHeap(new VariableExp("a"))))))),new CompStmt(new PrintStmt(new VariableExp("v"))
                ,new PrintStmt(new ReadHeap(new VariableExp("a")))))))));
        MyIStack<IStmt> exeStk10 = new MyStack<IStmt>(new Stack<>());
        MyIStack<MyIDictionary<String, Value>> symTbl10 = new MyStack<MyIDictionary<String, Value>>(new Stack<>());
        MyIList<Value> out10 = new MyList<>(new ArrayList<>());
        MyIDictionary<StringValue, BufferedReader> fileTable10 = new MyDictionary<>(new HashMap<>());
        IHeap<Integer,Value> heap10 = new Heap<>(new HashMap<>());
        MyIDictionary<String, Type> typeEnv = new MyDictionary<>(new HashMap<>());
        try{
            ex10.typecheck(typeEnv);
        } catch (MyException e) {
            throw new RuntimeException(e);
        }
        PrgState prg10= new PrgState(symTbl10, exeStk10, out10, ex10, fileTable10,heap10,1,procTable);
        IRepo repo10 = new Repo(prg10, "log10.txt");
        Controller ctr10 = new Controller(repo10);

        IStmt ex11 = new CompStmt(new VarDecStmt("v",new IntType()),new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))),
                new CompStmt(new VarDecStmt("w",new IntType()),new CompStmt(new AssignStmt("w",new ValueExp(new IntValue(5))),
                        new CompStmt(new fname("sum",new ArrayList<>(Arrays.asList(new ArithmeticExp('*',new VariableExp("v"),new ValueExp(new IntValue(10))),
                                new VariableExp("w")))),new CompStmt(new PrintStmt(new VariableExp("v")),new CompStmt(new forkStmt(new fname(
                                        "product",new ArrayList<>(Arrays.asList(new VariableExp("v"),new VariableExp("w"))))),new CompStmt(
                                                new forkStmt(new fname("sum",new ArrayList<>(Arrays.asList(new VariableExp("v"),new VariableExp("w"))))),new NopStmt()))))))));
        MyIStack<IStmt> exeStk11 = new MyStack<IStmt>(new Stack<>());
        MyIStack<MyIDictionary<String, Value>> symTbl11 = new MyStack<MyIDictionary<String, Value>>(new Stack<>());
        MyIList<Value> out11 = new MyList<>(new ArrayList<>());
        MyIDictionary<StringValue, BufferedReader> fileTable11 = new MyDictionary<>(new HashMap<>());
        IHeap<Integer,Value> heap11 = new Heap<>(new HashMap<>());
        PrgState prg11= new PrgState(symTbl11, exeStk11, out11, ex11, fileTable11,heap11,1,procTable);
        IRepo repo11 = new Repo(prg11, "log11.txt");
        Controller ctr11 = new Controller(repo11);

        IStmt ex12 = new CompStmt(new VarDecStmt("v",new IntType()),new CompStmt(new WhileStmt(new RelationalExp(new VariableExp("v"),
                new ValueExp(new IntValue(3)),"<"),new CompStmt(new forkStmt(new CompStmt(new PrintStmt(new VariableExp("v")),
                new AssignStmt("v",new ArithmeticExp('+',new VariableExp("v"),new ValueExp(new IntValue(1)))))),
                new AssignStmt("v",new ArithmeticExp('+',new VariableExp("v"),new ValueExp(new IntValue(1)))))),
                new CompStmt(new Sleep(5),new CompStmt(new PrintStmt(new ArithmeticExp('*',new VariableExp("v"),new ValueExp(new IntValue(10)))),
                        new NopStmt()))));
        MyIStack<IStmt> exeStk12 = new MyStack<IStmt>(new Stack<>());
        MyIStack<MyIDictionary<String, Value>> symTbl12 = new MyStack<MyIDictionary<String, Value>>(new Stack<>());
        MyIList<Value> out12 = new MyList<>(new ArrayList<>());
        MyIDictionary<StringValue, BufferedReader> fileTable12 = new MyDictionary<>(new HashMap<>());
        IHeap<Integer,Value> heap12 = new Heap<>(new HashMap<>());
        PrgState prg12= new PrgState(symTbl12, exeStk12, out12, ex12, fileTable12,heap12,1,procTable);
        IRepo repo12 = new Repo(prg12, "log12.txt");
        Controller ctr12 = new Controller(repo12);

        List<IStmt> statements = new ArrayList<>();
        statements.add(ex1);
        statements.add(ex2);
        statements.add(ex3);
        statements.add(ex4);
        statements.add(ex5);
        statements.add(ex6);
        statements.add(ex7);
        statements.add(ex8);
        statements.add(ex9);
        statements.add(ex10);
        statements.add(ex11);
        statements.add(ex12);



        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        menu.addCommand(new RunExample("4",ex4.toString(),ctr4));
        menu.addCommand(new RunExample("5",ex5.toString(),ctr5));
        menu.addCommand(new RunExample("6",ex6.toString(),ctr6  ));
        menu.addCommand(new RunExample("7",ex7.toString(),ctr7  ));
        menu.addCommand(new RunExample("8",ex8.toString(),ctr8  ));
        menu.addCommand(new RunExample("9",ex9.toString(),ctr9  ));
        menu.addCommand(new RunExample("10",ex10.toString(),ctr10  ));
        menu.addCommand(new RunExample("11",ex11.toString(),ctr11  ));
        menu.addCommand(new RunExample("12",ex11.toString(),ctr12  ));
        menu.show();
    }
}