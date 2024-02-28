package Model.Statements;

import Collection.MyIDictionary;
import Model.MyException;
import Model.ProgramState;
import Model.VarType.*;

public class VarDeclStatement implements IStatement{
    String name;
    IVarType typ;
    Value val;

    public VarDeclStatement()
    {

    }
    public VarDeclStatement(String name,IVarType typ)
    {
        this.name=name;
        this.typ=typ;
        //this.val=val;
    }
    @Override
    public ProgramState execute(ProgramState prgstate) throws MyException {
        MyIDictionary<String, Value> symboltable=prgstate.getSymbolTable();
        symboltable.put(name,this.typ.defaultValue());
        prgstate.setSymbolTable(symboltable);
        return null;
    }

    @Override
    public IStatement getType() {
        return new VarDeclStatement();
    }

    public MyIDictionary<String, IVarType> typecheck(MyIDictionary<String, IVarType> typeEnv) throws MyException
    {
        typeEnv.put(name,typ);
        return typeEnv;
    }

    @Override
    public String toString() {
        return typ.toString()+" "+name;
    }
}
