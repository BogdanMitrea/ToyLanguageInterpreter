package Model.VarType;

public class IntType implements IVarType{
    @Override
    public boolean equals(Object another)
    {
        if(another instanceof IntType)
            return true;
        else
            return false;
    }

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }

    @Override
    public String toString()
    {
        return "int";
    }
}
