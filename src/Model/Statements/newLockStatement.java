package Model.Statements;

import Collection.ILockTable;
import Collection.MyIDictionary;
import Model.MyException;
import Model.ProgramState;
import Model.VarType.IVarType;
import Model.VarType.IntType;
import Model.VarType.IntValue;
import Model.VarType.Value;

public class newLockStatement implements IStatement{

    private String varname;
    public newLockStatement(String var)
    {
        this.varname=var;
    }

    public newLockStatement() {

    }

    @Override
    public ProgramState execute(ProgramState prgstate) throws MyException {
        MyIDictionary<String, Value> symboltable= prgstate.getSymbolTable();
        ILockTable lockTable= prgstate.getLockTable();
        Integer firstfree=lockTable.getFirstFree();
        lockTable.put(firstfree, -1);
        symboltable.put(varname, new IntValue(firstfree));
        return null;
    }

    @Override
    public IStatement getType() {
        return new newLockStatement();
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
        return "newLock("+varname.toString()+")";
    }
}
