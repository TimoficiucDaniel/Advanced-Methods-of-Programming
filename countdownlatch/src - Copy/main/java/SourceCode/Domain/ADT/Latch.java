package SourceCode.Domain.ADT;

import SourceCode.Domain.MyException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Latch<A,C> implements ILatch<A,C>{
    HashMap<A,C> latchTable ;
    int add=1;

    public Latch(HashMap<A, C> latchTable) {
        this.latchTable = latchTable;
    }

    @Override
    public HashMap<A, C> getLatch() {
        synchronized (this){
            return latchTable;
        }
    }

    @Override
    public void put(A address, C content)  {
        synchronized (this){
            Lock lock = new ReentrantLock();
            lock.lock();
            latchTable.put(address,content);
            lock.unlock();
        }
    }

    @Override
    public int getFree() {
        synchronized (this){
            return add;
        }
    }

    @Override
    public void incFree() {
        synchronized (this){
            add++;
        }
    }

    @Override
    public C getContent(A address) throws MyException {
        synchronized (this){
            Lock lock = new ReentrantLock();
            lock.lock();
            C c= latchTable.get(address);
            lock.unlock();
            return c;
        }
    }

    @Override
    public void update(A address, C content) {
        synchronized (this){
            Lock lock = new ReentrantLock();
            lock.lock();
            latchTable.replace(address,content);
            lock.unlock();
        }
    }

    @Override
    public boolean containtsKey(A add) {
        synchronized (this){
            Lock lock = new ReentrantLock();
            lock.lock();
            boolean b= latchTable.containsKey(add);
            lock.unlock();
            return b;
        }
    }

    @Override
    public void setHeap(HashMap<A, C> heap) {
        synchronized (this){
            latchTable=heap;
        }
    }

    @Override
    public Map<A, C> getContents() {
        synchronized (this){
            return latchTable;
        }
    }

    @Override
    public Set<Map.Entry<A, C>> getSet() {
        synchronized (this){
            return latchTable.entrySet();
        }
    }

    @Override
    public String toString() {
        synchronized (this) {
            StringBuilder str =new StringBuilder();
            for(Map.Entry<A,C> r : this.getSet())
                str.append(r.getKey().toString()+","+r.getValue().toString()+"\n");
            return str.toString();
        }
    }
}
