package SourceCode.Domain.ADT;

import SourceCode.Domain.MyException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Heap<A,C> implements IHeap<A,C>{
    private HashMap<A,C> dictionary;
    private Stack<Integer>  addresses;
    private int index;

    @Override
    public HashMap<A, C> getHeap() {
        synchronized (this){return dictionary;}
    }

    public void setHeap(HashMap<A,C> newHeap)
    {synchronized (this){
        dictionary = newHeap;
    }}

    @Override
    public Map<A, C> getContents() {
        synchronized (this){
        return dictionary;}
    }

    @Override
    public Set<Map.Entry<A, C>> getSet() {
        synchronized (this) {
            return dictionary.entrySet();
        }
    }


    public Heap(HashMap<A, C> dictionary) {
        this.dictionary = dictionary;
        addresses = new Stack<>();
        index=1;
    }

    @Override
    public void put(A address, C content) {
        synchronized (this){
        dictionary.put(address,content);
        if(addresses.empty())
            index=index+1;
        else
            index=addresses.pop();
    }}


    @Override
    public int getCurrentAddress() {
        synchronized (this){
        return index;}
    }

    @Override
    public C getContent(A address) {
        synchronized (this){
            return dictionary.get(address);
        }
    }

    @Override
    public void update(A address, C content) {
        synchronized (this){
            dictionary.remove(address);
            dictionary.put(address,content);
        }
    }

    @Override
    public String toString() {
        synchronized (this){
            StringBuilder text = new StringBuilder();
            for(A key: dictionary.keySet())
                text.append(key).append(" : ").append(dictionary.get(key)).append("\n");
            return text.toString();
        }
    }
}
