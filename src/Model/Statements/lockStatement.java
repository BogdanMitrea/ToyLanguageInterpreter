package Model.Statements;

import Collection.ILockTable;
import Collection.MyIDictionary;
import Model.MyException;
import Model.ProgramState;
import Model.VarType.IVarType;
import Model.VarType.IntType;
import Model.VarType.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class lockStatement implements IStatement{
    String varname;

    public lockStatement()
    {

    }
    public lockStatement(String var)
    {
        this.varname=var;
    }
    @Override
    public ProgramState execute(ProgramState prgstate) throws MyException {
        prgstate.getLock().lock();
        MyIDictionary<String, Value> symboltable = prgstate.getSymbolTable();
        ILockTable lockTable = prgstate.getLockTable();
        if (symboltable.isdefined(varname)) {
            Value i = symboltable.get(varname);
            Integer index = Integer.valueOf(i.getVal());
            if (!lockTable.isdefined(index))
                throw new MyException("Index not in LockTable");
            else if (lockTable.get(index) == -1)
                lockTable.put(index, prgstate.getStateid());
            else
                prgstate.getExecutionStack().push(this);
        } else
            throw new MyException("Variable not defined");
        prgstate.getLock().unlock();
        return null;
    }

    @Override
    public IStatement getType() {
        return new lockStatement();
    }

    @Override
    public MyIDictionary<String, IVarType> typecheck(MyIDictionary<String, IVarType> typeEnv) throws MyException {
        if(!typeEnv.get(varname).equals(new IntType()))
            throw new MyException("Var must be integer");
        else
            return typeEnv;
    }

    @Override
    public String toString()
    {
        return "lock("+varname+")";
    }
}
