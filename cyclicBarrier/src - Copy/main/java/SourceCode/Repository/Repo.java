package SourceCode.Repository;

import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repo implements IRepo{
    private List<PrgState> currentProgramList;
    private String logFilePath;

    public Repo(List<PrgState> currentProgramList) {
        this.currentProgramList = currentProgramList;
        this.logFilePath = "";
    }

    @Override
    public PrgState getprg(int id){
        for( PrgState r : currentProgramList )
            if(id == r.getId_thread())
                return r;
        return  null;
    }
    public Repo(PrgState currentProgramList, String logFilePath) {
        this.currentProgramList = new ArrayList<>();
        this.currentProgramList.add(currentProgramList);
        this.setLogFilePath(logFilePath);
    }

    @Override
    public boolean isEmpty() {
        return currentProgramList.isEmpty();
    }

    @Override
    public List<PrgState> getCrtPrg() {
        return currentProgramList;
    }

    @Override
    public void setPrgList(List<PrgState> list) {
        this.currentProgramList = list;
    }

    @Override
    public void addPrg(PrgState state) {
        currentProgramList.add(state);
    }

    @Override
    public String getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = "D:\\projects\\cyclicBarrier\\src\\main\\java\\SourceCode\\Repository\\"+logFilePath;
        File f =  new File(this.logFilePath);
        f.delete();
        //this.logFilePath = logFilePath+".txt";
    }

    @Override
    public void logPrgStateExec(PrgState prg) throws IOException,MyException {
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        writer.print("");
        writer.print(prg.toString());
        writer.close();
    }
}

