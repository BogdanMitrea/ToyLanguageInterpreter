package Repository;

import Model.MyException;
import Model.ProgramState;

import java.util.List;

public interface IRepository {
    void setCurrentProgram(int index);
    List<ProgramState> getProgramStatesList();

    void setProgramStatesList(List<ProgramState> newlist);

    void logPrgStateExec(ProgramState prgState) throws MyException;
    ProgramState getCurrentProgramState();
}
