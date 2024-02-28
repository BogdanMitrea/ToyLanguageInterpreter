package Model.Statements;

import Collection.*;
import Model.MyException;
import Model.ProgramState;
import Model.VarType.IVarType;
import Model.VarType.Value;

import java.io.BufferedReader;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class forkStatement implements IStatement{
    IStatement statement;
    private final Lock lock=new ReentrantLock();
    public forkStatement(IStatement statement)
    {
        this.statement=statement;
    }

    public forkStatement() {

    }

    @Override
    public ProgramState execute(ProgramState prgstate) throws MyException {
        int newid=-1;
        MyIList<Value> outputlist=prgstate.getOutputList();
        MyIDictionary<String,Value> oldsymboltable= prgstate.getSymbolTable();
        MyIDictionary<String, BufferedReader> fileTable= prgstate.getFileTable();
        MyIHeap<Value> heap= prgstate.getheap();
        ILockTable lcktable= prgstate.getLockTable();
        lock.lock();
        try {
            newid = prgstate.get_firstfreeid();
        }
        finally {
            lock.unlock();
        }

        MyIStack<IStatement> newexecutionstack=new MyStack<IStatement>();
        MyIDictionary<String,Value> newsymboltable=new MyDictionary<String,Value>();
        for(Map.Entry<String,Value>  entry:oldsymboltable.getContent().entrySet())
            newsymboltable.put(new String(entry.getKey()), entry.getValue().deepcopy());
        newexecutionstack.push(this.statement);
        return new ProgramState(newexecutionstack,newsymboltable,outputlist,fileTable,heap,newid,lcktable);
    }

    @Override
    public IStatement getType() {
        return new forkStatement();
    }

    public MyIDictionary<String, IVarType> typecheck(MyIDictionary<String, IVarType> typeEnv) throws MyException
    {
        statement.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString()
    {
        return "fork("+statement.toString()+")";
    }
}
