package SourceCode.Domain.Type;

import SourceCode.Domain.Value.RefValue;
import SourceCode.Domain.Value.Value;

public class RefType implements Type{

    Type inner;
    public RefType(Type inner) {this.inner=inner;}
    public Type getInner() {return inner;}
    public boolean equals(Object another){
        if (another instanceof  RefType)
            return inner.equals(((RefType) another).getInner());
        else
            return false;
    }
    @Override
    public String toString() { return "Ref(" +inner.toString()+")";}
    @Override
    public Value defaultValue() { return new RefValue(0,inner);}
}
