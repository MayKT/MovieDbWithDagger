package com.mkttestprojects.moviedbwithdagger.ui.main;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.mkttestprojects.moviedbwithdagger.R;
import com.mkttestprojects.moviedbwithdagger.BaseActivity;
import com.mkttestprojects.moviedbwithdagger.ui.main.home.HomeFragment;
import com.mkttestprojects.moviedbwithdagger.ui.main.mylist.MyListFragment;
import com.mkttestprojects.moviedbwithdagger.ui.main.profile.ProfileFragment;
import com.mkttestprojects.moviedbwithdagger.ui.main.search.SearchFragment;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    Fragment fragment;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }


    @Override
    protected void setUpContents(Bundle savedInstanceState) {

//        setupToolbar(false);
//        setupToolbarText("Movies");

        openFragment(new HomeFragment());

        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Home", R.drawable.ic_home_black_24dp, R.color.colorwhite);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Search", R.drawable.search, R.color.colorwhite);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("My List", R.drawable.download, R.color.colorwhite);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Profile", R.drawable.account, R.color.colorwhite);


        // Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);

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
