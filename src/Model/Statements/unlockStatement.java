package Model.Statements;

import Collection.ILockTable;
import Collection.MyIDictionary;
import Model.MyException;
import Model.ProgramState;
import Model.VarType.IVarType;
import Model.VarType.IntType;
import Model.VarType.Value;

public class unlockStatement implements IStatement{
    String varname;

    public unlockStatement()
    {

    }
    public unlockStatement(String var)
    {
        this.varname=var;
    }
    @Override
    public ProgramState execute(ProgramState prgstate) throws MyException {
        MyIDictionary<String, Value> symboltable= prgstate.getSymbolTable();
        ILockTable lockTable= prgstate.getLockTable();
        if(symboltable.isdefined(varname)) {
            Value i = symboltable.get(varname);
            Integer index= Integer.valueOf(i.getVal());
            if(!lockTable.isdefined(index))
                throw new MyException("The index does not exist in the locktable");
            else if(lockTable.get(index)==prgstate.getStateid())
                lockTable.put(index,-1);
            else
                return null;
        }
        else
            throw new MyException("Variable not defined");
        return null;
    }

    @Override
    public IStatement getType() {
        return new unlockStatement();
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
        return "unlock("+varname+")";
    }
}
