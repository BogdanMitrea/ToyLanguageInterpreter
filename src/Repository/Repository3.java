package Repository;

import Model.MyException;
import Model.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository3 implements IRepository{
    List<ProgramState> programStateList;
    String logFilePath;
    public Repository3(ProgramState program_state,String logFilePath)
    {
        this.programStateList= new ArrayList<>();
        this.programStateList.add(program_state);
        this.logFilePath=logFilePath;
    }
    @Override
    public void setCurrentProgram(int index) {

    }

    @Override
    public List<ProgramState> getProgramStatesList() {
        return this.programStateList;
    }

    @Override
    public void setProgramStatesList(List<ProgramState> newlist) {
        this.programStateList=newlist;
    }

    @Override
    public void logPrgStateExec(ProgramState prgState) throws MyException {
        try {
            PrintWriter logfile=new PrintWriter(new BufferedWriter(new FileWriter(logFilePath,true)));
            ProgramState.setId(prgState.getStateid());
            logfile.write(prgState.toString());
            logfile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProgramState getCurrentProgramState() {
        return programStateList.get(0);
    }
}

