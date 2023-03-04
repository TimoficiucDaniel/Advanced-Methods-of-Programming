package SourceCode.Domain.ADT;

import SourceCode.Domain.MyException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Sem<A,C> implements ISem<A,C>{
    HashMap<A,C> sem;

    private int ad=1;
    public Sem(HashMap<A, C> sem) {
        this.sem = sem;
    }

    @Override
    public HashMap<A, C> getSem() {
        synchronized (this){
            return sem;
        }
    }

    @Override
    public void put(A address, C content) throws MyException {
        synchronized (this){
            sem.put(address,content);
        }
    }

    @Override
    public int getFree() {
        synchronized (this){
            return ad;
        }
    }
    @Override
    public void incFree(){
        synchronized (this){
            ad++;
        }
    }

    @Override
    public C getContent(A address) {
        synchronized (this){
            return sem.get(address);
        }
    }

    @Override
    public void update(A address, C content) {
        synchronized (this){
            Lock lock = new ReentrantLock();
            lock.lock();
            sem.replace(address,content);
            lock.unlock();
        }
    }

    @Override
    public boolean containsKey(A ad) {
        synchronized (this){
            Lock lock = new ReentrantLock();
            lock.lock();
            boolean b= sem.containsKey(ad);
            lock.unlock();
            return b;
        }
    }

    @Override
    public void setSem(HashMap<A, C> heap) {
        synchronized (this){
            sem = heap;
        }
    }

    @Override
    public Map<A, C> getContents() {
        synchronized (this){
            return sem;
        }
    }

    @Override
    public Set<Map.Entry<A, C>> getSet() {
        synchronized (this){
            return sem.entrySet();
        }
    }

    @Override
    public String toString() {
        synchronized (this){
            StringBuilder str = new StringBuilder();
            for(Map.Entry<A,C> r: this.getSet())
                str.append(r.getKey()+","+r.getValue()+"\n");
            return str.toString();
        }
    }
}
