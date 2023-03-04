package SourceCode.Domain.Type;

import SourceCode.Domain.Value.BooleanValue;
import SourceCode.Domain.Value.Value;

public class BooleanType implements Type{

    public boolean equals(Object another){
        if(another instanceof BooleanType)
            return true;
        else
            return false;
    }

    @Override
    public String toString(){
        return "boolean";
    }
    @Override
    public Value defaultValue() {
        return new BooleanValue(false);
    }
}
