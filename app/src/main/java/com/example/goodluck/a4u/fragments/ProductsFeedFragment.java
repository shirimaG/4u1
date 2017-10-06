package com.example.goodluck.a4u.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.goodluck.a4u.MyApplication;
import com.example.goodluck.a4u.R;
import com.example.goodluck.a4u.activities.MainActivity;
import com.example.goodluck.a4u.api.EndPoints;
import com.example.goodluck.a4u.api.GsonRequest;
import com.example.goodluck.a4u.data.adapters.ProductsRecycleAdapter;
import com.example.goodluck.a4u.data.entities.Metadata;
import com.example.goodluck.a4u.data.entities.product.Product;
import com.example.goodluck.a4u.data.entities.product.ProductListResponse;
import com.example.goodluck.a4u.interfaces.ProductRecyclerInterface;
import com.example.goodluck.a4u.utils.Constants;
import com.example.goodluck.a4u.utils.EndlessRecyclerScrollListener;

import timber.log.Timber;

/**
 * Fragment handles displaying all products list
 * Also allow display of the search results
 */
@SuppressWarnings("FieldCanBeLocal")
public class ProductsFeedFragment extends Fragment {

    private static final String TAG = ProductsFeedFragment.class.getSimpleName();

    // Content specific
    private TextView emptyContentView;
    private RecyclerView productsRecyclerView;
    private GridLayoutManager productsRecyclerLayoutManager;
    private ProductsRecycleAdapter productsRecycleAdapter;
    private EndlessRecyclerScrollListener endlessRecyclerScrollListener;

    private View loadMoreProgress;


    /**
     * Request metadata containing URLS for endlessScroll
     */
    private Metadata productsMetadata;


    /**
     * Show product default product lists
     */
    public static ProductsFeedFragment newInstance() {
        return new ProductsFeedFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, " - onCreateView");
        View view = inflater.inflate(R.layout.fragment_products_feed, container, false);

        this.emptyContentView = view.findViewById(R.id.product_feed_empty);
        this.loadMoreProgress = view.findViewById(R.id.product_feed_load_more_progress);

        Bundle args = getArguments();
        if (null != args) {
            Log.e(TAG, "Unexpected arguments passed: " + args);
        } else {
            MainActivity.setActionBarTitle(getString(R.string.app_name));

            // Opened first time (not from backstack)
            if (productsRecycleAdapter == null || productsRecycleAdapter.getItemCount() == 0) {
                prepareRecyclerAdapter();
                prepareProductRecycler(view);
                getProducts(null);
            } else {
                prepareProductRecycler(view);
                Log.d(TAG, "Restore previous state. (Products already loaded)");
            }
        }

        return view;
    }

    private void prepareRecyclerAdapter() {
        productsRecycleAdapter = new ProductsRecycleAdapter(getActivity(), new ProductRecyclerInterface() {
            @Override
            public void onProductSelected(View view, Product product) {
                ((MainActivity) getActivity()).onProductSelected(product);
            }
        });
    }

    private void prepareProductRecycler(View view) {
        this.productsRecyclerView = view.findViewById(R.id.feed_products_recycler);
        productsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        productsRecyclerView.setHasFixedSize(true);

        productsRecyclerLayoutManager = new GridLayoutManager(getActivity(), 1);
        productsRecyclerView.setLayoutManager(productsRecyclerLayoutManager);
        endlessRecyclerScrollListener = new EndlessRecyclerScrollListener(productsRecyclerLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                Timber.d("Load more");
                if (productsMetadata != null && productsMetadata.getNext() != null) {
                    getProducts(productsMetadata.getNext());
                } else {
                    Log.d(TAG, "CustomLoadMoreDataFromAPI NO MORE DATA");
                }
            }
        };

        productsRecyclerView.addOnScrollListener(endlessRecyclerScrollListener);
        productsRecyclerView.setAdapter(productsRecycleAdapter);
    }

    private void getProducts(String url) {
        loadMoreProgress.setVisibility(View.VISIBLE);
        if (null == url) {
            if (endlessRecyclerScrollListener != null) endlessRecyclerScrollListener.clean();
            productsRecycleAdapter.clear();
            url = EndPoints.PRODUCTS;
        }

        GsonRequest<ProductListResponse> getProductsRequest = new GsonRequest<>(
                Request.Method.GET, url, null, ProductListResponse.class, new Response.Listener<ProductListResponse>() {
            @Override
            public void onResponse(ProductListResponse response) {
                productsRecycleAdapter.addProducts(response.getProducts());
                productsMetadata = response.getMetadata();
                checkEmptyContent();
                loadMoreProgress.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                checkEmptyContent();
                // TODO: 05/10/2017 show error to application user
                Timber.e("WTF error: %s", error.getMessage());
            }
        });

        getProductsRequest.setRetryPolicy(MyApplication.getDefaultRetryPolicy());
        getProductsRequest.setShouldCache(false);
        MyApplication.getInstance().addToRequestQueue(getProductsRequest, Constants.PRODUCTS_REQUESTS_TAG);
    }

    private void checkEmptyContent() {
        if (productsRecycleAdapter != null && productsRecycleAdapter.getItemCount() > 0) {
            emptyContentView.setVisibility(View.INVISIBLE);
            productsRecyclerView.setVisibility(View.VISIBLE);
        } else {
            emptyContentView.setVisibility(View.VISIBLE);
            productsRecyclerView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onStop() {
        if (loadMoreProgress != null) {
            // Hide progress dialog if exist.
            if (loadMoreProgress.getVisibility() == View.GONE && endlessRecyclerScrollListener != null) {
                // Fragment stopped during loading data. Allow new loading on return
                endlessRecyclerScrollListener.resetLoading();
            }
            loadMoreProgress.setVisibility(View.GONE);
        }
        MyApplication.getInstance().cancelPendingRequests(Constants.PRODUCTS_REQUESTS_TAG);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        if (productsRecyclerView != null) productsRecyclerView.clearOnScrollListeners();
        super.onDestroyView();
    }
}