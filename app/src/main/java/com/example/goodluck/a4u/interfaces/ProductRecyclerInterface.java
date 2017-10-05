package com.example.goodluck.a4u.interfaces;


import android.view.View;

import com.example.goodluck.a4u.data.entities.product.Product;

public interface ProductRecyclerInterface {
    void onProductSelected(View view, Product product);
}
