package com.mkttestprojects.moviedbwithdagger.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.mkttestprojects.moviedbwithdagger.R;
import com.mkttestprojects.moviedbwithdagger.common.BaseActivity;
import com.mkttestprojects.moviedbwithdagger.ui.main.home.HomeFragment;
import com.mkttestprojects.moviedbwithdagger.ui.main.mylist.MyListFragment;
import com.mkttestprojects.moviedbwithdagger.ui.main.profile.ProfileFragment;
import com.mkttestprojects.moviedbwithdagger.ui.main.search.SearchFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    Fragment fragment;

    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    public static Intent MainActivityIntent(Context context){
        return new Intent(context,MainActivity.class);
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void setUpContents(Bundle savedInstanceState) {

//        setupToolbar(false);
//        setupToolbarText("Movies");

        openFragment(new HomeFragment());

//        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getString(R.string.home),R.drawable.ic_home_black_24dp);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getString(R.string.search), R.drawable.search);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getString(R.string.mylist), R.drawable.download);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(getString(R.string.profile), R.drawable.account);


        // Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
//        bottomNavigation.addItem(item5);

        bottomNavigation.setDefaultBackgroundColor(getColor(R.color.color_black));

        bottomNavigation.setBehaviorTranslationEnabled(false);

        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        bottomNavigation.setOnTabSelectedListener((position, wasSelected) -> {
            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                    openFragment(fragment);
                    return true;

                case 1:
                    fragment = new SearchFragment();
                    openFragment(fragment);
                    return true;

                case 2:
                    fragment = new MyListFragment();
                    openFragment(fragment);
                    return  true;

                case 3:
                    fragment = new ProfileFragment();
                    openFragment(fragment);
                    return true;
            }

            return false;
        });

    }


    @Override
    protected void showLoading() {

    }

    @Override
    protected void hideloading() {

    }

    @Override
    protected void showDialogMsg(String msg) {

    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
