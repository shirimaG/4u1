package com.example.goodluck.a4u.data.entities.product;


import com.example.goodluck.a4u.data.entities.Metadata;

import java.util.List;

public class ProductListResponse {

    private long count;
    private String next;
    private String previous;

    private Metadata metadata;

    private List<Product> products;

    public void setCount(long count) {
        this.count = count;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Metadata getMetadata() {
        if (metadata == null)
            metadata = new Metadata(count, next, previous);
        return metadata;
    }
}
