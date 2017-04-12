package com.example.leszek.simpledagger2mvp.presentation.userdetails;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.leszek.simpledagger2mvp.R;
import com.example.leszek.simpledagger2mvp.SimpleDagger2MvpApplication;
import com.example.leszek.simpledagger2mvp.di.component.ApplicationComponent;
import com.example.leszek.simpledagger2mvp.presentation.userdetails.di.DaggerUserDetailsComponent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.leszek.simpledagger2mvp.common.BundleKey.KEY_USER_LOGIN;

public class UserDetailsActivity extends AppCompatActivity implements UserDetailsView, OnMapReadyCallback {

    @BindView(R.id.userdetails_toolbar)
    Toolbar toolbar;
    @BindView(R.id.userdetails_coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.userdetails_avatar)
    ImageView imageView;
    @BindView(R.id.userdetails_bio_view)
    View bioView;
    @BindView(R.id.userdetails_name_view)
    View nameView;
    @BindView(R.id.userdetails_company_view)
    View companyView;
    @BindView(R.id.userdetails_flags_view)
    View flagsView;
    @BindView(R.id.userdetails_flag_hireable)
    View hireable;
    @BindView(R.id.userdetails_flag_admin)
    View admin;
    @BindView(R.id.userdetails_flag_organization)
    View organization;
    @BindView(R.id.userdetails_fab)
    FloatingActionButton fab;
    @BindView(R.id.userdetails_location_view)
    View locationView;

    //user details
    @BindView(R.id.userdetails_bio_text)
    TextView bio;
    @BindView(R.id.userdetails_clickable_blog)
    View blog;
    @BindView(R.id.userdetails_clickable_gitprofile)
    View gitProfile;
    @BindView(R.id.userdetails_name_text)
    TextView name;
    @BindView(R.id.userdetails_company_text)
    TextView company;
    @BindView(R.id.userdetails_id_text)
    TextView id;
    @BindView(R.id.userdetails_date_joined_text)
    TextView dateJoined;
    @BindView(R.id.userdetails_followers_text)
    TextView followers;


    @Inject
    UserDetailsPresenter presenter;

    SupportMapFragment mapFragment;
    Geocoder geocoder;
    String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationComponent applicationComponent = ((SimpleDagger2MvpApplication) getApplication()).getApplicationComponent();
        DaggerUserDetailsComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
                .injectTo(this);

        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);


        setSupportActionBar(toolbar);
        String login = getIntent().getStringExtra(KEY_USER_LOGIN);
        presenter.attachView(this, login);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(login);
        toolbar.setNavigationOnClickListener(v -> finish());


        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.userdetails_map);
        geocoder = new Geocoder(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void setUserDetails(UserDetailsModel ud) {
        Glide.with(this).load(ud.getAvatarUrl()).into(imageView);
        if (ud.getLocation() != null) {
            cityName = ud.getLocation();
        }
        id.setText(String.valueOf(ud.getId()));
        if(ud.isHireable() || ud.isSiteAdmin() || ud.getType().equals("Organization")){
            setVisible(flagsView);
            if(ud.isHireable()) setVisible(hireable);
            if(ud.isSiteAdmin()) setVisible(admin);
            if(ud.getType().equals("Organization")) setVisible(organization);
        }
        if(ud.getName() != null){
            setVisible(nameView);
            name.setText(ud.getName());
        }
        if (ud.getBio() != null) {
            setVisible(bioView);
            bio.setText(ud.getBio());
        }
        if (ud.getCompany() != null) {
            setVisible(companyView);
            company.setText(ud.getCompany());
        }
        dateJoined.setText(ud.getCreatedAt());
        followers.setText(String.valueOf(ud.getFollowers()));
        gitProfile.setOnClickListener(v -> {
            Intent newIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(ud.getHtmlUrl()));
            startActivity(newIntent);
        });
        if (ud.getBlog() != null) {
            blog.setVisibility(View.VISIBLE);
            blog.setOnClickListener(v -> {
                Intent newIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(ud.getBlog()));
                startActivity(newIntent);
            });
        }
        if (ud.getEmail() != null) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", ud.getEmail(), null));
                startActivity(Intent.createChooser(intent, "Send email..."));
            });
        }

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (cityName != null) {
            setVisible(locationView);
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_map_marker);
            List<Address> address = new ArrayList<>(1);
            try {
                address = geocoder.getFromLocationName(cityName, 1);
            } catch (IOException e) {
                e.printStackTrace();
                locationView.setVisibility(View.GONE);
            }
            Address a = address.get(0);
            LatLng location = new LatLng(a.getLatitude(), a.getLongitude());
            Marker marker = googleMap.addMarker(new MarkerOptions().position(location)
                    .title(cityName).icon(icon).anchor(0.5f,0));
            marker.showInfoWindow();
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        }
    }

    @Override
    public void showErrorMessage() {
        Snackbar.make(coordinatorLayout, R.string.userdetails_error_message, Snackbar.LENGTH_LONG).show();
    }

    public void setVisible(View v){
        v.setVisibility(View.VISIBLE);
    }
}
