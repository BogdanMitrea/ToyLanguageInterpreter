import Collection.MyDictionary;
import Collection.MyIDictionary;
import Controller.Controller;
import Model.Commands.ExitCommand;
import Model.Commands.RunExample;
import Model.Expressions.*;
import Model.MyException;
import Model.ProgramState;
import Model.Statements.*;
import Model.VarType.*;
import Repository.IRepository;
import Repository.Repository3;
import View.TextMenu;

public class Interpreter {
    public static void main(String[] args) {
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
                                                                        new closeRFileStatement(new ConstantExpression(new StringValue("varf")))))))))));

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


        try {
            ex1.typecheck(new MyDictionary<String, IVarType>());
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return;
        }
        try {
            ex2.typecheck(new MyDictionary<String, IVarType>());
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return;
        }
        try {
            ex3.typecheck(new MyDictionary<String, IVarType>());
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return;
        }
        try {
           ex4.typecheck(new MyDictionary<String, IVarType>());
        } catch (MyException e) {
           System.out.println(e.getMessage());
            return;
        }
        try {
            ex5.typecheck(new MyDictionary<String, IVarType>());
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return;
        }
        try {
            ex6.typecheck(new MyDictionary<String, IVarType>());
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return;
        }
        try {
            ex7.typecheck(new MyDictionary<String, IVarType>());
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return;
        }
        try {
            ex8.typecheck(new MyDictionary<String, IVarType>());
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return;
        }
        try {
            ex9.typecheck(new MyDictionary<String, IVarType>());
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return;
        }
        try {
            ex10.typecheck(new MyDictionary<String, IVarType>());
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return;
        }

        ProgramState prg1=new ProgramState(ex1);
        ProgramState prg2=new ProgramState(ex2);
        ProgramState prg3=new ProgramState(ex3);
        ProgramState prg4=new ProgramState(ex4);
        ProgramState prg5=new ProgramState(ex5);
        ProgramState prg6=new ProgramState(ex6);
        ProgramState prg7=new ProgramState(ex7);
        ProgramState prg8=new ProgramState(ex8);
        ProgramState prg9=new ProgramState(ex9);
        ProgramState prg10=new ProgramState(ex10);

        IRepository repo1=new Repository3(prg1,"log1.txt");
        Controller controller1=new Controller(repo1);

        IRepository repo2=new Repository3(prg2,"log2.txt");
        Controller controller2=new Controller(repo2);

        IRepository repo3=new Repository3(prg3,"log3.txt");
        Controller controller3=new Controller(repo3);

        IRepository repo4=new Repository3(prg4,"log4.txt");
        Controller controller4=new Controller(repo4);

        IRepository repo5=new Repository3(prg5,"log5.txt");
        Controller controller5=new Controller(repo5);

        IRepository repo6=new Repository3(prg6,"log6.txt");
        Controller controller6=new Controller(repo6);

        IRepository repo7=new Repository3(prg7,"log7.txt");
        Controller controller7=new Controller(repo7);

        IRepository repo8=new Repository3(prg8,"log8.txt");
        Controller controller8=new Controller(repo8);

        IRepository repo9=new Repository3(prg9,"log9.txt");
        Controller controller9=new Controller(repo9);

        IRepository repo10=new Repository3(prg10,"log10.txt");
        Controller controller10=new Controller(repo10);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",ex1.toString(),controller1));
        menu.addCommand(new RunExample("2",ex2.toString(),controller2));
        menu.addCommand(new RunExample("3",ex3.toString(),controller3));
        menu.addCommand(new RunExample("4",ex4.toString(),controller4));
        menu.addCommand(new RunExample("5",ex5.toString(),controller5));
        menu.addCommand(new RunExample("6",ex6.toString(),controller6));
        menu.addCommand(new RunExample("7",ex7.toString(),controller7));
        menu.addCommand(new RunExample("8",ex8.toString(),controller8));
        menu.addCommand(new RunExample("9",ex9.toString(),controller9));
        menu.addCommand(new RunExample("10",ex10.toString(),controller10));
        menu.show();
    }
}
