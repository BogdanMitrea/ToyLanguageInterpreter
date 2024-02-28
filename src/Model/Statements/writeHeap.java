package Model.Statements;

import Collection.MyIDictionary;
import Collection.MyIHeap;
import Model.Expressions.Expression;
import Model.MyException;
import Model.ProgramState;
import Model.VarType.IVarType;
import Model.VarType.RefType;
import Model.VarType.RefValue;
import Model.VarType.Value;

public class writeHeap implements IStatement{
    Expression expr;
    String var_name;

    public writeHeap()
    {

    }
    public writeHeap(String var_name,Expression e)
    {
        this.var_name=var_name;
        this.expr=e;
    }
    @Override
    public ProgramState execute(ProgramState prgstate) throws MyException {
        MyIDictionary<String, Value> smbltbl=prgstate.getSymbolTable();
        if(!smbltbl.isdefined(var_name))
            throw new MyException("The variable is not defined");
        else
        {
            if(!(smbltbl.get(var_name).getType() instanceof RefType))
            {
                throw new MyException("The variable type is not RefType");
            }
            else
            {
                Value exprval=this.expr.evaluate(smbltbl,prgstate.getheap() );
                Value tablevalue=smbltbl.get(var_name);
                if(!prgstate.getheap().isdefined(((RefValue)(tablevalue)).getAddress()))
                    throw new MyException("The RefValue associated in SymTable is not a key in Heap");
                else
                {
                    if(!(exprval.getType().equals(((RefType)(tablevalue.getType())).getInner())))
                        throw new MyException("The type of the expression value and the location type from the given variable do not match");
                    else
                    {
                        MyIHeap<Value> heap=prgstate.getheap();
                        heap.put(((RefValue)(tablevalue)).getAddress(),exprval);
                        prgstate.setHeap(heap);
                    }
                }

            }
        }
        return null;
    }

    @Override
    public IStatement getType() {
        return new writeHeap();
    }

    public MyIDictionary<String, IVarType> typecheck(MyIDictionary<String, IVarType> typeEnv) throws MyException
    {
        IVarType typevar = typeEnv.get(var_name);
        IVarType typexp = expr.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("WriteHeap stmt: right hand side and left hand side have different types ");
    }

    @Override
    public String toString(){
        return "writeHeap("+this.var_name+","+ this.expr.toString()+")";
    }
}
