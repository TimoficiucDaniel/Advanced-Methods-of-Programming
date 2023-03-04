package SourceCode.Domain.ADT;

import java.util.Iterator;

public interface MyIList<T>{
    int size();
    void clear();
    T get(int index);

    boolean remove(T v);

    boolean add(T e);

    String toString();
    Iterable<T> getAll();

    boolean isEmpty();

    Iterator<T> getIter();
}
