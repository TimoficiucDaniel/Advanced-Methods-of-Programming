package SourceCode.Domain.ADT;

import SourceCode.Domain.MyException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface ILatch<A,C> {
    public HashMap<A,C> getLatch();
    public void put(A address, C content) throws MyException;
    public int getFree();

    void incFree();
    public C getContent(A address) throws MyException;
    public void update(A address, C content);

    boolean containtsKey(A add);

    void setHeap(HashMap<A, C> heap);

    Map<A,C> getContents();

    Set<Map.Entry<A,C>> getSet();
}
