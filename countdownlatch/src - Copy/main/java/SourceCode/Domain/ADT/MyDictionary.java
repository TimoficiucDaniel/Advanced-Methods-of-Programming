package SourceCode.Domain.ADT;

import SourceCode.Domain.MyException;
import SourceCode.Domain.Value.StringValue;
import SourceCode.Domain.Value.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<K,V> implements MyIDictionary<K,V>{
    private Map<K,V> dictionary;

    public MyDictionary(HashMap<K, V> h) {
        this.dictionary = h;
    }

    @Override
    public String toString(){
        synchronized (this) {
            StringBuilder string = new StringBuilder();
            Set<K> keys = dictionary.keySet();
            for (K key : keys)
                string.append(key).append("-->").append(dictionary.get(key)).append("\n");
            return string.toString();
        }
}

    @Override
    public String toStringFileTable(){
        synchronized (this) {
            StringBuilder string = new StringBuilder();
            Set<K> keys = dictionary.keySet();
            for (K key : keys) {
                string.append(key.toString()).append("\n");
            }
            return string.toString();
        }
    }

    @Override
    public boolean verify(StringValue s) {
        synchronized (this){
        V v = dictionary.get(s);
        return v == null;}
    }

    @Override
    public void deleteEntry(StringValue s) {
        dictionary.remove(s);
    }

    @Override
    public MyIDictionary<K, V> deepcopy() {
        MyIDictionary<K,V> dict = new MyDictionary<>(new HashMap<>());
        for(K k: dictionary.keySet())
        {
            dict.put(k,dictionary.get(k));
        }
        return dict;
    }

    @Override
    public Map<K, V> getContent() {
        return (Map<K, V>) dictionary;
    }

    @Override
    public Set<Map.Entry<K, V>> getSet() {
        synchronized (this){return dictionary.entrySet();}
    }

    @Override
    public V getValue(K key) {
        synchronized (this){return this.dictionary.get(key);}
    }

    @Override
    public V put(K key, V value) {
        synchronized (this){return this.dictionary.put(key,value);}
    }

    @Override
    public boolean isDefined(K key) {
        synchronized (this){
        return dictionary.get(key) != null;}
    }

    @Override
    public int size() {
        synchronized (this) {
            return this.dictionary.size();
        }
    }

    @Override
    public V lookup(K key) throws MyException {
        synchronized (this) {
            V value = dictionary.get(key);
            if (value == null)
                throw new MyException("Variable " + key + " is not defined\n");
            return value;
        }
    }

    @Override
    public void update(K id, V val) {
        synchronized (this) {
            dictionary.remove(id);
            dictionary.put(id, val);
        }
    }


}
