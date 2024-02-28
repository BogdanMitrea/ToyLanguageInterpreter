package Model.Statements;

import Collection.MyIDictionary;
import Collection.MyIStack;
import Model.MyException;
import Model.ProgramState;
import Model.VarType.IVarType;

public class CompoundStatement implements IStatement{
    private IStatement first;
    private IStatement second;

    public CompoundStatement(IStatement s1,IStatement s2)
    {
        this.first=s1;
        this.second=s2;
    }

    public CompoundStatement() {

    }

    public IStatement getSecond()
    {
        return this.second;
    }

    public IStatement getFirst()
    {
        return this.first;
    }


    public MyIDictionary<String, IVarType> typecheck(MyIDictionary<String, IVarType> typeEnv) throws MyException
    {
        return second.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public String toString() {
        return first.toString() + "; " + second.toString();
    }
    @Override
    public ProgramState execute(ProgramState state) {
        MyIStack<IStatement> stk=state.getExecutionStack();
        stk.push(second);
        stk.push(first);
        state.setExecutionStack(stk);
        return null;
    }

    @Override
    public IStatement getType() {
        return new CompoundStatement();
    }
}
