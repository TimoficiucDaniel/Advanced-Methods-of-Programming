package SourceCode.View;

import SourceCode.Controller.Controller;
import SourceCode.Domain.ADT.*;
import SourceCode.Domain.Expression.ArithmeticExp;
import SourceCode.Domain.Expression.ValueExp;
import SourceCode.Domain.Expression.VariableExp;
import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Statements.*;
import SourceCode.Domain.Type.BooleanType;
import SourceCode.Domain.Type.IntType;
import SourceCode.Domain.Value.BooleanValue;
import SourceCode.Domain.Value.IntValue;
import SourceCode.Domain.Value.StringValue;
import SourceCode.Domain.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class View {
    Controller controller;

    public View(Controller controller) {
        this.controller = controller;
    }

    public void printMenu(){
        System.out.println("0.Exit");
        System.out.println("1.Input a program");
        System.out.println("2.Run program");
    }

    public void printProgramMenu(){
        System.out.println("1:"+"\n"+"int v; v=2;Print(v)");
        System.out.println("2:"+"\n"+"int a;int b; a=2+3*5;b=a+1;Print(b)");
        System.out.println("3:"+"\n"+"bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)");
    }

    public void start() throws IOException, MyException {
        try {
            System.out.println("Insert file name: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String logFilePath = reader.readLine();
            while (true) {
                controller.setRepoFilePath(logFilePath);
                this.printMenu();
                System.out.println("\nChoose an option:");
                String option = reader.readLine();
                if (option.equals("0")) return;
                else if (option.equals("1")) {
                    this.printProgramMenu();
                    System.out.println("Choose a program:");
                    option = reader.readLine();
                    if (option.equals("1")) {
                        IStmt ex1 = new CompStmt(new VarDecStmt("v", new IntType()), new CompStmt(new AssignStmt
                                ("v", new ValueExp(new IntValue(2))), new PrintStmt(new VariableExp("v"))));
                        MyIStack<IStmt> exeStk = new MyStack<IStmt>(new Stack<>());
                        MyIDictionary<String, Value> symTbl = new MyDictionary<>(new HashMap<>());
                        MyIList<Value> out = new MyList<>(new ArrayList<>());
                        MyIDictionary<StringValue, BufferedReader > fileTable = new MyDictionary<>(new HashMap<>());
                        IHeap<Integer,Value> heap = new Heap<>(new HashMap<>());
                        controller.addPrg(new PrgState(symTbl, exeStk, out, ex1,fileTable,heap,1));
                    } else if (option.equals("2")) {
                        IStmt ex2 = new CompStmt(new VarDecStmt("a", new IntType()), new CompStmt(new VarDecStmt
                                ("b", new IntType()), new CompStmt(new AssignStmt("a", new ArithmeticExp(
                                '+', new ValueExp(new IntValue(2)), new ArithmeticExp('*',
                                new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))), new CompStmt(new
                                AssignStmt("b", new ArithmeticExp('+', new VariableExp("a"), new ValueExp
                                (new IntValue(1)))), new PrintStmt(new VariableExp("b"))))));
                        MyIStack<IStmt> exeStk = new MyStack<>(new Stack<>());
                        MyIDictionary<String, Value> symTbl = new MyDictionary<>(new HashMap<>());
                        MyIList<Value> out = new MyList<>(new ArrayList<>());
                        MyIDictionary<StringValue, BufferedReader > fileTable = new MyDictionary<>(new HashMap<>());
                        IHeap<Integer,Value> heap = new Heap<>(new HashMap<>());
                        controller.addPrg(new PrgState(symTbl, exeStk, out, ex2,fileTable,heap,1));
                    } else if (option.equals("3")) {
                        IStmt ex3 = new CompStmt(new VarDecStmt("a", new BooleanType()), new CompStmt(new VarDecStmt
                                ("v", new IntType()), new CompStmt(new AssignStmt("a", new ValueExp(new BooleanValue
                                (true))), new CompStmt(new IfStmt(new VariableExp("a"), new AssignStmt("v", new
                                ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                new PrintStmt(new VariableExp("v"))))));

                        MyIStack<IStmt> exeStk = new MyStack<>(new Stack<>());
                        MyIDictionary<String, Value> symTbl = new MyDictionary<>(new HashMap<>());
                        MyIList<Value> out = new MyList<>(new ArrayList<>());
                        MyIDictionary<StringValue, BufferedReader > fileTable = new MyDictionary<>(new HashMap<>());
                        IHeap<Integer,Value> heap = new Heap<>(new HashMap<>());
                        controller.addPrg(new PrgState(symTbl, exeStk, out, ex3,fileTable,heap,1));
                    } else System.out.println("Input a valid option");
                } else if (option.equals("2"))
                    controller.allStep();
                else System.out.println("Input a valid option");
            }
        }catch (IOException e){
            throw e;
        }
    }
}
