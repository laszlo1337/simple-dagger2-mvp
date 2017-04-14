package com.example.leszek.simpledagger2mvp.presentation.users;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.leszek.simpledagger2mvp.R;
import com.example.leszek.simpledagger2mvp.presentation.base.BaseActivity;
import com.example.leszek.simpledagger2mvp.presentation.userdetails.UserDetailsActivity;
import com.example.leszek.simpledagger2mvp.presentation.users.adapter.EndlessRecyclerOnScrollListener;
import com.example.leszek.simpledagger2mvp.presentation.users.adapter.UsersListAdapter;
import com.example.leszek.simpledagger2mvp.presentation.users.di.DaggerUsersComponent;
import com.example.leszek.simpledagger2mvp.presentation.users.di.UsersComponent;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;

import static com.example.leszek.simpledagger2mvp.common.BundleKey.KEY_USER_LOGIN;

public class UsersActivity extends BaseActivity<UsersPresenter> implements UsersView, EndlessRecyclerOnScrollListener.OnLoadMoreListener {

    @Inject
    UsersPresenter presenter;
    @BindView(R.id.bar_progress)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_coordinator)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private UsersListAdapter adapter;
    private boolean progressBarHidden;
    private EndlessRecyclerOnScrollListener endlessScrollListener;


    @Override
    protected int layoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void created() {
        setSupportActionBar(toolbar);

        adapter = new UsersListAdapter();
        adapter.setOnClickListener(this);
        recyclerView.setAdapter(adapter);
        endlessScrollListener = new EndlessRecyclerOnScrollListener((LinearLayoutManager) recyclerView.getLayoutManager(), this);
        recyclerView.addOnScrollListener(endlessScrollListener);
    }

    @Override
    protected void inject() {
        presenterComponent = DaggerUsersComponent.builder().applicationComponent(getApplicationComponent()).build();
        ((UsersComponent) presenterComponent).injectTo(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_query_hint));
        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(getApplication(), "OPENED", Toast.LENGTH_SHORT).show();
                RxSearchView.queryTextChanges(searchView)
                        .filter(charSequence -> !TextUtils.isEmpty(charSequence))
                        .debounce(500, TimeUnit.MILLISECONDS)
                        .subscribe(searchedLogin -> {
                            presenter.setQuery(searchedLogin.toString());
                            presenter.searchViewSelected(true);
                        });
                recyclerView.smoothScrollToPosition(0);
                progressBarHidden = true;
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(getApplication(), "CLOSED", Toast.LENGTH_SHORT).show();
                endlessScrollListener.reset(0, true);
                presenter.searchViewSelected(false);
                progressBarHidden = false;
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickListItem(String name) {
        Intent intent = new Intent(this, UserDetailsActivity.class);
        intent.putExtra(KEY_USER_LOGIN, name);
        startActivity(intent);
    }

    @Override
    public void onLoadMore() {
        if (!progressBarHidden) {
            progressBar.show();
        }
        presenter.loadMore();
    }


    @Override
    public void showErrorMessage() {
        Snackbar.make(coordinatorLayout, R.string.error_message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setUsers(List<UserModel> users) {
        adapter.setUsers(users);
    }

    @Override
    public void updateUsers(List<UserModel> users) {
        adapter.update(users);
        progressBar.hide();
    }

    @Override
    public void clearUsers() {
        adapter.clearUsers();
    }
}
