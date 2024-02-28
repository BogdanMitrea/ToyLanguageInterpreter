package Model.Expressions;

import Collection.MyIDictionary;
import Collection.MyIHeap;
import Model.MyException;
import Model.VarType.IVarType;
import Model.VarType.IntType;
import Model.VarType.IntValue;
import Model.VarType.Value;

public class ArithmeticExpression implements Expression{
    private Expression e1;
    private Expression e2;
    private String op;
    public ArithmeticExpression(String op,Expression e1,Expression e2)
    {
        this.e1=e1;
        this.e2=e2;
        this.op=op;
    }
    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable, MyIHeap<Value> heap) throws MyException
    {
        Value e1value=this.e1.evaluate(symbolTable,heap );
        Value e2value=this.e2.evaluate(symbolTable, heap);
        if(!e1value.getType().equals(new IntType()) || !e2value.getType().equals(new IntType()))
            throw new MyException("Expected two int values");
        Integer e1res=Integer.parseInt(e1value.getVal());
        Integer e2res=Integer.parseInt(e2value.getVal());
        if(op.equals("+"))
            return new IntValue(e1res+e2res);
        if(op.equals("-"))
            return new IntValue(e1res-e2res);
        if(op.equals("*"))
            return new IntValue(e1res*e2res);
        if(op.equals("/"))
            if(e2res==0)
                throw new MyException("Division by zero!");
            else
                return new IntValue(e1res/e2res);
        return new IntValue(0);
    }

    @Override
    public IVarType typecheck(MyIDictionary<String, IVarType> typeEnv) throws MyException {
        IVarType typ1= e1.typecheck(typeEnv);
        IVarType typ2= e2.typecheck(typeEnv);
        if(typ1.equals(new IntType()))
        {
            if(typ2.equals(new IntType()))
            {
                return new IntType();
            } else
                throw new MyException("second operand is not an integer");
        }else
            throw new MyException("first operand is not an integer");
    }

    @Override
    public String toString() {
        String sign="+";
        if(op.equals("+"))
            sign=" + ";
        if(op.equals("-"))
            sign=" - ";
        if(op.equals("*"))
            sign=" * ";
        if(op.equals("/"))
            sign=" / ";
        return "("+this.e1.toString()+sign+this.e2.toString()+")";
    }
}
