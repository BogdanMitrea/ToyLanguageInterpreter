package Model.VarType;

public class RefValue implements Value{
    int address;
    IVarType locationType;
    public RefValue(int address,IVarType locationType)
    {
        this.address=address;
        this.locationType=locationType;
    }
    @Override
    public IVarType getType() {
        return new RefType(locationType);
    }
    public int getAddress(){
        return this.address;
    }

    public void setAddress(int newAddres)
    {
        this.address=newAddres;
    }

    @Override
    public String getVal() {
        return null;
    }

    @Override
    public Value deepcopy() {
        return new RefValue(this.address,this.locationType);
    }

    @Override
    public String toString(){
        return "("+address+","+locationType+")";
    }
}
