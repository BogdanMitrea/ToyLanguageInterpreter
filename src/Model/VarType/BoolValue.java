package Model.VarType;

public class BoolValue implements Value{
    boolean val;

    public BoolValue(BoolValue v)
    {
        this.val=v.val;
    }
    public BoolValue(boolean v)
    {
        val=v;
    }

    @Override
    public IVarType getType() {
        return new BoolType();
    }

    @Override
    public String getVal() {
        if(val)
            return "true";
        else
            return "false";
    }

    @Override
    public Value deepcopy() {
        return new BoolValue(this.val);
    }

    @Override
    public String toString() {
        if (val)
            return "true";
        else
            return "false";
    }
}
