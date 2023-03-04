package SourceCode.Domain.ADT;

import SourceCode.Domain.MyException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface ISem<A,C> {
    public HashMap<A,C> getSem();
    public void put(A address, C content) throws MyException;
    public int getFree();
    void incFree();
    public C getContent(A address) throws MyException;
    public void update(A address, C content);
    boolean containsKey(A ad);
    void setSem(HashMap<A, C> heap);

    Map<A,C> getContents();

    Set<Map.Entry<A,C>> getSet();
}
