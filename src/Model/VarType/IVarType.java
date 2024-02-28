package Model.VarType;

public interface IVarType {
    boolean equals(Object another);
    Value defaultValue();
    String toString();
}
