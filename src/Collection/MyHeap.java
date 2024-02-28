package Collection;

import Model.Expressions.Expression;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyHeap<V> implements MyIHeap<V> {
    private HashMap<Integer,V> mydictionary;
    Integer firstfree=0;

    public MyHeap(){
        this.firstfree=0;
        this.mydictionary=new HashMap<>();
    }

    public HashMap<Integer, V> getContent() {
        return mydictionary;
    }

    public void setContent(Map<Integer, V> newmap)
    {
        this.mydictionary=(HashMap<Integer, V>)newmap;
    }

    public V get(Integer key){
        return mydictionary.get(key);
    }
    @Override
    public Integer getKey(V value){
        for(Integer key : mydictionary.keySet()){
            if(mydictionary.get(key).equals(value))
                return key;
        }
        return null;
    }
    @Override
    public V put(Integer key, V value){
        return mydictionary.put(key, value);
    }
    @Override
    public String toString(){
        String res="";
        for (Map.Entry<Integer, V> entry : mydictionary.entrySet()) {
            res+=entry.getKey()+"->"+entry.getValue();
            res+="\n ";
        }
        return res;
    }

    @Override
    public Integer addValue(V Value)
    {
        firstfree+=1;
        mydictionary.put(firstfree,Value);
        return firstfree;
    }
    @Override
    public int size() {
        return mydictionary.size();
    }
    @Override
    public boolean isdefined(Integer name) {
        return mydictionary.containsKey(name);
    }
    @Override
    public void remove(String id){
        mydictionary.remove(id);
    }
    @Override
    public Collection<V> values() {
        return mydictionary.values();
    }
    @Override
    public boolean containsValue(V element) {
        return mydictionary.containsValue(element);
    }
}
