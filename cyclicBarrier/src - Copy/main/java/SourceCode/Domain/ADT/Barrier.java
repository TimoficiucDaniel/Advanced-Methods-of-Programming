package SourceCode.Domain.ADT;

import SourceCode.Domain.MyException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Barrier<A,C> implements IBarrier<A,C>{

    HashMap<A,C> barriertable;
    int freeAdrr =1;

    public Barrier(HashMap<A, C> barriertable) {
        this.barriertable = barriertable;
    }

    @Override
    public HashMap<A, C> getBarrier() {
        synchronized (this){
            return barriertable;
        }
    }

    @Override
    public void put(A address, C content) throws MyException {
        synchronized (this){
            barriertable.put(address,content);
        }
    }

    @Override
    public int getCurrentAddress() {
        synchronized (this){
            return freeAdrr;
        }
    }

    @Override
    public void IncAddr(){
        synchronized (this){
            freeAdrr++;
        }
    }

    @Override
    public boolean containsKey(A key) {
        synchronized (this){
            return barriertable.containsKey(key);
        }
    }

    @Override
    public C getContent(A address)  {
        synchronized (this){
            return barriertable.get(address);
        }
    }

    @Override
    public void update(A address, C content) {
        synchronized (this){
            barriertable.replace(address,content);
        }
    }

    @Override
    public void setHeap(HashMap<A, C> heap) {
        synchronized (this){
            barriertable=heap;
        }
    }

    @Override
    public Map<A, C> getContents() {
        synchronized (this){
            return barriertable;
        }
    }

    @Override
    public Set<Map.Entry<A, C>> getSet() {
        synchronized (this){
            return barriertable.entrySet();
        }
    }

    @Override
    public String toString() {
        synchronized (this) {
            StringBuilder str = new StringBuilder();
            for( Map.Entry<A,C> r: barriertable.entrySet())
                str.append(r.getKey() +"--"+r.getValue());
            return str.toString();
        }
    }
}
