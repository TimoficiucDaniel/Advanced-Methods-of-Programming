package SourceCode.Repository;

import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;

import java.io.IOException;
import java.util.List;

public interface IRepo {

    PrgState getprg(int id);
    boolean isEmpty();
    List<PrgState> getCrtPrg();

    void setPrgList(List<PrgState> list);

    void addPrg(PrgState prgState);

    String getLogFilePath();

    void logPrgStateExec(PrgState prg) throws MyException, IOException;

    void setLogFilePath(String logFilePath) throws IOException;
}