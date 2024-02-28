package Collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Enumeration;
public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    private HashMap<K, V> mydictionary;

    public MyDictionary(){
        mydictionary = new HashMap<K,V>();
    }

    @Override
    public V get(K key){
        return mydictionary.get(key);
    }

    public HashMap<K, V> getContent()
    {
        return mydictionary;
    }

    @Override
    public MyIDictionary<K, V> clone() {
        MyIDictionary<K, V> clonedDictionary = new MyDictionary<K,V>();
        // Cloning the content of the dictionary
        for (Map.Entry<K, V> entry : mydictionary.entrySet()) {
            clonedDictionary.put(entry.getKey(), entry.getValue());
        }
        return clonedDictionary;
    }

    @Override
    public K getKey(V value){
        for(K key : mydictionary.keySet()){
            if(mydictionary.get(key).equals(value))
                return key;
        }
        return null;
    }

    @Override
    public V put(K key, V value){
        return mydictionary.put(key, value);
    }

    @Override
    public String toString(){
        String res="";
        for (Map.Entry<K, V> entry : mydictionary.entrySet()) {
            res+=entry.getKey()+": "+entry.getValue();
            res+="\n ";
        }
        return res;
    }

    @Override
    public int size() {
        return mydictionary.size();
    }

    @Override
    public boolean isdefined(K name) {
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
