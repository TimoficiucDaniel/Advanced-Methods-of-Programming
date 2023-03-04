package com.example.a7;

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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;


import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SceneController implements Initializable {
    @FXML
    private Stage stage;

    @FXML
    private Button ContinueButton;
    @FXML
    private ListView<IStmt> initList;
    @FXML
    private Parent root;
    @FXML
    private Scene scene;

    private IStmt currentIStmt = null;

    @FXML
    public void switchToScene2(ActionEvent event) throws IOException {
        if(currentIStmt != null)
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
            root = loader.load();

            Scene2Controller scene2controller = loader.getController();
            scene2controller.setCurrent(currentIStmt);
            scene2controller.initScene();
            scene2controller.populate();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
        else  {
            System.out.println("error");
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IStmt ex1 = new CompStmt(new VarDecStmt("v", new IntType()), new CompStmt(new AssignStmt
                ("v", new ValueExp(new IntValue(2))), new PrintStmt(new VariableExp("v"))));

        IStmt ex2 = new CompStmt(new VarDecStmt("a", new IntType()), new CompStmt(new VarDecStmt
                ("b", new IntType()), new CompStmt(new AssignStmt("a", new ArithmeticExp(
                '+', new ValueExp(new IntValue(2)), new ArithmeticExp('*',
                new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))), new CompStmt(new
                AssignStmt("b", new ArithmeticExp('+', new VariableExp("a"), new ValueExp
                (new IntValue(1)))), new PrintStmt(new VariableExp("b"))))));

        IStmt ex3 = new CompStmt(new VarDecStmt("a", new BooleanType()), new CompStmt(new VarDecStmt
                ("v", new IntType()), new CompStmt(new AssignStmt("a", new ValueExp(new BooleanValue
                (true))), new CompStmt(new IfStmt(new VariableExp("a"), new AssignStmt("v", new
                ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))),
                new PrintStmt(new VariableExp("v"))))));

        IStmt ex4 = new CompStmt(new VarDecStmt("varf",new StringType()),new CompStmt(new AssignStmt("varf",new
                ValueExp(new StringValue("D:\\projects\\A3cred\\src\\Repository\\test.in"))),new CompStmt(new OpenRFile(new VariableExp("varf")),new
                CompStmt(new VarDecStmt("varc",new IntType()),new CompStmt(new readFile(new VariableExp("varf")
                ,new StringValue("varc")),new CompStmt(new PrintStmt(new VariableExp("varc")),new CompStmt(new readFile(new
                VariableExp("varf"),new StringValue("varc")),new CompStmt(new PrintStmt(new VariableExp("varc")),new
                CloseRFile(new VariableExp("varf"))))))))));

        IStmt ex5 = new CompStmt(new VarDecStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new
                ValueExp(new IntValue(4))), new CompStmt(new WhileStmt(new RelationalExp(new VariableExp("v"),
                new ValueExp(new IntValue(0)), ">"), new CompStmt(new PrintStmt(new VariableExp("v")),
                new AssignStmt("v", new ArithmeticExp('-', new VariableExp("v"), new ValueExp(new IntValue(1)))))),
                new CompStmt(new PrintStmt(new VariableExp("v")), new NopStmt()))));

        IStmt ex6 = new CompStmt(new VarDecStmt("v", new RefType(new IntType())), new CompStmt(new HeapAllocation
                ("v", new ValueExp(new IntValue(20))), new CompStmt(new VarDecStmt("a", new RefType(new
                RefType(new IntType()))), new CompStmt(new HeapAllocation("a", new VariableExp("v")), new
                CompStmt(new PrintStmt(new VariableExp("v")), new CompStmt(new PrintStmt(new VariableExp("a")),
                new NopStmt()))))));

        IStmt ex7 = new CompStmt(new VarDecStmt("v", new RefType(new IntType())), new CompStmt(new
                HeapAllocation("v", new ValueExp(new IntValue(20))), new CompStmt(new VarDecStmt("a",
                new RefType(new RefType(new IntType()))), new CompStmt(new HeapAllocation("a", new VariableExp("v")),
                new CompStmt(new PrintStmt(new ReadHeap(new VariableExp("v"))), new CompStmt(new PrintStmt(new ArithmeticExp
                        ('+', new ValueExp(new IntValue(5)), new ReadHeap(new ReadHeap(new VariableExp("a"))))
                ), new NopStmt()))))));

        IStmt ex8 =new CompStmt(new VarDecStmt("v", new RefType(new IntType())), new CompStmt(new HeapAllocation
                ("v", new ValueExp(new IntValue(20))), new CompStmt(new PrintStmt(new ReadHeap(new VariableExp("v"))),
                new CompStmt(new HeapWrite(new ValueExp(new IntValue(30)), "v"), new CompStmt(new PrintStmt(
                        new ArithmeticExp('+', new ReadHeap(new VariableExp("v")), new ValueExp(new IntValue(5))
                        )), new NopStmt())))));

        IStmt ex9 =new CompStmt(new VarDecStmt("v", new RefType(new IntType())), new CompStmt(
                new HeapAllocation("v", new ValueExp(new IntValue(20))), new CompStmt(new VarDecStmt("a",
                new RefType(new RefType(new IntType()))), new CompStmt(new HeapAllocation("a", new VariableExp("v")),
                new CompStmt(new HeapAllocation("v", new ValueExp(new IntValue(30))), new CompStmt(new PrintStmt(
                        new ReadHeap(new ReadHeap(new VariableExp("a")))), new NopStmt()))))));

        IStmt ex10 = new CompStmt(new VarDecStmt("v",new IntType()),new CompStmt(new VarDecStmt("a",new RefType(new IntType()))
                ,new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(10))),new CompStmt(new HeapAllocation("a"
                ,new ValueExp(new IntValue(22))),new CompStmt(new forkStmt(new CompStmt(new HeapWrite(new ValueExp(new
                                IntValue(30)), "a"),new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(32))),new CompStmt(new
                PrintStmt(new VariableExp("v")),new PrintStmt(new ReadHeap(new VariableExp("a"))))))),new CompStmt(new PrintStmt(new VariableExp("v"))
                ,new PrintStmt(new ReadHeap(new VariableExp("a")))))))));
        MyIDictionary<String, Type> typeEnv = new MyDictionary<>(new HashMap<>());
        try{
            ex10.typecheck(typeEnv);
        } catch (MyException e) {
            throw new RuntimeException(e);
        }

        IStmt ex11 = new CompStmt(
                new VarDecStmt("v1", new RefType(new IntType())),
                new CompStmt(
                        new VarDecStmt("cnt", new IntType()),
                        new CompStmt(
                                new HeapAllocation("v1", new ValueExp(new IntValue(1))),
                                new CompStmt(
                                        new CreateSemaphore("cnt", new ReadHeap(new VariableExp("v1"))),
                                        new CompStmt(
                                                new forkStmt(
                                                        new CompStmt(
                                                                new AcquireStmt("cnt"),
                                                                new CompStmt(
                                                                        new HeapWrite( new ArithmeticExp('*', new ReadHeap(new VariableExp("v1")), new ValueExp(new IntValue(10))),"v1"),
                                                                        new CompStmt(
                                                                                new PrintStmt(new ReadHeap(new VariableExp("v1"))),
                                                                                new Release("cnt")
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompStmt(
                                                        new forkStmt(
                                                                new CompStmt(
                                                                        new AcquireStmt("cnt"),
                                                                        new CompStmt(
                                                                                new HeapWrite( new ArithmeticExp('*',  new ReadHeap(new VariableExp("v1")), new ValueExp(new IntValue(10))),"v1"),
                                                                                new CompStmt(
                                                                                        new HeapWrite( new ArithmeticExp('*',  new ReadHeap(new VariableExp("v1")), new ValueExp(new IntValue(2))),"v1"),
                                                                                        new CompStmt(
                                                                                                new PrintStmt(new ReadHeap(new VariableExp("v1"))),
                                                                                                new Release("cnt")
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        ),
                                                        new CompStmt(
                                                                new AcquireStmt("cnt"),
                                                                new CompStmt(
                                                                        new PrintStmt(new ArithmeticExp('-', new ReadHeap(new VariableExp("v1")), new ValueExp(new IntValue(1)))),
                                                                        new Release("cnt")
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        IStmt ex12 = new CompStmt(new VarDecStmt("b",new BooleanType()),new CompStmt(new VarDecStmt("c",new IntType()),
                new CompStmt(new AssignStmt("b",new ValueExp(new BooleanValue(true))),new CompStmt(new ConditionalAssignmentStmt(
                        "c",new VariableExp("b"),new ValueExp(new IntValue(100)),new ValueExp(new IntValue(200))),new
                        CompStmt(new PrintStmt(new VariableExp("c")),new CompStmt(new ConditionalAssignmentStmt("c",
                        new ValueExp(new BooleanValue(false)),new ValueExp(new IntValue(100)),new ValueExp(new IntValue(200))),new PrintStmt(new VariableExp("c"))))))));

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
        initList.getItems().addAll(statements);

        initList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IStmt>() {
            @Override
            public void changed(ObservableValue<? extends IStmt> observableValue, IStmt iStmt, IStmt t1) {
                currentIStmt = initList.getSelectionModel().getSelectedItem();
            }
        });
    }

}