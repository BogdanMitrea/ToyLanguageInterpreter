package Model.VarType;

public class IntValue implements Value{
    int val;
    public IntValue(int v)
    {
        val=v;
    }

    @Override
    public IVarType getType() {
        return new IntType();
    }

    @Override
    public String getVal() {
        return String.valueOf(val);
    }

    @Override
    public String toString(){
        return ""+val;
    }

    @Override
    public Value deepcopy() {
        return new IntValue(this.val);
    }
}
