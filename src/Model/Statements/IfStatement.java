package Model.Statements;

import Collection.MyIDictionary;
import Collection.MyIStack;
import Model.Expressions.Expression;
import Model.MyException;
import Model.ProgramState;
import Model.VarType.BoolType;
import Model.VarType.IVarType;

public class IfStatement implements IStatement {

    Expression exp;
    IStatement thenS;
    IStatement elseS;
    public IfStatement(Expression e,IStatement thenS,IStatement elseS)
    {
        exp=e;
        this.thenS=thenS;
        this.elseS=elseS;
    }

    public IfStatement() {

    }

    @Override
    public ProgramState execute(ProgramState prgstate) throws MyException {
        String res=exp.evaluate(prgstate.getSymbolTable(),prgstate.getheap() ).getVal();
        MyIStack<IStatement> stk= prgstate.getExecutionStack();
        if(res.equals("true"))
            stk.push(thenS);
        else
            stk.push(elseS);
        prgstate.setExecutionStack(stk);
        return null;
    }

    @Override
    public IStatement getType() {
        return new IfStatement();
    }

    public MyIDictionary<String, IVarType> typecheck(MyIDictionary<String, IVarType> typeEnv) throws MyException
    {
        IVarType typexp=exp.typecheck(typeEnv);
        if(typexp.equals(new BoolType()))
        {
            thenS.typecheck(typeEnv.clone());
            elseS.typecheck(typeEnv.clone());
            return typeEnv;
        }
        else
            throw  new MyException("The condition of IF has not the type bool");
    }

    @Override
    public String toString()
    {
        return "(IF("+ exp.toString()+") THEN(" +thenS.toString() +")ELSE("+elseS.toString()+"))";
    }
}
