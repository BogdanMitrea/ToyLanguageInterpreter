package Model.Expressions;

import Collection.MyIDictionary;
import Collection.MyIHeap;
import Model.MyException;
import Model.VarType.IVarType;
import Model.VarType.Value;

public class VarExpression implements Expression{
    String id;
    public VarExpression(String id)
    {
        this.id=id;
    }
    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable, MyIHeap<Value> heap) throws MyException {
        return symbolTable.get(id);
    }

    @Override
    public IVarType typecheck(MyIDictionary<String, IVarType> typeEnv) throws MyException {
        return typeEnv.get(id);
    }

    @Override
    public String toString()
    {
        return this.id;
    }
}
