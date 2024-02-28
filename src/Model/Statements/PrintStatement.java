package Model.Statements;

import Collection.MyIDictionary;
import Model.Expressions.Expression;
import Model.MyException;
import Model.ProgramState;
import Model.VarType.IVarType;
import Model.VarType.Value;

public class PrintStatement implements IStatement{
    Expression expression;
    public PrintStatement()
    {

    }
    public PrintStatement(Expression expression)
    {
        this.expression=expression;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symboltable=state.getSymbolTable();
        state.addOutput(expression.evaluate(symboltable, state.getheap()));
        return null;
    }

    @Override
    public IStatement getType() {
        return new PrintStatement();
    }

    public MyIDictionary<String, IVarType> typecheck(MyIDictionary<String, IVarType> typeEnv) throws MyException
    {
        expression.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}
