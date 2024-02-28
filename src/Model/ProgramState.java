package Model;

import Collection.*;
import Model.Expressions.Expression;
import Model.Statements.IStatement;
import Model.VarType.IntValue;
import Model.VarType.Value;

import java.io.BufferedReader;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProgramState {
    private MyIStack<IStatement> executionStack;
    private MyIDictionary<String, Value> symbolTable;
    private MyIList<Value> outputList;
    private MyIDictionary<String,BufferedReader> fileTable;
    private MyIHeap<Value> myheap;
    private ILockTable lockTable;
    private int stateid;
    private static int id=0;
    private static int firstfreeid;

    private static final Lock lock=new ReentrantLock();

    public ProgramState()
    {
        this.executionStack= new MyStack<IStatement>();
        this.symbolTable= new MyDictionary<String,Value>();
        this.fileTable= new MyDictionary<String,BufferedReader>();
        this.outputList= new MyList<Value>();
        this.myheap=new MyHeap<Value>();
        this.stateid= firstfreeid;
        this.lockTable=new LockTable();
    }

    public  ProgramState(IStatement s)
    {
        this.executionStack= new MyStack<IStatement>();
        this.executionStack.push(s);
        this.symbolTable= new MyDictionary<String,Value>();
        this.fileTable= new MyDictionary<String,BufferedReader>();
        this.outputList= new MyList<Value>();
        this.myheap=new MyHeap<Value>();
        this.lockTable=new LockTable();
        this.stateid= firstfreeid;
        firstfreeid+=1;
    }
    public ProgramState(MyIStack<IStatement> exestack,MyIDictionary<String,Value> symboltable, MyIList<Value> outputlist,MyIDictionary<String,BufferedReader> ft,MyIHeap<Value> hp,ILockTable lockTable)
    {
        this.executionStack=exestack;
        this.symbolTable=symboltable;
        this.outputList=outputlist;
        this.fileTable=ft;
        this.myheap=hp;
        this.lockTable=lockTable;
        this.stateid= firstfreeid;
        firstfreeid+=1;
    }

    public ProgramState(MyIStack<IStatement> exestack,MyIDictionary<String,Value> symboltable, MyIList<Value> outputlist,MyIDictionary<String,BufferedReader> ft,MyIHeap<Value> hp,int id,ILockTable lockTable)
    {
        this.lockTable=lockTable;
        this.executionStack=exestack;
        this.symbolTable=symboltable;
        this.outputList=outputlist;
        this.fileTable=ft;
        this.myheap=hp;
        this.stateid=Integer.parseInt(new IntValue(id).getVal());
        firstfreeid=stateid+1;
    }
    public MyIStack<IStatement> getExecutionStack(){
        return this.executionStack;
    }

    public void setExecutionStack(MyIStack<IStatement> newstack)
    {
        this.executionStack=newstack;
    }

    public MyIDictionary<String,Value> getSymbolTable()
    {
        return this.symbolTable;
    }

    public void setSymbolTable(MyIDictionary<String,Value> newtable)
    {
        this.symbolTable=newtable;
    }

    public MyIDictionary<String,BufferedReader> getFileTable()
    {
        return this.fileTable;
    }

    public void setFileTable(MyIDictionary<String,BufferedReader> newtable)
    {
        this.fileTable=newtable;
    }

    public MyIList<Value> getOutputList()
    {
        return this.outputList;
    }

    public void setOutputList(MyIList<Value> newlist)
    {
        this.outputList=newlist;
    }

    public MyIHeap<Value> getheap()
    {
        return this.myheap;
    }

    public void setHeap(MyIHeap<Value> newheap)
    {
        this.myheap=newheap;
    }

    public void addOutput(Value e)
    {
        this.outputList.add(e);
    }

    public Lock getLock(){return lock;}
    public ILockTable getLockTable(){return  this.lockTable;}

    public void setLockTable(ILockTable newlocktable){this.lockTable=newlocktable;}

    public ProgramState oneStep() throws MyException
    {
        try {
            if (executionStack.isEmpty())
                throw new MyException("prgstate stack is empty");
            else {
                IStatement crtStatement = executionStack.pop();
                return crtStatement.execute(this);
            }
        }
        catch (MyException e)
        {
            return null;
        }
    }
    public int getStateid()
    {
        return this.stateid;
    }
    public static synchronized void setId(int new_id)
    {
        id=new_id;
    }
    public Boolean isNotCompleted(){
        return !this.executionStack.isEmpty();
    }

    public int get_firstfreeid()
    {
        synchronized (this) {
            this.firstfreeid += 1;
            return firstfreeid - 1;
        }
    }

    public String toString(){
        return "Program state: "+ id+"\n"+
                "Output list:\n "+outputList.toString()+"\n"+
                "Execution stack:\n "+executionStack.toString()+"\n"+
                "Symbol table:\n "+symbolTable.toString()+"\n"+
                "File table:\n "+fileTable.toString()+"\n"+
                "Heap:\n "+myheap.toString()+"\n"+
                "Lock Table:\n "+lockTable.toString()+"\n"+
                "--------------------------------------------------"+"\n";
    }

}


