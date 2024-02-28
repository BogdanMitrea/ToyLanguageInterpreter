package Collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface ILockTable{

    Integer get(Integer key);

    Integer getFirstFree();
    Integer put(Integer key, Integer value);
    String toString();
    int size();
    HashMap<Integer, Integer> getContent();
    void setContent(Map<Integer,Integer> newmap);
    boolean isdefined(Integer name);
    void remove(Integer id);
    Collection<Integer> values();
}
