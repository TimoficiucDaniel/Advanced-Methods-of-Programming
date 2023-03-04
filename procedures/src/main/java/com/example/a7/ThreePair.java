package com.example.a7;

public class ThreePair<T1,T2,T3> {
    T1 first;
    T2 second;
    T3 third;

    public T1 getFirst() {
        return first;
    }

    public T2 getSecond() {
        return second;
    }

    public T3 getThird() {
        return third;
    }

    public ThreePair(T1 first, T2 second, T3 third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }
}
