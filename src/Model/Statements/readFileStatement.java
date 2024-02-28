package Model.Statements;

import Collection.MyIDictionary;
import Model.Expressions.Expression;
import Model.MyException;
import Model.ProgramState;
import Model.VarType.*;

import java.io.BufferedReader;
import java.io.IOException;

public class readFileStatement implements IStatement{
    Expression expr;
    String var_name;

    public readFileStatement()
    {

    }
    public readFileStatement(Expression expr,String name)
    {
        this.expr=expr;
        this.var_name=name;
    }
    @Override
    public ProgramState execute(ProgramState prgstate) throws MyException {
        MyIDictionary<String,Value> symboltable=prgstate.getSymbolTable();
        Value exprval=null;
        if(!symboltable.isdefined(var_name))
            throw new MyException("Variable is not defined");
        if(!symboltable.get(var_name).getType().equals(new IntType()))
            throw new MyException("Variable must be an integer");
        try {
            exprval=this.expr.evaluate(symboltable, prgstate.getheap());
        }
        catch (MyException e)
        {
            throw new MyException(e.getMessage());
        }
        String exprvalstring=exprval.getVal();
        BufferedReader reader=null;
        if(!prgstate.getFileTable().isdefined(exprvalstring))
            throw new MyException("filename is not defined in the filetable");
        else
            reader = prgstate.getFileTable().get(exprvalstring);
        Value newval;
        try {
            String readval=reader.readLine();
            if(readval == null)
                newval=new IntValue(0);
            else
                newval=new IntValue(Integer.parseInt(readval));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        symboltable.put(var_name,newval);
        prgstate.setSymbolTable(symboltable);
        return null;
    }

    @Override
    public IStatement getType() {
        return new readFileStatement();
    }

    public MyIDictionary<String, IVarType> typecheck(MyIDictionary<String, IVarType> typeEnv) throws MyException
    {
        IVarType typevar = typeEnv.get(var_name);
        IVarType typexp = expr.typecheck(typeEnv);
        if(typevar.equals(new IntType()) && typexp.equals(new StringType()))
            return typeEnv;
        else
            throw new MyException("ReadFile stmt: right hand side and left hand side have different types ");
    }

    @Override
    public String toString()
    {
        return "Readfile: "+this.expr.toString()+" to "+this.var_name;
    }
}
