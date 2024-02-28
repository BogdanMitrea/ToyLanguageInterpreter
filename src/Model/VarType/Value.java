package Model.VarType;

public interface Value {
    IVarType getType();

    String getVal();

    Value deepcopy();

    public String toString();
}
