package SourceCode.Domain.Value;

import SourceCode.Domain.Type.IntType;
import SourceCode.Domain.Type.Type;

public class IntValue implements Value{
    private int val;
    public IntValue(int v){
        val = v;
    }

    public int getVal() {
        return val;

    }

    public String toString(){
        return String.valueOf(val);
    }

    @Override
    public Value deepCopy() {
        return new IntValue(this.val);
    }

    @Override
    public Type getType(){
        return new IntType();
    }

    public boolean equals(Object another){
        if(another instanceof IntValue)
            return true;
        else
            return false;
    }
}
