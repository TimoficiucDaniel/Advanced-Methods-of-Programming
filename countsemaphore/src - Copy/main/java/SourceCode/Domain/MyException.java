package SourceCode.Domain;

public class MyException extends Exception {
    private String msg;

    @Override
    public String toString(){
        return msg;
    }

    public MyException(String msg) {
        this.msg = msg;
    }
}
