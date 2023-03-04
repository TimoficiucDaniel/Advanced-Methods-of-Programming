package SourceCode.Domain.Value;

import SourceCode.Domain.Type.BooleanType;
import SourceCode.Domain.Type.Type;

public class BooleanValue implements Value{
    private boolean val;
    public BooleanValue(boolean v){
        val = v;
    }

    public boolean getVal(){
        return val;
    }

    @Override
    public String toString(){
        if(val)
            return "true";
        else
            return "false";
    }

    @Override
    public Value deepCopy() {
        return new BooleanValue(this.val);
    }

    @Override
    public Type getType() {
        return new BooleanType();
    }

    public boolean equals(Object another){
        if(another instanceof BooleanValue)
            return true;
        else
            return false;
    }
}
