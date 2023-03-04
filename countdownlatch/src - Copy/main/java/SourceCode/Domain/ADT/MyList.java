package SourceCode.Domain.ADT;

import java.util.ArrayList;
import java.util.Iterator;

public class MyList<T> implements MyIList<T>{
    private ArrayList<T> list;

    public MyList(ArrayList<T> a){
        this.list = a;
    }

    @Override
    public boolean remove(T v){
        synchronized (this){
            return this.list.remove(v);
        }
    }

    @Override
    public String toString(){
        synchronized (this){
            StringBuilder string = new StringBuilder();
            for(T s:list)
                string.append(s).append("\n");
            return string.toString();
        }
    }

    @Override
    public int size() {
        synchronized (this){
            return this.list.size();
        }
    }

    @Override
    public void clear() {
        synchronized (this){
            this.list.clear();
        }
    }

    @Override
    public T get(int index) {
        synchronized (this){
            return this.list.get(index);
        }
    }

    @Override
    public boolean add(T e) {
        synchronized (this){
            return this.list.add(e);
        }
    }

    @Override
    public Iterable<T> getAll() {
        synchronized (this){
            return this.list;
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (this){
            return list.isEmpty();
        }
    }

    @Override
    public Iterator<T> getIter() {
        synchronized (this){
            return list.listIterator();
        }
    }
}
