package com.example.leszek.simpledagger2mvp.presentation.users;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.leszek.simpledagger2mvp.R;
import com.example.leszek.simpledagger2mvp.SimpleDagger2MvpApplication;
import com.example.leszek.simpledagger2mvp.di.component.ApplicationComponent;
import com.example.leszek.simpledagger2mvp.presentation.userdetails.UserDetailsActivity;
import com.example.leszek.simpledagger2mvp.presentation.users.adapter.EndlessRecyclerOnScrollListener;
import com.example.leszek.simpledagger2mvp.presentation.users.adapter.UsersListAdapter;
import com.example.leszek.simpledagger2mvp.presentation.users.di.DaggerUsersComponent;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

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



    @Override
    public void onLoadMore() {
        progressBar.show();
        presenter.loadMore();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void updateUsers(List<UserModel> users) {
        adapter.update(users);
        progressBar.hide();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationComponent applicationComponent = ((SimpleDagger2MvpApplication)getApplication()).getApplicationComponent();
        DaggerUsersComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
                .injectTo(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        adapter = new UsersListAdapter();
        adapter.setOnClickListener(this);
        recyclerView.setAdapter(adapter);
        EndlessRecyclerOnScrollListener endlessScrollListener = new EndlessRecyclerOnScrollListener((LinearLayoutManager)recyclerView.getLayoutManager(),this);
        recyclerView.addOnScrollListener(endlessScrollListener);


        presenter.attachView(new WeakReference<>(this).get());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showErrorMessage() {
        Snackbar.make(coordinatorLayout, R.string.error_message, BaseTransientBottomBar.LENGTH_LONG).show();
    }

    @Override
    public void onClickListItem(String name) {
        Intent intent = new Intent(this, UserDetailsActivity.class);
        intent.putExtra(KEY_USER_LOGIN, name);
        startActivity(intent);
    }
}
