package Model.Statements;

import Collection.MyIDictionary;
import Collection.MyIStack;
import Model.Expressions.Expression;
import Model.MyException;
import Model.ProgramState;
import Model.VarType.BoolType;
import Model.VarType.IVarType;

public class whileStatement implements IStatement{
    Expression expr;
    IStatement statement;

    public whileStatement()
    {

    }
    public whileStatement(Expression e,IStatement stmt)
    {
        this.expr=e;
        this.statement=stmt;
    }

    @Override
    public ProgramState execute(ProgramState prgstate) throws MyException {
        MyIStack<IStatement> exestack= prgstate.getExecutionStack();
        if((expr.evaluate(prgstate.getSymbolTable(), prgstate.getheap())).getType() instanceof BoolType) {
            if (expr.evaluate(prgstate.getSymbolTable(),prgstate.getheap() ).getVal()=="true")
            {
                exestack.push(this);
                exestack.push(this.statement);
                prgstate.setExecutionStack(exestack);
                return null;
            }
        }
        else
        {
            throw new MyException("Bool value expected.");
        }
        return null;
    }

    @Override
    public IStatement getType() {
        return new whileStatement();
    }

    public MyIDictionary<String, IVarType> typecheck(MyIDictionary<String, IVarType> typeEnv) throws MyException
    {
        IVarType typexp=expr.typecheck(typeEnv);
        if(typexp.equals(new BoolType()))
        {
            statement.typecheck((MyIDictionary<String, IVarType>) typeEnv.clone());
            return typeEnv;
        }
        else
            throw  new MyException("The condition of While has not the type bool");
    }

    @Override
    public String toString(){
        return "while("+expr.toString()+")" + statement.toString();
    }
}
