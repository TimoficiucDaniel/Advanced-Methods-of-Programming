package SourceCode.View;
import SourceCode.Controller.Controller;
import SourceCode.Domain.MyException;

import java.io.IOException;

public class RunExample extends Command
{
    private Controller ctr;
    public RunExample(String key, String desc,Controller ctr)
    {
        super(key, desc);
        this.ctr=ctr;
    }

    @Override
    public void execute()
    {
        ctr.allStep();
    }
}