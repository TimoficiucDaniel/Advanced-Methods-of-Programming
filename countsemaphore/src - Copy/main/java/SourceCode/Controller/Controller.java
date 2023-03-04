package SourceCode.Controller;

import SourceCode.Domain.MyException;
import SourceCode.Domain.PrgState;
import SourceCode.Domain.Value.RefValue;
import SourceCode.Domain.Value.Value;
import SourceCode.Repository.IRepo;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


public class Controller {
    public IRepo repo;

    private ExecutorService executor;
    List<PrgState> prgList;


    public Controller(IRepo repo) {
        this.repo = repo;
        executor = Executors.newFixedThreadPool(6);
        prgList = removeCompletedPrg(repo.getCrtPrg());
    }

    public int getnrOfPrg(){
        return repo.getCrtPrg().size();
    }


    public List<PrgState> getCrtPrg(){
        return repo.getCrtPrg();
    }
    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream().filter(p->p.isNotCompleted()).collect(Collectors.toList());
    }
    private Map<Integer, Value> GarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer, Value> heap) {
        return heap.entrySet().stream().filter(e -> (symTableAddr.contains(e.getKey()) ||
                heapAddr.contains(e.getKey()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    private List<Integer> getAddrFromHeap(Collection<Value> heapValues) {
        return heapValues.stream().filter(v -> v instanceof RefValue).map(v -> {
            RefValue v1 = (RefValue) v;
            return v1.getAddress();
        }).collect(Collectors.toList());
    }
    public void setRepoFilePath(String logFilePath) {
        try {
            this.repo.setLogFilePath(logFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void oneStepForAllPrgGui(List<PrgState> list) {
        list.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        List<Callable<PrgState>> callList = list.stream().map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
                .collect(Collectors.toList());
        List<Pair> newPrgList = null;
        try{
        newPrgList = executor.invokeAll(callList).stream().map(future -> {
            try {
                return new Pair(future.get(),null);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).filter(p -> p.first != null || p.second != null).collect(Collectors.toList());
        } catch (InterruptedException e){
            System.out.println("Could not invoke the callables");
        }
        list.addAll(newPrgList.stream().map(pair -> pair.first).collect(Collectors.toList()));
        list.forEach(prg-> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        repo.setPrgList(list);
    }

    public void allStepGui(){
        prgList = removeCompletedPrg(repo.getCrtPrg());
        if(prgList.size() > 0 ){
            PrgState prg = prgList.get(0);
            oneStepForAllPrgGui(prgList);
            prg.getHeap().setHeap((HashMap<Integer, Value>) GarbageCollector( getAddrFromSymTable(prg.getSymTable().getContent().values()),
                    getAddrFromHeap(prg.getHeap().getContents().values()),prg.getHeap().getContents()));
            repo.setPrgList(prgList);
        }
        else {
            executor.shutdownNow();
            repo.setPrgList(prgList);
        }
    }

    public void oneStepForAllPrg(List<PrgState> list) {
        list.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        List<Callable<PrgState>> callList = list.stream().map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
                .collect(Collectors.toList());
        List<Pair> newPrgList = null;
        try{
            newPrgList = executor.invokeAll(callList).stream().map(future -> {
                try {
                    return new Pair(future.get(),null);
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).filter(p -> p.first != null || p.second != null).collect(Collectors.toList());
        } catch (InterruptedException e){
            System.out.println("Could not invoke the callables");
        }
        list.addAll(newPrgList.stream().map(pair -> pair.first).collect(Collectors.toList()));
        list.forEach(prg-> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        repo.setPrgList(list);
    }

    public void allStep(){
        executor = Executors.newFixedThreadPool(6);
        List<PrgState> prgList = removeCompletedPrg(repo.getCrtPrg());
        while(prgList.size() > 0){
            PrgState prg = prgList.get(0);
            prg.getHeap().setHeap((HashMap<Integer, Value>) GarbageCollector( getAddrFromSymTable(prg.getSymTable().getContent().values()),
                    getAddrFromHeap(prg.getHeap().getContents().values()),prg.getHeap().getContents()));
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getCrtPrg());
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }

    public void addPrg(PrgState prgState) {
        repo.addPrg(prgState);
    }
}
