package Model.Statements;

import Collection.MyIDictionary;
import Collection.MyIStack;
import Model.Expressions.BooleanExpression;
import Model.Expressions.ConstantExpression;
import Model.Expressions.Expression;
import Model.Expressions.VarExpression;
import Model.MyException;
import Model.ProgramState;
import Model.VarType.IVarType;
import Model.VarType.IntType;
import Model.VarType.IntValue;

public class forStatement implements IStatement{

    String var;
    Expression exp1;
    Expression exp2;
    Expression exp3;

    IStatement stmt;
    public forStatement()
    {

    }
    public forStatement(String var, Expression e1,Expression e2,Expression e3,IStatement stmt)
    {
        this.var=var;
        this.exp1=e1;
        this.exp2=e2;
        this.exp3=e3;
        this.stmt=stmt;
    }
    @Override
    public ProgramState execute(ProgramState prgstate) throws MyException {
        MyIStack<IStatement> prgstack= prgstate.getExecutionStack();
        prgstack.push(new CompoundStatement(new VarDeclStatement(var,new IntType()), new CompoundStatement(
                new AssignStatement(var,exp1),
                        new whileStatement(new BooleanExpression("<",new VarExpression(var),exp2),new CompoundStatement(stmt,new AssignStatement(var,exp3)))
        )));
        return null;
    }

    @Override
    public IStatement getType() {
        return new forStatement();
    }

    @Override
    public MyIDictionary<String, IVarType> typecheck(MyIDictionary<String, IVarType> typeEnv) throws MyException {
        if(typeEnv.get(var).equals(new IntType()) && exp1.typecheck(typeEnv).equals(new IntType()) && exp2.typecheck(typeEnv).equals(new IntType()) && exp3.typecheck(typeEnv).equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("All the expressions in the for statement must be integers");
    }

    @Override
    public String toString(){
        return "for("+var+"="+exp1.toString()+";"+var+"<"+exp2.toString()+";"+var+"="+exp3.toString()+")";
    }
}
