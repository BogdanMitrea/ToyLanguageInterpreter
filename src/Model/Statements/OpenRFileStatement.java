package Model.Statements;

import Collection.MyIDictionary;
import Model.Expressions.Expression;
import Model.MyException;
import Model.ProgramState;
import Model.VarType.IVarType;
import Model.VarType.StringType;
import Model.VarType.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFileStatement implements IStatement{
    Expression expr;

    public OpenRFileStatement()
    {

    }
    public OpenRFileStatement(Expression expr)
    {
        this.expr=expr;
    }
    @Override
    public ProgramState execute(ProgramState prgstate) throws MyException {
        MyIDictionary<String,Value> symboltable=prgstate.getSymbolTable();
        Value exprval=expr.evaluate(symboltable, prgstate.getheap());
        if(!(exprval.getType().equals(new StringType())))
            throw new MyException("String type expected");
        if(prgstate.getSymbolTable().isdefined(exprval.getVal()))
            throw new MyException("Filename already in the filetable");
        String filename=exprval.getVal();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        MyIDictionary<String,BufferedReader> filetable=prgstate.getFileTable();
        filetable.put(filename,bufferedReader);
        prgstate.setFileTable(filetable);
        return null;
    }

    @Override
    public IStatement getType() {
        return new OpenRFileStatement();
    }

    public MyIDictionary<String, IVarType> typecheck(MyIDictionary<String, IVarType> typeEnv) throws MyException
    {
        IVarType typexp = expr.typecheck(typeEnv);
        if (typexp.equals(new StringType()))
            return typeEnv;
        else
            throw new MyException("Expression must have string type");
    }

    @Override
    public String toString()
    {
        return "open file: "+expr.toString();
    }
}
