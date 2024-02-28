package Model.VarType;

public class StringType implements IVarType{
    @Override
    public boolean equals(Object another)
    {
        return another instanceof StringType;
    }
    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    @Override
    public String toString()
    {
        return "String";
    }
}
