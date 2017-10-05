package com.example.goodluck.a4u.data.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.goodluck.a4u.R;
import com.example.goodluck.a4u.data.entities.product.Product;
import com.example.goodluck.a4u.interfaces.ProductRecyclerInterface;

import java.util.ArrayList;
import java.util.List;

public class ProductsRecycleAdapter extends RecyclerView.Adapter<ProductsRecycleAdapter.ViewHolder>{

    private final ProductRecyclerInterface productRecyclerInterface;
    private List<Product> products = new ArrayList<>();
    private LayoutInflater layoutInflater;

    /**
     * Created an adapter that handles a list of product items
     */

    public ProductsRecycleAdapter(Context context, ProductRecyclerInterface productRecyclerInterface) {
        this.productRecyclerInterface = productRecyclerInterface;
    }

    private Product getItem(int position) {
        return products.get(position);
    }

    /**
     * Returns the size of dataset
     */
    @Override
    public int getItemCount() {
        return products.size();
    }

    public void  addProducts(List<Product> productList) {
        products.addAll(productList);
        notifyDataSetChanged();
    }


    /**
     * Create new views , Invoked by layout manager
     */
    @Override
    public ProductsRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_products, parent, false);
        return new ViewHolder(view, productRecyclerInterface);
    }

    // Replace the contents of a view (layout manager)
    @Override
    public void onBindViewHolder(ProductsRecycleAdapter.ViewHolder holder, int position) {
        Product product = getItem(position);
        holder.bindContent(product);

        // - replace the content of the view with that element
        holder.productTitleTV.setText(holder.product.getTitle());
        holder.productDescriptionTV.setText(holder.product.getDescription());
        holder.productPriceTV.setText(String.valueOf(holder.product.getPrice()));
    }

    public void clear() {
        products.clear();
    }


    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productTitleTV;
        public TextView productDescriptionTV;
        public TextView productPriceTV;
        private Product product;

        public ViewHolder(View view, final ProductRecyclerInterface categoryRecyclerInterface) {
            super(view);
            productTitleTV = view.findViewById(R.id.product_item_title);
            productDescriptionTV = view.findViewById(R.id.product_item_description);
            productPriceTV = view.findViewById(R.id.product_item_price);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    categoryRecyclerInterface.onProductSelected(view, product);
                }
            });
        }

        public void bindContent(Product product) {
            this.product = product;
        }
    }
}
