package SourceCode.Domain.ADT;

import SourceCode.Domain.MyException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface ILock<A,C> {
    public HashMap<A,C> getLock();
    public void put(A int1, C int2);
    public int getCurrentAddress();
    public C getContent(A int1);
    public void update(A int1, C int2);

    public boolean contains(A int1);

    void setLock(HashMap<A, C> lock);

    Map<A,C> getContents();

    Set<Map.Entry<A,C>> getSet();
}
