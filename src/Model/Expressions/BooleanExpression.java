package Model.Expressions;

import Collection.MyIDictionary;
import Collection.MyIHeap;
import Model.MyException;
import Model.VarType.*;

public class BooleanExpression implements Expression{

    private Expression left;
    private Expression right;
    private String op;

    public BooleanExpression(String op,Expression e1,Expression e2)
    {
        this.left=e1;
        this.right=e2;
        this.op=op;
    }
    public Expression getExpression_left() {
        return this.left;
    }

    public Expression getExpression_right() {
        return this.right;
    }

    public String getComparisonOperator() {
        return this.op;
    }
    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable, MyIHeap<Value> heap) throws MyException {
        Value expr1 = left.evaluate(symbolTable, heap);
        Value expr2 = right.evaluate(symbolTable,heap );
        if(!expr1.getType().equals(new IntType()) || !expr2.getType().equals(new IntType()))
            throw new MyException("Int type required for both operands");
        boolean res = switch (op) {
            case "<" -> Integer.parseInt(expr1.getVal()) < Integer.parseInt(expr2.getVal());
            case "<=" -> Integer.parseInt(expr1.getVal()) <= Integer.parseInt(expr2.getVal());
            case ">" -> Integer.parseInt(expr1.getVal()) > Integer.parseInt(expr2.getVal());
            case ">=" -> Integer.parseInt(expr1.getVal()) >= Integer.parseInt(expr2.getVal());
            case "==" -> Integer.parseInt(expr1.getVal()) == Integer.parseInt(expr2.getVal());
            case "!=" -> Integer.parseInt(expr1.getVal()) != Integer.parseInt(expr2.getVal());
            default -> throw new MyException(op + " is not a valid comparison operator.");
        };
        if(res)
            return new BoolValue(true);
        else
            return new BoolValue(false);
    }

    @Override
    public IVarType typecheck(MyIDictionary<String, IVarType> typeEnv) throws MyException {
        IVarType typ1= left.typecheck(typeEnv);
        IVarType typ2= right.typecheck(typeEnv);
        if(typ1.equals(new IntType()))
        {
            if(typ2.equals(new IntType()))
            {
                return new BoolType();
            } else
                throw new MyException("second operand is not an integer");
        }else
            throw new MyException("first operand is not an integer");
    }

    public String toString() {
        return "( " + left.toString() + " " + op + " " + right.toString() + " )";
    }
}
