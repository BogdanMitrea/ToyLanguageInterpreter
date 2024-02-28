package Model.Expressions;

import Collection.MyIDictionary;
import Collection.MyIHeap;
import Model.MyException;
import Model.VarType.IVarType;
import Model.VarType.Value;

public interface Expression {
    Value evaluate(MyIDictionary<String, Value> symbolTable, MyIHeap<Value> heap) throws MyException;

    IVarType typecheck(MyIDictionary<String,IVarType> typeEnv) throws MyException;
    String toString();
}
