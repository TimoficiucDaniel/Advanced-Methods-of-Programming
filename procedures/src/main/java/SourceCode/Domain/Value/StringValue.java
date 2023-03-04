package SourceCode.Domain.Value;

import SourceCode.Domain.Type.StringType;
import SourceCode.Domain.Type.Type;

public class StringValue implements Value{

    private String val;
    public StringValue(String val){
        this.val = val;
    }

    public String getVal(){
        return val;
    }

    public String defaultValue(){
        return "";
    }
    @Override
    public String toString(){
        return val;
    }

    @Override
    public Value deepCopy() {
        return new StringValue(this.val);
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public boolean equals(Object another){
        if(another instanceof StringValue)
            return false;
        else
            return true;
    }
}
