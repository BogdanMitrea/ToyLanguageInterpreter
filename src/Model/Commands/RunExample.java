package Model.Commands;

import Controller.Controller;
import Model.MyException;

public class RunExample extends Command{
    private Controller ctrl;
    public RunExample(String key, String desc, Controller ctrl) {
        super(key,desc);
        this.ctrl = ctrl;
    }
    @Override
    public void execute() {
        try{
            ctrl.allStep(); }
        catch(MyException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
