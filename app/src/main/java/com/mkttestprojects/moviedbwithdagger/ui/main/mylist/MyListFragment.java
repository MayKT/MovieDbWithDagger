package com.mkttestprojects.moviedbwithdagger.ui.main.mylist;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mkttestprojects.moviedbwithdagger.common.BaseFragment;
import com.mkttestprojects.moviedbwithdagger.R;
import com.mkttestprojects.moviedbwithdagger.models.MovieListInfo;
import com.mkttestprojects.moviedbwithdagger.models.MovieListModel;
import com.mkttestprojects.moviedbwithdagger.ui.MovieAdapter;
import com.mkttestprojects.moviedbwithdagger.ui.login.LoginActivity;
import com.mkttestprojects.moviedbwithdagger.ui.main.Resource;
import com.mkttestprojects.moviedbwithdagger.util.SharePreferenceHelper;
import com.mkttestprojects.moviedbwithdagger.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.Binds;

public class MyListFragment extends BaseFragment {
    @BindView(R.id.rv_mywatchlist)
    RecyclerView rvMywatchlist;

    @BindView(R.id.rv_ratedmovies)
    RecyclerView rvRatedMovies;

    @BindView(R.id.btn_singin)
    Button btnSignin;

    @BindView(R.id.layout_already_signin)
    LinearLayout layoutAlreadySignin;

    @Inject
    ViewModelProviderFactory providerFactory;

    private MyListFragmentViewModel myListFragmentViewModel;

    private MovieAdapter movieAdapter;

    private MovieAdapter ratedAdapter;

    private String sessionId;

    private SharePreferenceHelper mSharePreference;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mylist;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        init();
    }

    private void init() {

        myListFragmentViewModel = new ViewModelProvider(this,providerFactory).get(MyListFragmentViewModel.class);

        movieAdapter = new MovieAdapter();

        ratedAdapter = new MovieAdapter();

        mSharePreference = new SharePreferenceHelper(getContext());

        sessionId = mSharePreference.getSessionId();

        rvMywatchlist.setHasFixedSize(true);
        rvMywatchlist.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rvMywatchlist.setAdapter(movieAdapter);

        rvRatedMovies.setHasFixedSize(true);
        rvRatedMovies.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rvRatedMovies.setAdapter(ratedAdapter);

        if(mSharePreference.isLogin()){
            btnSignin.setVisibility(View.GONE);
            layoutAlreadySignin.setVisibility(View.VISIBLE);
            subscribeWatchListMovies(sessionId);
            subscribeRatedMovies(sessionId);
        }
        else {
            layoutAlreadySignin.setVisibility(View.GONE);
            btnSignin.setVisibility(View.VISIBLE);
        }
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoginActivity.LoginActivityIntent(getContext()));
            }
        });
    }

    private void subscribeRatedMovies(String sessionId) {
        movieAdapter.clear();
        myListFragmentViewModel.observeWatchList(sessionId).observe(this, new Observer<Resource<MovieListModel>>() {
            @Override
            public void onChanged(Resource<MovieListModel> movieListModelResource) {
                if(movieListModelResource != null){
                    switch (movieListModelResource.status){
                        case LOADING:
                            movieAdapter.showLoading();
                            break;
                        case SUCCESS:
                            movieAdapter.clearFooter();
                            for (MovieListInfo movieListInfo : movieListModelResource.data.getResults())
                                movieAdapter.add(movieListInfo);
                            break;
                        case ERROR:
                            movieAdapter.clearFooter();
                            showDialogMsg("Error getting WatchList movies");
                            break;
                    }
                }
            }
        });
    }

    private void showDialogMsg(String msg) {
        new AlertDialog.Builder(getContext())
                .setMessage(msg)
                .setPositiveButton(
                        "Ok",
                        (dialog, whichButton) -> {
                            subscribeRatedMovies(sessionId);
                            subscribeRatedMovies(sessionId);
                            dialog.dismiss();
                        })
                .show();
    }

    private void subscribeWatchListMovies(String sessionId) {
        ratedAdapter.clear();
        myListFragmentViewModel.observeRatedMovie(sessionId).observe(this, new Observer<Resource<MovieListModel>>() {
            @Override
            public void onChanged(Resource<MovieListModel> movieListModelResource) {
                if(movieListModelResource != null){
                    switch (movieListModelResource.status){
                        case LOADING:
                            ratedAdapter.showLoading();
                            break;
                        case SUCCESS:
                            ratedAdapter.clearFooter();
                            for(MovieListInfo movieListInfo : movieListModelResource.data.getResults())
                                ratedAdapter.add(movieListInfo);
                            break;
                        case ERROR:
                            ratedAdapter.clearFooter();
                            showDialogMsg("Error getting Rated Movies");
                            break;
                    }
                }
            }
        });
    }
}
