package SourceCode.Domain.ADT;

import SourceCode.Domain.MyException;
import SourceCode.Domain.Value.StringValue;
import SourceCode.Domain.Value.Value;

import java.util.Map;
import java.util.Set;

public interface MyIDictionary<K,V> {
    V getValue(K key);
    V put(K key, V value);

    boolean isDefined(K key);

    String toString();
    int size();
    V lookup(K key) throws MyException;


    void update(K id, V val);

    String toStringFileTable();

    boolean verify(StringValue s);

    void deleteEntry(StringValue s);

    MyIDictionary<K,V> deepcopy();

    Map<K, V> getContent();

    Set<Map.Entry<K, V>> getSet();
}
