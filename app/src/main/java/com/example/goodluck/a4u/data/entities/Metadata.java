package com.example.goodluck.a4u.data.entities;


public class Metadata {
    private long count;
    private String next;
    private String previous;

    public Metadata(long count, String next, String previous) {
        this.count = count;
        this.next = next;
        this.previous = previous;

    }

    public long getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }
}
