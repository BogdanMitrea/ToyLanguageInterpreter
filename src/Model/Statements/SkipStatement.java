package Model.Statements;

import Collection.MyIDictionary;
import Model.MyException;
import Model.ProgramState;
import Model.VarType.IVarType;

public class SkipStatement implements IStatement{

    public SkipStatement()
    {

    }
    @Override
    public ProgramState execute(ProgramState prgstate) throws MyException {
        return null;
    }

    @Override
    public IStatement getType() {
        return new SkipStatement();
    }

    @Override
    public MyIDictionary<String, IVarType> typecheck(MyIDictionary<String, IVarType> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString()
    {
        return "Skip";
    }
}
