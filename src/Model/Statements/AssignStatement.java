package Model.Statements;

import Collection.MyIDictionary;
import Collection.MyIStack;
import Model.Expressions.Expression;
import Model.MyException;
import Model.ProgramState;
import Model.VarType.IVarType;
import Model.VarType.Value;

public class AssignStatement implements IStatement{

    String id;
    Expression expression;


    public AssignStatement(String id,Expression e)
    {
        this.id=id;
        this.expression=e;
    }

    public AssignStatement() {

    }

    @Override
    public ProgramState execute(ProgramState prgstate) throws MyException {
        MyIStack<IStatement> stack= prgstate.getExecutionStack();
        MyIDictionary<String, Value> symboltable= prgstate.getSymbolTable();
        if(symboltable.isdefined(id))
        {
            try {
                Value val = expression.evaluate(symboltable, prgstate.getheap());
                IVarType typId= (symboltable.get(id)).getType();
                if (val.getType(). equals(typId))
                    symboltable.put(id,val);
                else
                    throw new MyException("declared type of variable "+id+" and type of the assigned expression do not match");
            }
            catch (MyException e) {
                throw new MyException(e.getMessage());
            }
        }
        else
            throw new MyException("The variable " + id + " was not declared before");
        return null;
    }

    @Override
    public IStatement getType() {
        return new AssignStatement();
    }

    public MyIDictionary<String, IVarType> typecheck(MyIDictionary<String, IVarType> typeEnv) throws MyException
    {
        IVarType varType=typeEnv.get(id);
        IVarType exptype=expression.typecheck(typeEnv);
        if (varType.equals(exptype))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types ");
    }

    @Override
    public String toString() {
        return id+"="+expression.toString();
    }
}
