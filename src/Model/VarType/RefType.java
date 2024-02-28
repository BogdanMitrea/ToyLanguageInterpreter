package Model.VarType;

public class RefType implements IVarType{
    IVarType inner;
    public RefType(IVarType inner) {this.inner=inner;}
    public IVarType getInner() {return inner;}
    public boolean equals(Object another){
        if (another instanceof RefType)
            return inner.equals(((RefType) another).getInner());
        else
            return false;
    }
    public String toString() { return "Ref(" +inner.toString()+")";}
    public Value defaultValue() { return new RefValue(0,inner);}
}
