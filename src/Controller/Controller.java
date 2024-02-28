package Controller;

import Model.MyException;
import Model.ProgramState;
import Model.VarType.RefValue;
import Model.VarType.Value;
import Repository.IRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import Collection.MyHeap;

public class Controller {
    IRepository repository;

    ExecutorService executorService = Executors.newFixedThreadPool(8);

    private MyHeap<ProgramState> indextoprogramstate =new MyHeap<ProgramState>();

    Map<Integer, Value> GarbageCollector(List<Integer> symTableAddr,List<Integer> heapAddr, Map<Integer,Value> heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    List<Integer> getAddrFromHeap(Collection<Value> heapValues){
        return heapValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public void setCurrentProgram(int index)
    {
        this.repository.setCurrentProgram(index);
    }
    public Controller(IRepository repo)
    {
        this.repository=repo;
    }

    public void oneStepForAllPrg(List<ProgramState> programStateList) throws MyException {
            List<ProgramState> prgList = removeCompletedPrg(repository.getProgramStatesList());
            if(!prgList.isEmpty()) {
                ProgramState prgstate = prgList.get(0);
                prgstate.getheap().setContent(GarbageCollector(
                        getAddrFromSymTable(prgstate.getSymbolTable().getContent().values()),
                        getAddrFromHeap(prgstate.getheap().getContent().values()),
                        prgstate.getheap().getContent()));
            }
            boolean done=true;
            for( ProgramState ps:programStateList)
            {
                if(!ps.getExecutionStack().isEmpty())
                    done=false;
            }
            if(done)
                throw new MyException(".");
            programStateList.forEach(prg -> {
                try {
                    repository.logPrgStateExec(prg);
                } catch (MyException e) {
                    throw new RuntimeException(e);
                }
            });
            List<Callable<ProgramState>> callList = programStateList.stream()
                    .map((ProgramState p) -> (Callable<ProgramState>) (() -> {
                        return p.oneStep();
                    }))
                    .collect(Collectors.toList());
            List<ProgramState> newPrgList;
            try {
                newPrgList = this.executorService.invokeAll(callList).stream()
                        .map(future -> {
                            try {
                                return future.get();
                            } catch (InterruptedException | ExecutionException e) {
                                return null;
                            }
                        }).filter(p -> p != null).collect(Collectors.toList());
            } catch (InterruptedException e) {
                throw new MyException(e.getMessage());
            }
            programStateList.addAll(newPrgList);
            programStateList.forEach(prg -> {
                try {
                    repository.logPrgStateExec(prg);
                } catch (MyException e) {
                    throw new RuntimeException(e);
                }
            });
            repository.setProgramStatesList(programStateList);
        for( ProgramState ps:programStateList) {
            if (!indextoprogramstate.containsValue(ps))
                indextoprogramstate.addValue(ps);
        }
    }
    public void allStep() throws MyException
    {
        //remove the completed programs
        List<ProgramState> prgList = removeCompletedPrg(repository.getProgramStatesList());
        try {
            while (prgList.size() > 0) {
                ProgramState prgstate = prgList.get(0);
                prgstate.getheap().setContent(GarbageCollector(
                        getAddrFromSymTable(prgstate.getSymbolTable().getContent().values()),
                        getAddrFromHeap(prgstate.getheap().getContent().values()),
                        prgstate.getheap().getContent()));
                oneStepForAllPrg(prgList);
                prgList = removeCompletedPrg(repository.getProgramStatesList());
            }
        }
        catch (MyException e) {
            throw new MyException(e.getMessage());
        }
        executorService.shutdownNow();
        //HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        //setPrgList of repository in order to change the repository

        // update the repository state
        repository.setProgramStatesList(prgList);
    }
    public IRepository getRepository()
    {
        return  this.repository;
    }
    public List<ProgramState> getPrograms()
    {
        return this.repository.getProgramStatesList();
    }

    public List<ProgramState> removeCompletedPrg(List<ProgramState> inPrgList)
    {
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public MyHeap<ProgramState> getIndextoprogramstate()
    {
        return this.indextoprogramstate;
    }
}
