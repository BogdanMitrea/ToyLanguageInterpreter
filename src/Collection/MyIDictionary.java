package Collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
public interface MyIDictionary<K,V> {
    V get(K key);
    V put(K key, V value);
    String toString();
    int size();
    boolean isdefined(K name);
    void remove(String id);
    HashMap<K, V> getContent();
    MyIDictionary<K, V> clone();
    Collection<V> values();
    boolean containsValue(V element);
    K getKey(V value);
}
