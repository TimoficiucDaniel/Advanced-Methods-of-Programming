package com.example.a7;

import SourceCode.Domain.Expression.ArithmeticExp;
import SourceCode.Domain.Expression.VariableExp;
import SourceCode.Domain.MyException;
import SourceCode.Domain.Statements.*;
import SourceCode.Domain.Type.IntType;
import SourceCode.Domain.Value.Value;
import com.example.a7.Pair;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import SourceCode.Controller.Controller;
import SourceCode.Domain.ADT.*;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Type.Type;
import SourceCode.Domain.Value.StringValue;
import SourceCode.Domain.Value.Value;
import SourceCode.Repository.IRepo;
import SourceCode.Repository.Repo;

import java.io.BufferedReader;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Scene2Controller {
    @FXML private TextField textField;
    @FXML private TableView proctable;
    @FXML private TableColumn<ThreePair<String,List<String>,IStmt>,String> name;
    @FXML private TableColumn<ThreePair<String,List<String>,IStmt>,List<String>> names;
    @FXML private TableColumn<ThreePair<String,List<String>,IStmt>,IStmt> statement;

    @FXML private TableView<Pair<Integer, Value>> heap;
    @FXML private TableColumn<Pair<Integer, Value>, Integer> heapAddress;
    @FXML private TableColumn<Pair<Integer, Value>, Value> heapValue;
    @FXML private ListView out;
    @FXML private ListView fileTable;
    @FXML private ListView prgStateId;
    @FXML private TableView symTable;
    @FXML private TableColumn<Pair<String ,Value>, String> symTableName;
    @FXML private TableColumn<Pair<String ,Value>, Value> symTableValue;
    @FXML private ListView exeStack;
    @FXML private Button oneStep;
    private IStmt stmt;
    private PrgState pr;
    private IRepo repo;
    private Controller ctrl;

    private ExecutorService executor = Executors.newFixedThreadPool(2);
    private Integer id;

    public void initScene()
    {

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
        pr = new PrgState( new MyStack<MyIDictionary<String,Value>>(new Stack<>()),new MyStack<>(new Stack<>()), new MyList<>(new ArrayList<>()),stmt,
                new MyDictionary<>(new HashMap<>()), new Heap<>(new HashMap<>()), 1,procTable);
        ArrayList<PrgState> list = new ArrayList<>();
        list.add(pr);
        repo = new Repo(pr,"GuiLog");
        ctrl = new Controller(repo);
        name.setCellValueFactory(new PropertyValueFactory<ThreePair<String,List<String>,IStmt>,String>("first"));
        names.setCellValueFactory(new PropertyValueFactory<ThreePair<String,List<String>,IStmt>,List<String>>("second"));
        statement.setCellValueFactory(new PropertyValueFactory<ThreePair<String,List<String>,IStmt>,IStmt>("third"));
        heapAddress.setCellValueFactory(new PropertyValueFactory<Pair<Integer,Value>,Integer>("first"));
        heapValue.setCellValueFactory(new PropertyValueFactory<Pair<Integer,Value>,Value>("second"));
        symTableName.setCellValueFactory(new PropertyValueFactory<Pair<String,Value>,String>("first"));
        symTableValue.setCellValueFactory(new PropertyValueFactory<Pair<String,Value>,Value>("second"));

        prgStateId.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                id = (Integer) prgStateId.getSelectionModel().getSelectedItem();
            }
        });
    }

    public void populate(){
        populateTextField();
        populateHeap();
        populateOut();
        populateFiletable();
        populatePrgStateId();
        populateProcTable();
    }

    private void populateTextField()
    {
        textField.setText(String.valueOf(ctrl.getnrOfPrg()));
    }

    private void populateHeap() {
        ObservableList<Pair<Integer, Value>> obsList = FXCollections.observableArrayList();
        if (ctrl.getnrOfPrg() > 0)
        {
            heap.getItems().clear();
            for (Map.Entry<Integer, Value> r : ctrl.getCrtPrg().get(0).getHeap().getSet()) {
                Pair<Integer, Value> pair = new Pair<>(r.getKey(), r.getValue());
                obsList.add(pair);
            }
            heap.setItems(obsList);
            heap.refresh();
        }
    }

    private void populateOut() {
        if (ctrl.getnrOfPrg() > 0) {
            out.getItems().clear();
            Iterator<Value> i = ctrl.getCrtPrg().get(0).getOut().getIter();
            while (i.hasNext()) {
                out.getItems().add(i.next());
            }

            out.refresh();
        }
    }


    private void  populateFiletable(){
        if(ctrl.getnrOfPrg()>0){
            fileTable.getItems().clear();
        for(Map.Entry<StringValue, BufferedReader> r : ctrl.getCrtPrg().get(0).getFileTable().getSet())
        {
            fileTable.getItems().add(r.getKey());
        }
        fileTable.refresh();
    }
    }

    private void populatePrgStateId(){
        prgStateId.getItems().clear();
        for(PrgState prg : ctrl.getCrtPrg()){
            prgStateId.getItems().add(prg.getId_thread());
        }
        prgStateId.refresh();
    }

    private void populateSymTable(Integer id){
        symTable.getItems().clear();
        ObservableList<Pair<String, Value>> obsList = FXCollections.observableArrayList();
        for(Map.Entry<String, Value> r : ctrl.repo.getprg(id).getSymTable().getSet())
        {
            Pair<String, Value> pair = new Pair<>(r.getKey(),r.getValue());
            obsList.add(pair);
        }
        symTable.setItems(obsList);
        symTable.refresh();
    }

    private void populateProcTable() {
        if (ctrl.getnrOfPrg() > 0) {
            proctable.getItems().clear();
            ObservableList<ThreePair<String, List<String>, IStmt>> obsList = FXCollections.observableArrayList();
            for (Map.Entry<String, Pair<List<String>, IStmt>> r : ctrl.getCrtPrg().get(0).getProctable().getSet()) {
                ThreePair<String, List<String>, IStmt> pair = new ThreePair<>(r.getKey(), r.getValue().first, r.getValue().second);
                obsList.add(pair);
            }
            proctable.setItems(obsList);
            proctable.refresh();
        }
    }

    private void populateExeStack(Integer id){
        if(ctrl.getnrOfPrg()>0){
            exeStack.getItems().clear();
        Iterator<IStmt> i = ctrl.repo.getprg(id).getStackAsList().getIter();
        while (i.hasNext())
        {
            exeStack.getItems().add(i.next());
        }

        exeStack.refresh();
        }
    }

    public void setCurrent(IStmt stmt)
    {
        this.stmt=stmt;
    }

    @FXML
    public void runOneStep(){
        ctrl.allStepGui();
        populate();
    }

    @FXML
    public void getPrgWithId(MouseEvent event)
    {
        if(id!=null) {

                    populateExeStack(id);
                    populateSymTable(id);


    }
    }
}
