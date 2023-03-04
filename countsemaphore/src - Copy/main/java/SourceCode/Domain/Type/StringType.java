package SourceCode.Domain.Type;

import SourceCode.Domain.Value.StringValue;
import SourceCode.Domain.Value.Value;

public class StringType implements Type{

    public boolean equals(Object another){
        if(another instanceof StringType)
            return true;
        else
            return false;
    }


    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    @Override
    public String toString() {
        return "string";
    }
}
