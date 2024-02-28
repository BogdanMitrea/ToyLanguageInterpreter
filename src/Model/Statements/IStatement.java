package Model.Statements;

import Collection.MyIDictionary;
import Model.ProgramState;
import Model.MyException;
import Model.VarType.IVarType;

public interface IStatement {
    ProgramState execute(ProgramState prgstate) throws MyException;

    IStatement getType();
    MyIDictionary<String, IVarType> typecheck(MyIDictionary<String,IVarType> typeEnv) throws MyException;
    String toString();
}
