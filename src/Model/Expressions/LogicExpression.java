package Model.Expressions;

import Collection.MyIDictionary;
import Collection.MyIHeap;
import Model.MyException;
import Model.VarType.*;

public class LogicExpression implements Expression{

    private Expression e1;
    private Expression e2;
    private String op;
    public LogicExpression(Expression e1,Expression e2,String op)
    {
        this.e1=e1;
        this.e2=e2;
        this.op=op;
    }
    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable, MyIHeap<Value> heap) throws MyException {
        Value e1val=this.e1.evaluate(symbolTable, heap);
        Value e2val=this.e2.evaluate(symbolTable, heap);
        if(op.equals("OR"))
            return new BoolValue(Boolean.parseBoolean(e1val.getVal()) || Boolean.parseBoolean(e2val.getVal()));
        if(op.equals("AND"))
            return new BoolValue( Boolean.parseBoolean(e1val.getVal()) && Boolean.parseBoolean(e2val.getVal()));
        return new IntValue(0);
    }

    @Override
    public IVarType typecheck(MyIDictionary<String, IVarType> typeEnv) throws MyException {
        IVarType typ1= e1.typecheck(typeEnv);
        IVarType typ2= e2.typecheck(typeEnv);
        if(typ1.equals(new BoolType()))
        {
            if(typ2.equals(new BoolType()))
            {
                return new BoolType();
            } else
                throw new MyException("second operand is not a boolean");
        }else
            throw new MyException("first operand is not a boolean");
    }

    @Override
    public String toString()
    {
        return "("+this.e1.toString()+this.op+this.e2.toString()+")";
    }
}
