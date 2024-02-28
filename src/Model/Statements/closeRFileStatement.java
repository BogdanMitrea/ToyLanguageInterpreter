package Model.Statements;

import Collection.MyIDictionary;
import Model.Expressions.Expression;
import Model.MyException;
import Model.ProgramState;
import Model.VarType.IVarType;
import Model.VarType.StringType;

import java.io.BufferedReader;
import java.io.IOException;

public class closeRFileStatement implements IStatement{

    Expression expr;
    public closeRFileStatement(Expression expr)
    {
        this.expr=expr;
    }

    public closeRFileStatement() {

    }

    @Override
    public ProgramState execute(ProgramState prgstate) throws MyException {
        String exprstring=expr.evaluate(prgstate.getSymbolTable(), prgstate.getheap()).getVal();
        if(!prgstate.getFileTable().isdefined(exprstring))
            throw new MyException("Filename not in file table");
        BufferedReader reader=prgstate.getFileTable().get(exprstring);
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MyIDictionary<String,BufferedReader> filetable= prgstate.getFileTable();
        filetable.remove(exprstring);
        prgstate.setFileTable(filetable);
        return null;
    }

    @Override
    public IStatement getType() {
        return new closeRFileStatement();
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
        return "close file: "+expr.toString();
    }
}
