package Collection;
import Model.Expressions.Expression;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface MyIHeap<V> {

    V get(Integer key);
    V put(Integer key, V value);
    String toString();
    int size();
    HashMap<Integer, V> getContent();
    void setContent(Map<Integer,V> newmap);
    boolean isdefined(Integer name);
    void remove(String id);
    Collection<V> values();
    boolean containsValue(V element);
    Integer addValue(V Value);
    Integer getKey(V value);
}
