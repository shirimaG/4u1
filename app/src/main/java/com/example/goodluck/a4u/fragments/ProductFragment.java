package com.example.goodluck.a4u.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.goodluck.a4u.R;
import com.example.goodluck.a4u.activities.MainActivity;
import com.example.goodluck.a4u.data.entities.product.Product;
import com.example.goodluck.a4u.utils.MsgUtils;
import com.example.goodluck.a4u.utils.Utils;

import timber.log.Timber;


public class ProductFragment extends Fragment {

    private static final String PRODUCT_ID = "product_id";
    private static final String PRODUCT_JSON = "product_json";

    // Fields referencing product layouts
    private TextView productTitleTv;
    private TextView productPriceTv;
    private TextView productDescription;

    /**
     * Refers to the displayed product
     */
    private Product product;

    /**
     * Create a new fragment instance for product detail
     *
     * @param product id of product to show
     * @return fragment instance
     */
    public static ProductFragment newInstance(Product product) {
        Bundle args = new Bundle();
        String productJson = Utils.getGsonParser().toJson(product);
        args.putString(PRODUCT_JSON, productJson);
        args.putLong(PRODUCT_ID, product.getId());
        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        Timber.d("%s - onCreateView", this.getClass().getSimpleName());
        MainActivity.setActionBarTitle(getString(R.string.product));

        View view = inflater.inflate(R.layout.fragment_product, container, false);

        productTitleTv = view.findViewById(R.id.product_title_tv);
        productDescription = view.findViewById(R.id.product_description_tv);
        productPriceTv = view.findViewById(R.id.product_price_tv);

        Product product = Utils.getGsonParser().fromJson(getArguments().getString(PRODUCT_JSON), Product.class);
        refreshScreenData(product);
        return view;
    }

    private String concatCurrency(double price, String currency) {
        return String.valueOf(price).concat(" ").concat(currency);
    }

    private void refreshScreenData(Product product) {
        if (null != product) {
            productTitleTv.setText(product.getTitle());
            productDescription.setText(product.getDescription());
            productPriceTv.setText(concatCurrency(product.getPrice(), "TZSH"));
        } else {
            MsgUtils.showToast(getActivity(), MsgUtils.TOAST_TYPE_INTERNAL_ERROR, getString(R.string.internal_error), MsgUtils.ToastLength.SHORT);
            Timber.e(new RuntimeException(), "Refresh product screen with null product");
        }
    }
}
