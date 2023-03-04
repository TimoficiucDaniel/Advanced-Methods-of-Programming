package SourceCode.Domain.Value;

import SourceCode.Domain.Type.Type;

public interface Value{
    Type getType();
    String toString();

    Value deepCopy();

}
