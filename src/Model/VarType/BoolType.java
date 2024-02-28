package Model.VarType;

public class BoolType implements IVarType{
    @Override
    public boolean equals(Object another)
    {
        if(another instanceof BoolType)
            return true;
        else
            return false;
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(true);
    }

    @Override
    public String toString()
    {
        return "bool";
    }
}
