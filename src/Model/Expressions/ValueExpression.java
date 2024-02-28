package Model.Expressions;

import Collection.MyIDictionary;
import Collection.MyIHeap;
import Model.MyException;
import Model.VarType.IVarType;
import Model.VarType.Value;

public class ValueExpression implements Expression{
    Value val;

    public ValueExpression(Value val)
    {
        this.val=val;
    }
    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable, MyIHeap<Value> heap) throws MyException {
        return this.val;
    }

    @Override
    public IVarType typecheck(MyIDictionary<String, IVarType> typeEnv) throws MyException {
        return val.getType();
    }

    @Override
    public String toString()
    {
        return ""+this.val;
    }

}
