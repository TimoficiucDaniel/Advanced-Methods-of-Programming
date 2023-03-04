package SourceCode.Domain.ADT;

import SourceCode.Domain.MyException;
import SourceCode.Domain.Statements.IStmt;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IProcTable<A,C> {
    public HashMap<A, C> getProc();
    public void put(A address, C content) throws MyException;
    public int getCurrentAddress();
    public C getContent(A address) throws MyException;
    public void update(A address, C content);

    void setProc(HashMap<A, C> heap);
    boolean isDefined(A address);

    Map<A,C> getContents();

    Set<Map.Entry<A,C>> getSet();
}
