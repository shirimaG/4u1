package com.example.goodluck.a4u.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.goodluck.a4u.BuildConfig;
import com.example.goodluck.a4u.R;
import com.example.goodluck.a4u.fragments.ProductsFeedFragment;
import com.example.goodluck.a4u.utils.MsgUtils;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    private static final String MSG_MAIN_ACTIVITY_IS_NULL = "MainActivity instance is null";
    private static final String TAG = MainActivity.class.getSimpleName();
    private static MainActivity mInstance = null;

    /**
     * Indicate the app will be closed on next back press
     */
    public boolean isAppReadyToFinish = false;

    /**
     * Returns Main activity instance. Null if the activity doesn't exist
     *
     * @return activity instance
     */
    private static synchronized MainActivity getInstance() {
        return mInstance;
    }

    /**
     * Update actionBar title
     */

    public static void setActionBarTitle(String title) {
        MainActivity instance = MainActivity.getInstance();
        if (null != instance) {
            instance.setTitle(title);
        } else {
            Log.e(TAG, MSG_MAIN_ACTIVITY_IS_NULL);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInstance = this;

        setContentView(R.layout.activity_main);

        // Prepare tool bar
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
        } else {
            Log.e(TAG, "GetSupportActionBar returned null");
        }

        addInitialFragment();
    }

    private void addInitialFragment() {
        clearBackStack();
        Timber.d("Products feed fragment called");
        Fragment fragment = ProductsFeedFragment.newInstance();
        FragmentManager frgManager = getSupportFragmentManager();
        FragmentTransaction frgTransaction = frgManager.beginTransaction();
        frgTransaction.add(R.id.main_content_frame, fragment).commit();
        frgManager.executePendingTransactions();
    }

    private void replaceFragment(Fragment newFragment, String transactionTag) {
        if (newFragment != null) {
            FragmentManager frgManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = frgManager.beginTransaction();
            fragmentTransaction.addToBackStack(transactionTag);
            fragmentTransaction.replace(R.id.main_content_frame, newFragment).commit();
            frgManager.executePendingTransactions();
        } else {
            Timber.e(new RuntimeException(), "Replace fragments with null newFragment parameter");
        }
    }

    private void clearBackStack() {
        Timber.d("Clearing backstack");
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            if (BuildConfig.DEBUG) {
                for (int i = 0; i < manager.getBackStackEntryCount(); i++) {
                    Timber.d("BackStack content_%d= %d, name: %s", i, manager.getBackStackEntryAt(i).getId(), manager.getBackStackEntryAt(i).getName());
                }
            }
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStackImmediate(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        Timber.d("BackStack cleared");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Prepare search view
        MenuItem searchItem = menu.findItem(R.id.action_search);
        if (null != searchItem) {
            prepareSearchView(searchItem);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0 | isAppReadyToFinish) {
            super.onBackPressed();
        } else {
            // Back stack is empty. For closing the app user have to tap two times in two seconds
            MsgUtils.showToast(this, MsgUtils.TOAST_TYPE_MESSAGE, getString(R.string.another_click_for_leaving_app),
                    MsgUtils.ToastLength.SHORT);
            isAppReadyToFinish = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isAppReadyToFinish = false;
                }
            }, 2000);
        }
    }

    private void prepareSearchView(final MenuItem searchItem) {
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSubmitButtonEnabled(true);
        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Submit search query and hide search action view
                onSearchSubmitted(query);
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

    }

    /**
     * Method create new category fragment with defined search query
     *
     * @param searchQuery text used for products search
     */
    private void onSearchSubmitted(String searchQuery) {
        Timber.d("Search submitted: ?search=%s",searchQuery);
    }

    /**
     * Launch ProductFragment
     */

    public void onProductSelected(long productId) {
        Toast.makeText(this, "Product clicked " + String.valueOf(productId), Toast.LENGTH_SHORT).show();
    }

}
