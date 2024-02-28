package Collection;

import java.util.ArrayList;

public interface MyIList<T> {
    int size();
    boolean isEmpty();
    public ArrayList<T> getArrayList();
    boolean add(T e);
    void clear();
    T get(int index);
    String toString();
}
