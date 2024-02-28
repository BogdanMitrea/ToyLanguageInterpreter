package GUI;

import Collection.MyDictionary;
import Controller.Controller;
import Model.Expressions.*;
import Model.MyException;
import Model.ProgramState;
import Model.Statements.*;
import Model.VarType.*;
import Repository.IRepository;
import Repository.Repository3;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainLayout implements Initializable {

    @FXML
    private HBox hBox;
    @FXML
    private Button runbutton;
    @FXML
    private ListView<String> programListView=new ListView<>();
    List<Controller> validprograms;
    List<IStatement> statementList;
    public void PopulateListView()
    {
        IStatement ex1=new CompoundStatement(new VarDeclStatement("v",new IntType()),new CompoundStatement(new AssignStatement("v",
                new ConstantExpression(new IntValue(2))),new PrintStatement(new VarExpression("v"))));

        IStatement ex2 = new CompoundStatement( new VarDeclStatement("a",new IntType()),
                new CompoundStatement(new VarDeclStatement("b",new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ArithmeticExpression("+",new ConstantExpression(new IntValue(2)),new
                                ArithmeticExpression("*",new ConstantExpression(new IntValue(3)), new ConstantExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignStatement("b",new ArithmeticExpression("+",new VarExpression("a"), new ValueExpression(new
                                        IntValue(1)))), new PrintStatement(new VarExpression("b"))))));

        IStatement ex3 = new CompoundStatement(new VarDeclStatement("a",new BoolType()),
                new CompoundStatement(new VarDeclStatement("v", new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VarExpression("a"),new AssignStatement("v",new ValueExpression(new
                                        IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                                        VarExpression("v"))))));

        IStatement ex4= new CompoundStatement(new VarDeclStatement("varf",new StringType()),
                new CompoundStatement(new AssignStatement("varf",new ConstantExpression(new StringValue("test.in"))),
                        new CompoundStatement(new OpenRFileStatement(new VarExpression("varf")),
                                new CompoundStatement(new VarDeclStatement("varc",new IntType()),
                                        new CompoundStatement(new readFileStatement(new VarExpression("varf"),"varc"),
                                                new CompoundStatement(new PrintStatement(new VarExpression("varc")),
                                                        new CompoundStatement(new readFileStatement(new VarExpression("varf"),"varc"),
                                                                new CompoundStatement(new PrintStatement(new VarExpression("varc")),
                                                                        new closeRFileStatement(new VarExpression("varf"))))))))));

        IStatement ex5=new IfStatement(new BooleanExpression("<=",new ConstantExpression(new IntValue(2)),new ConstantExpression(new IntValue(3))),
                new PrintStatement(new ConstantExpression(new IntValue(10))),new PrintStatement(new ConstantExpression(new IntValue(20))));

        IStatement ex6=new CompoundStatement(new VarDeclStatement("v",new RefType(new IntType())),
                new CompoundStatement(new newAllocStatement("v",new ConstantExpression(new IntValue(20))),
                        new CompoundStatement(new VarDeclStatement("a",new RefType(new RefType(new IntType()))),
                                new CompoundStatement(new newAllocStatement("a",new VarExpression("v")),
                                        new CompoundStatement(new PrintStatement(new VarExpression("v")),new PrintStatement(new VarExpression("a")))))));

        IStatement ex7=new CompoundStatement(new VarDeclStatement("v",new IntType()),
                new CompoundStatement(new AssignStatement("v",new ConstantExpression(new IntValue(4))),
                        new CompoundStatement(new whileStatement(new BooleanExpression(">",new VarExpression("v"),new ConstantExpression(new IntValue(0))),
                                new CompoundStatement(new PrintStatement(new VarExpression("v")),new AssignStatement("v",new ArithmeticExpression("-",
                                        new VarExpression("v"),new ConstantExpression(new IntValue(1)))))),
                                new PrintStatement(new VarExpression("v")))));

        IStatement ex8=new CompoundStatement(new VarDeclStatement("v",new RefType(new IntType())),
                new CompoundStatement(new newAllocStatement("v",new ConstantExpression(new IntValue(20))),
                        new CompoundStatement(new PrintStatement(new readHeap(new VarExpression("v"))),
                                new CompoundStatement(new writeHeap("v",new ConstantExpression(new IntValue(30))),
                                        new CompoundStatement(new PrintStatement(new ArithmeticExpression("+",new readHeap(new VarExpression("v")),
                                                new ConstantExpression(new IntValue(5)))),
                                                new PrintStatement(new VarExpression("v")))))));
        IStatement ex9=new CompoundStatement(new VarDeclStatement("v",new RefType(new IntType())),
                new CompoundStatement(new newAllocStatement("v",new ConstantExpression(new IntValue(20))),
                        new CompoundStatement(new VarDeclStatement("a",new RefType(new RefType(new IntType()))),
                                new CompoundStatement(new newAllocStatement("a",new VarExpression("v")),
                                        new CompoundStatement(new newAllocStatement("v",new ConstantExpression(new IntValue(30))),
                                                new PrintStatement(new readHeap(new readHeap(new VarExpression("a")))))))));

        IStatement ex10=new CompoundStatement(new VarDeclStatement("v",new IntType()),
                new CompoundStatement(new VarDeclStatement("a",new RefType(new IntType())),
                        new CompoundStatement(new AssignStatement("v",new ConstantExpression(new IntValue(10))),
                                new CompoundStatement(new newAllocStatement("a",new ConstantExpression(new IntValue(22))),
                                        new CompoundStatement(new forkStatement(
                                                new CompoundStatement(new writeHeap("a",new ConstantExpression(new IntValue(30))),
                                                        new CompoundStatement(new AssignStatement("v",new ConstantExpression(new IntValue(32))),
                                                                new CompoundStatement(new PrintStatement(new VarExpression("v")),
                                                                        new PrintStatement(new readHeap(new VarExpression("a"))))))),
                                                new CompoundStatement(new VarDeclStatement("t",new BoolType()),
                                                        new CompoundStatement(new PrintStatement(new VarExpression("v")),
                                                                new PrintStatement(new readHeap(new VarExpression("a"))))))))));

        IStatement ex11=new CompoundStatement(new VarDeclStatement("a", new RefType(new IntType())),
                new CompoundStatement(new newAllocStatement("a", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VarDeclStatement("v", new IntType()),
                                new CompoundStatement(new forStatement("v", new ValueExpression(new IntValue(0)), new ValueExpression(new IntValue(3)), new ArithmeticExpression("+", new VarExpression("v"), new ValueExpression(new IntValue(1))),
                                        new forkStatement(new CompoundStatement(new PrintStatement(new VarExpression("v")),
                                                new AssignStatement("v", new ArithmeticExpression("*", new VarExpression("v"), new readHeap(new VarExpression("a"))))))),
                                        new PrintStatement(new readHeap(new VarExpression("a")))))));


        IStatement ex12= new CompoundStatement(new VarDeclStatement("v1", new RefType(new IntType())),
                new CompoundStatement(new VarDeclStatement("v2", new RefType(new IntType())),
                        new CompoundStatement(new VarDeclStatement("x", new IntType()),
                                new CompoundStatement(new VarDeclStatement("q", new IntType()),
                                        new CompoundStatement(new newAllocStatement("v1", new ValueExpression(new IntValue(20))) ,
                                                new CompoundStatement(new newAllocStatement("v2", new ValueExpression(new IntValue(30))),
                                                        new CompoundStatement(new newLockStatement("x"),
                                                                new CompoundStatement(new forkStatement(
                                                                        new CompoundStatement(new forkStatement(
                                                                                new CompoundStatement(new lockStatement("x"),
                                                                                        new CompoundStatement(new writeHeap("v1", new ArithmeticExpression("-", new readHeap(new VarExpression("v1")), new ValueExpression(new IntValue(1)))),
                                                                                                new unlockStatement("x")))
                                                                        ),
                                                                                new CompoundStatement(new lockStatement("x"),
                                                                                        new CompoundStatement(new writeHeap("v1", new ArithmeticExpression("*", new readHeap(new VarExpression("v1")), new ValueExpression(new IntValue(10)))),
                                                                                                new unlockStatement("x"))))
                                                                ),
                                                                        new CompoundStatement( new newLockStatement("q"),
                                                                                new CompoundStatement(new forkStatement(
                                                                                        new CompoundStatement( new forkStatement(
                                                                                                new CompoundStatement(new lockStatement("q"),
                                                                                                        new CompoundStatement(new writeHeap("v2", new ArithmeticExpression("+", new readHeap(new VarExpression("v2")), new ValueExpression(new IntValue(5)))),
                                                                                                                new unlockStatement("q")))
                                                                                        ),
                                                                                                new CompoundStatement(new lockStatement("q"),
                                                                                                        new CompoundStatement(new writeHeap("v2", new ArithmeticExpression("*", new readHeap(new VarExpression("v2")), new ValueExpression(new IntValue(10)))),
                                                                                                                new unlockStatement("q"))))
                                                                                ),
                                                                                        new CompoundStatement(new SkipStatement(),
                                                                                                new CompoundStatement(new SkipStatement(),
                                                                                                        new CompoundStatement(new SkipStatement(),
                                                                                                                new CompoundStatement(new SkipStatement(),
                                                                                                                        new CompoundStatement(new lockStatement("x"),
                                                                                                                                new CompoundStatement(new PrintStatement(new readHeap(new VarExpression("v1"))),
                                                                                                                                        new CompoundStatement(new unlockStatement("x"),
                                                                                                                                                new CompoundStatement(new lockStatement("q"),
                                                                                                                                                        new CompoundStatement(new PrintStatement(new readHeap(new VarExpression("v2"))),
                                                                                                                                                                new unlockStatement("q"))))))))))))))))))));

        try {
            ex1.typecheck(new MyDictionary<String, IVarType>());
            ProgramState prg1=new ProgramState(ex1);
            IRepository repo1=new Repository3(prg1,"log1.txt");
            Controller controller1=new Controller(repo1);
            statementList.add(ex1);
            validprograms.add(controller1);
        } catch (MyException e) {
            //return;
        }
        try {
            ex2.typecheck(new MyDictionary<String, IVarType>());
            ProgramState prg2=new ProgramState(ex2);
            IRepository repo2=new Repository3(prg2,"log2.txt");
            Controller controller2=new Controller(repo2);
            statementList.add(ex2);
            validprograms.add(controller2);
        } catch (MyException e) {
            //return;
        }
        try {
            ex3.typecheck(new MyDictionary<String, IVarType>());
            ProgramState prg3=new ProgramState(ex3);
            IRepository repo3=new Repository3(prg3,"log3.txt");
            Controller controller3=new Controller(repo3);
            statementList.add(ex3);
            validprograms.add(controller3);
        } catch (MyException e) {
            //return;
        }
        try {
            ex4.typecheck(new MyDictionary<String, IVarType>());
            ProgramState prg4=new ProgramState(ex4);
            IRepository repo4=new Repository3(prg4,"log4.txt");
            Controller controller4=new Controller(repo4);
            statementList.add(ex4);
            validprograms.add(controller4);
        } catch (MyException e) {
            //return;
        }
        try {
            ex5.typecheck(new MyDictionary<String, IVarType>());
            ProgramState prg5=new ProgramState(ex5);
            IRepository repo5=new Repository3(prg5,"log5.txt");
            Controller controller5=new Controller(repo5);
            statementList.add(ex5);
            validprograms.add(controller5);
        } catch (MyException e) {
            //return;
        }
        try {
            ex6.typecheck(new MyDictionary<String, IVarType>());
            ProgramState prg6=new ProgramState(ex6);
            IRepository repo6=new Repository3(prg6,"log6.txt");
            Controller controller6=new Controller(repo6);
            statementList.add(ex6);
            validprograms.add(controller6);
        } catch (MyException e) {
            //return;
        }
        try {
            ex7.typecheck(new MyDictionary<String, IVarType>());
            ProgramState prg7=new ProgramState(ex7);
            IRepository repo7=new Repository3(prg7,"log7.txt");
            Controller controller7=new Controller(repo7);
            statementList.add(ex7);
            validprograms.add(controller7);
        } catch (MyException e) {
            //return;
        }
        try {
            ex8.typecheck(new MyDictionary<String, IVarType>());
            ProgramState prg8=new ProgramState(ex8);
            IRepository repo8=new Repository3(prg8,"log8.txt");
            Controller controller8=new Controller(repo8);
            statementList.add(ex8);
            validprograms.add(controller8);
        } catch (MyException e) {
            //return;
        }
        try {
            ex9.typecheck(new MyDictionary<String, IVarType>());
            ProgramState prg9=new ProgramState(ex9);
            IRepository repo9=new Repository3(prg9,"log9.txt");
            Controller controller9=new Controller(repo9);
            statementList.add(ex9);
            validprograms.add(controller9);
        } catch (MyException e) {
            //return;
        }
        try {
            ex10.typecheck(new MyDictionary<String, IVarType>());
            ProgramState prg10=new ProgramState(ex10);
            IRepository repo10=new Repository3(prg10,"log10.txt");
            Controller controller10=new Controller(repo10);
            statementList.add(ex10);
            validprograms.add(controller10);
        } catch (MyException e) {
            //return;
        }
        try {
            ex11.typecheck(new MyDictionary<String, IVarType>());
            ProgramState prg11=new ProgramState(ex11);
            IRepository repo11=new Repository3(prg11,"log11.txt");
            Controller controller11=new Controller(repo11);
            statementList.add(ex11);
            validprograms.add(controller11);
        } catch (MyException e) {
            //return;
        }
        try {
            ex12.typecheck(new MyDictionary<String, IVarType>());
            ProgramState prg12=new ProgramState(ex12);
            IRepository repo12=new Repository3(prg12,"log12.txt");
            Controller controller12=new Controller(repo12);
            statementList.add(ex12);
            validprograms.add(controller12);
        } catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error encountered!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        programListView.setItems(FXCollections.observableArrayList(
                statementList.stream().map(IStatement::toString).collect(Collectors.toList())
        ));
    }
    @FXML
    private void RunButtonPressed()
    {
        if (programListView.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("program-run-layout.fxml"));
                Parent interpreterView = loader.load();
                ProgramRunLayout controller = loader.getController();
                controller.setController(validprograms.get(programListView.getSelectionModel().getSelectedIndex()));
                Stage stage = new Stage();
                stage.setTitle("Program "+(programListView.getSelectionModel().getSelectedIndex()+1));
                stage.setScene(new Scene(interpreterView));
                stage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validprograms=new ArrayList<>();
        statementList=new ArrayList<>();
        HBox.setHgrow(runbutton, Priority.ALWAYS);
        HBox.setHgrow(programListView, Priority.ALWAYS);
    }
}
