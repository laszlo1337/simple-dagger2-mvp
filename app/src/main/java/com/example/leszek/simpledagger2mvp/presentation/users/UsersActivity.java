package com.example.leszek.simpledagger2mvp.presentation.users;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.leszek.simpledagger2mvp.R;
import com.example.leszek.simpledagger2mvp.SimpleDagger2MvpApplication;
import com.example.leszek.simpledagger2mvp.di.component.ApplicationComponent;
import com.example.leszek.simpledagger2mvp.domain.model.User;
import com.example.leszek.simpledagger2mvp.presentation.userdetails.UserDetailsActivity;
import com.example.leszek.simpledagger2mvp.presentation.users.adapter.EndlessRecyclerOnScrollListener;
import com.example.leszek.simpledagger2mvp.presentation.users.adapter.UsersListAdapter;
import com.example.leszek.simpledagger2mvp.presentation.users.di.DaggerUsersComponent;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.leszek.simpledagger2mvp.common.BundleKey.KEY_USER_LOGIN;

public class UsersActivity extends AppCompatActivity implements UsersView, EndlessRecyclerOnScrollListener.OnLoadMoreListener {
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
    public void onLoadMore() {
        if (!progressBarHidden) {
            progressBar.show();
        }
        presenter.loadMore();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationComponent applicationComponent = ((SimpleDagger2MvpApplication) getApplication()).getApplicationComponent();
        DaggerUsersComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
                .injectTo(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        adapter = new UsersListAdapter();
        adapter.setOnClickListener(this);
        recyclerView.setAdapter(adapter);
        endlessScrollListener = new EndlessRecyclerOnScrollListener((LinearLayoutManager) recyclerView.getLayoutManager(), this);
        recyclerView.addOnScrollListener(endlessScrollListener);

        presenter.attachView(new WeakReference<>(this).get());

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
                progressBarHidden = true;
                return true;
            }
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(getApplication(), "CLOSED", Toast.LENGTH_SHORT).show();
                endlessScrollListener.reset(0,true);
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
    public void showErrorMessage() {
        Snackbar.make(coordinatorLayout, R.string.error_message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onClickListItem(String name) {
        Intent intent = new Intent(this, UserDetailsActivity.class);
        intent.putExtra(KEY_USER_LOGIN, name);
        startActivity(intent);
    }
}
