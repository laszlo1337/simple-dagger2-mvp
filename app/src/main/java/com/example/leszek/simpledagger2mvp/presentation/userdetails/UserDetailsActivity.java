package com.example.leszek.simpledagger2mvp.presentation.userdetails;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.leszek.simpledagger2mvp.R;
import com.example.leszek.simpledagger2mvp.SimpleDagger2MvpApplication;
import com.example.leszek.simpledagger2mvp.di.component.ApplicationComponent;
import com.example.leszek.simpledagger2mvp.presentation.userdetails.di.DaggerUserDetailsComponent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.leszek.simpledagger2mvp.common.BundleKey.KEY_USER_LOGIN;

public class UserDetailsActivity extends AppCompatActivity implements UserDetailsView {

    @BindView(R.id.userdetails_toolbar)
    Toolbar toolbar;
    @BindView(R.id.userdetails_coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @Inject
    UserDetailsPresenter presenter;
    @BindView(R.id.userdetails_avatar)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationComponent applicationComponent = ((SimpleDagger2MvpApplication)getApplication()).getApplicationComponent();
        DaggerUserDetailsComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
                .injectTo(this);

        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        String login = getIntent().getStringExtra(KEY_USER_LOGIN);
        presenter.attachView(this,login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(login);
        toolbar.setNavigationOnClickListener(v -> finish());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.userdetails_fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void setUserDetails(UserDetailsModel userDetails) {
        Glide.with(this).load(userDetails.getAvatarUrl()).into(imageView);
    }

    @Override
    public void showErrorMessage() {
        Snackbar.make(coordinatorLayout, R.string.userdetails_error_message, Snackbar.LENGTH_LONG).show();
    }
}
