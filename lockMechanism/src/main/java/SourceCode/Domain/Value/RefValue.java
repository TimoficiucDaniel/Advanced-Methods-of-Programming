package SourceCode.Domain.Value;

import SourceCode.Domain.Type.RefType;
import SourceCode.Domain.Type.Type;

public class RefValue implements Value{
    int address;
    Type locationType;

    public RefValue(int address, Type locationType) {
        this.address=address;
        this.locationType=locationType;
    }

    int getAddr() {return address;}

    public int getAddress() {
        return address;
    }

    public Type getLocationType() {
        return locationType;
    }

    public Type getType()
    { return new RefType(locationType);}

    @Override
    public Value deepCopy() {
        return new RefValue(address,locationType);
    }

    @Override
    public String toString() {
        return "( " + address + ", " + locationType.toString() + " )";
    }
}
