package SourceCode.Domain.ADT;

import SourceCode.Domain.MyException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface IBarrier<A,C> {
    public HashMap<A,C> getBarrier();
    public void put(A address, C content) throws MyException;
    public int getCurrentAddress();
    public C getContent(A address) throws MyException;
    public void update(A address, C content);

    void setHeap(HashMap<A, C> heap);

    Map<A,C> getContents();

    Set<Map.Entry<A,C>> getSet();
    public void IncAddr();

    boolean containsKey(A key);
}
