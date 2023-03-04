package SourceCode.Domain.ADT;

import SourceCode.Domain.MyException;
import SourceCode.Domain.Statements.IStmt;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProcTable<A,C> implements IProcTable<A,C>{
    HashMap<A,C> proctable;

    public ProcTable(HashMap<A, C> proctable) {
        this.proctable = proctable;
    }

    @Override
    public HashMap<A, C> getProc() {
        synchronized (this){
            return proctable;
        }
    }

    @Override
    public void put(A address, C content)  {
        synchronized (this){
            proctable.put(address,content);
        }
    }

    @Override
    public int getCurrentAddress() {
        return 0;
    }

    @Override
    public C getContent(A address) throws MyException {
        synchronized (this){
            return proctable.get(address);
        }
    }

    @Override
    public void update(A address, C content) {
        synchronized (this){
            if(proctable.containsKey(address))
                proctable.replace(address,content);
        }
    }

    @Override
    public void setProc(HashMap<A, C> heap) {
        synchronized (this){
            proctable= heap;
        }
    }

    @Override
    public boolean isDefined(A address) {
        synchronized (this){
            return proctable.containsKey(address);
        }
    }

    @Override
    public Map<A, C> getContents() {
        synchronized (this){
            return proctable;
        }
    }

    @Override
    public Set<Map.Entry<A, C>> getSet() {
        synchronized (this){
            return proctable.entrySet();
        }
    }
}
