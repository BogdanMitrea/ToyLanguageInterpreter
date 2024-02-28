package Model.Expressions;

import Collection.MyIDictionary;
import Collection.MyIHeap;
import Model.MyException;
import Model.VarType.*;

public class readHeap implements Expression {

    Expression expr;
    public readHeap(Expression expr)
    {
        this.expr=expr;
    }
    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable, MyIHeap<Value> heap) throws MyException {
        Value exprval=expr.evaluate(symbolTable,heap);
        if(!(exprval.getType() instanceof RefType))
        {
            throw new MyException("RefType required");
        }
        else {
            int addr = ((RefValue) (exprval)).getAddress();
            if (!heap.isdefined(addr))
                throw new MyException("Address is not in the heap table");
            else
                return heap.get(addr);
        }
    }

    @Override
    public IVarType typecheck(MyIDictionary<String, IVarType> typeEnv) throws MyException {
        IVarType typ= expr.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft =(RefType) typ;
            return reft.getInner();
        } else
            throw new MyException("the rH argument is not a Ref Type");
    }


    @Override
    public String toString()
    {
        return "readHeap("+this.expr.toString()+")";
    }
}
