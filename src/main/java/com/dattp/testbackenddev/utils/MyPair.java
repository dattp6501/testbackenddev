package com.dattp.testbackenddev.utils;

public class MyPair <K,V> {
    private K first;
    private V second;
    public MyPair() {
        super();
    }
    public MyPair(K first, V second){
        this.first = first;
        this.second = second;
    }
    public K getFirst() {
        return first;
    }
    public void setFirst(K first) {
        this.first = first;
    }
    public V getSecond() {
        return second;
    }
    public void setSecond(V second) {
        this.second = second;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((first == null) ? 0 : first.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof MyPair))
            return false;
        MyPair other = (MyPair) obj;
        if (first == null) {
            if (other.first != null)
                return false;
        } else if (!first.equals(other.first))
            return false;
        return true;
    }
}
