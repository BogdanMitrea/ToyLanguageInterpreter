package GUI;

import Collection.MyIStack;
import Controller.Controller;
import Model.MyException;
import Model.ProgramState;
import Model.Statements.CompoundStatement;
import Model.Statements.IStatement;
import Model.VarType.Value;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.*;
import Collection.MyHeap;
public class ProgramRunLayout implements Initializable {
    private Controller controller;
    private ProgramState displayedstate=new ProgramState();
    @FXML
    private ListView<Value> outputlist=new ListView<>();
    @FXML
    public Label donelabel=new Label();

    @FXML
    private ListView<String> executionstack=new ListView<>();

    @FXML
    private ListView<String> filetable=new ListView<>();

    @FXML
    TableView<Map.Entry<Integer, String>> heap = new TableView<>();

    @FXML
    TableColumn<Map.Entry<Integer, Value>, Integer> addressColumn = new TableColumn<>();

    @FXML
    TableColumn<Map.Entry<String, String>, String> valueColumn = new TableColumn<>();

    @FXML
    ListView<Integer> programstateslist=new ListView<>();

    @FXML
    TextField statefield=new TextField();

    @FXML
    TableView<Map.Entry<String, String>> symboltable = new TableView<>();

    @FXML
    TableColumn<Map.Entry<String, String>, String> nameColumn = new TableColumn<>();

    @FXML
    TableColumn<Map.Entry<String, String>, String> symbolvalueColumn = new TableColumn<>();

    @FXML
    TableView<Map.Entry<Integer, Integer>> locktable = new TableView<>();

    @FXML
    TableColumn<Map.Entry<Integer, Integer>, Integer> checklockColumn = new TableColumn<>();

    @FXML
    TableColumn<Map.Entry<Integer, Integer>, Integer> varColumn = new TableColumn<>();


    public ArrayList<String> StacktoStringArray(MyIStack<IStatement> stk)
    {
        Stack<IStatement> stack=new Stack<>();
        for (IStatement statement : stk.getstack()) {
            stack.push(statement);
        }
        ArrayList<String> res=new ArrayList<>();
        while (!stack.isEmpty())
        {
            if(stack.get(stack.size()-1).getType() instanceof CompoundStatement) {
                res.add(((CompoundStatement) stack.get(stack.size() - 1)).getFirst().toString());
                IStatement second = ((CompoundStatement) stack.get(stack.size() - 1)).getSecond();
                stack.pop();
                stack.push(second);
            }
            else
            {
                res.add(stack.get(stack.size() - 1).toString());
                stack.pop();
            }
        }
        return res;
    }
    public void loadData(){
        executionstack.getItems().clear();
        executionstack.getItems().addAll(StacktoStringArray(displayedstate.getExecutionStack()));

        outputlist.setItems(FXCollections.observableArrayList(
                (controller.getRepository().getCurrentProgramState().getOutputList().getArrayList())
        ));

        filetable.setItems(FXCollections.observableArrayList(
                (controller.getRepository().getCurrentProgramState().getFileTable().getContent().keySet())
        ));

        List<Map.Entry<Integer, String>> heapTableList=new ArrayList<>();
        for(Map.Entry<Integer, Value> element:displayedstate.getheap().getContent().entrySet()){
            Map.Entry<Integer, String> el=new AbstractMap.SimpleEntry<Integer, String>(element.getKey(),element.getValue().toString());
            heapTableList.add(el);
        }
        heap.setItems(FXCollections.observableList(heapTableList));
        addressColumn.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        valueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue()));

        List<Map.Entry<String, String>> symboltablelist=new ArrayList<>();
        for(Map.Entry<String, Value> element:displayedstate.getSymbolTable().getContent().entrySet()){
            Map.Entry<String, String> el=new AbstractMap.SimpleEntry<String, String>(element.getKey(),element.getValue().toString());
            symboltablelist.add(el);
        }
        symboltable.setItems(FXCollections.observableList(symboltablelist));
        nameColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getKey()));
        symbolvalueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue()));

        List<Map.Entry<Integer, Integer>> locktablelist=new ArrayList<>();
        for(Map.Entry<Integer, Integer> element:displayedstate.getLockTable().getContent().entrySet()){
            Map.Entry<Integer, Integer> el=new AbstractMap.SimpleEntry<Integer, Integer>(element.getKey(),element.getValue());
            locktablelist.add(el);
        }
        locktable.setItems(FXCollections.observableList(locktablelist));
        varColumn.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        checklockColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getValue()).asObject());

        programstateslist.getItems().clear();
        programstateslist.getItems().addAll(controller.getPrograms().stream().map(ProgramState::getStateid).toList());

        statefield.setText(""+programstateslist.getItems().size());
    }

    public void setController(Controller ctrl) {
        controller=ctrl;
        displayedstate=ctrl.getRepository().getCurrentProgramState();
        loadData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void switchProgramState(MouseEvent event) {
        int newi = programstateslist.getSelectionModel().getSelectedIndex();
        MyHeap<ProgramState> idtoprogramstate = controller.getIndextoprogramstate();
        ProgramState mainprg=idtoprogramstate.get(0);
        if(idtoprogramstate.size()>1 && newi!=-1) {
            displayedstate = idtoprogramstate.get(newi+1);
            loadData();
        }
    }

    public void OneStepPressed() throws MyException {

        try {
            controller.oneStepForAllPrg(controller.getPrograms());
            loadData();
        }
        catch (MyException e)
        {
            donelabel.setText("No more steps remaining");
        }
        try {
            controller.oneStepForAllPrg(controller.getPrograms());
            loadData();
        }
        catch (MyException e)
        {
            donelabel.setText("No more steps remaining");
        }
    }
}
