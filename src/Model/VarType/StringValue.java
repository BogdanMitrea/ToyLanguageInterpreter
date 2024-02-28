package Model.VarType;

public class StringValue implements Value{
    String str;
    public StringValue(String str)
    {
        this.str=str;
    }
    @Override
    public IVarType getType() {
        return new StringType();
    }
    @Override
    public String getVal() {
        return str;
    }
    @Override
    public Value deepcopy() {
        return new StringValue(this.str);
    }
    @Override
    public String toString(){
        return str;
    }

}
