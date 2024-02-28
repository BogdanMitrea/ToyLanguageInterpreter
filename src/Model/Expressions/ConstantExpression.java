package Model.Expressions;

import Collection.MyIDictionary;
import Collection.MyIHeap;
import Model.MyException;
import Model.VarType.IVarType;
import Model.VarType.Value;

public class ConstantExpression implements Expression{
    private Value nr;
    public ConstantExpression(Value nr)
    {
        this.nr=nr;
    }
    public Value getNr()
    {
        return this.nr;
    }
    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable, MyIHeap<Value> heap) throws MyException {
        return this.nr;
    }
    @Override
    public IVarType typecheck(MyIDictionary<String, IVarType> typeEnv) throws MyException {
        return nr.getType();
    }
    @Override
    public String toString() {
        return this.nr.toString();
    }
}
