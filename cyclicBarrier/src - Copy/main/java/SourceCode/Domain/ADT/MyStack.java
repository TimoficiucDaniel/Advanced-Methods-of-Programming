package SourceCode.Domain.ADT;

import java.util.Stack;
import java.util.List;

public class MyStack<T> implements  MyIStack<T>{
    private Stack<T> stack;



    public MyStack(Stack<T> s) {
        this.stack = s;
    }


    @Override
    public String toString(){
        synchronized (this){
            StringBuilder string= new StringBuilder();
            Stack<T> copie = (Stack<T>)stack.clone();
            while(!copie.isEmpty()) {
                T s = copie.pop();
                string.append(s).append("\n");
            }
            return string.toString();
        }
    }
    @Override
    public T pop() {
        synchronized (this){
            return stack.pop();
        }
    }

    @Override
    public void push(T v) {
        synchronized (this){
            stack.push(v);
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (this){
            return stack.isEmpty();
        }
    }

    @Override
    public List<T> getAll() {
        synchronized (this){
            return stack;
        }
    }
}
