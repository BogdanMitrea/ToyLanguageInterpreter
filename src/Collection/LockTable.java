package Collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTable implements ILockTable{
    private HashMap<Integer,Integer> mydictionary;
    private int firstfree=0;
    public LockTable()
    {
        mydictionary=new HashMap<>();
    }

    @Override
    public Integer getFirstFree()
    {
        synchronized (this)
        {
            firstfree++;
            return firstfree;
        }
    }

    @Override
    public Integer get(Integer key) {
        synchronized (this) {
            return mydictionary.get(key);
        }

    }

    @Override
    public Integer put(Integer key, Integer value) {
        synchronized (this) {
            return mydictionary.put(key, value);
        }
    }

    @Override
    public int size() {
        synchronized (this) {
            return mydictionary.size();
        }
    }

    @Override
    public HashMap<Integer, Integer> getContent() {
        synchronized (this) {
            return mydictionary;
        }
    }

    @Override
    public void setContent(Map<Integer, Integer> newmap) {
        synchronized (this) {
            mydictionary = (HashMap<Integer, Integer>) newmap;
        }
    }

    @Override
    public boolean isdefined(Integer name) {
        synchronized (this) {
            return mydictionary.containsKey(name);
        }
    }

    @Override
    public void remove(Integer id) {
        synchronized (this) {
            mydictionary.remove(id);
        }
    }

    @Override
    public Collection<Integer> values() {
        synchronized (this) {
            return mydictionary.values();
        }
    }

    @Override
    public String toString(){
        String res="";
        for (Map.Entry<Integer, Integer> entry : mydictionary.entrySet()) {
            res+=entry.getKey()+"->"+entry.getValue();
            res+="\n ";
        }
        return res;
    }
}
