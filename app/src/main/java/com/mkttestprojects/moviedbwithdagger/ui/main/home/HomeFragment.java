package com.mkttestprojects.moviedbwithdagger.ui.main.home;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mkttestprojects.moviedbwithdagger.BaseFragment;
import com.mkttestprojects.moviedbwithdagger.R;
import com.mkttestprojects.moviedbwithdagger.models.MovieListInfo;
import com.mkttestprojects.moviedbwithdagger.models.MovieListModel;
import com.mkttestprojects.moviedbwithdagger.ui.MovieAdapter;
import com.mkttestprojects.moviedbwithdagger.ui.main.Resource;
import com.mkttestprojects.moviedbwithdagger.viewmodels.ViewModelProviderFactory;
import javax.inject.Inject;
import butterknife.BindView;

public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private HomeFragmentViewModel homeFragmentViewModel;

    private static final String TAG = "HomeFragment";

    @BindView(R.id.first_recycler_view)
    RecyclerView firstrecyclerView;

    @BindView(R.id.second_recycler_view)
    RecyclerView secondRecyclerView;

    @BindView(R.id.third_recycler_view)
    RecyclerView thirdRecyclerView;

    @BindView(R.id.fourth_recycler_view)
    RecyclerView fourthRecyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    MovieAdapter homeFragmentAdapter;

    @Inject
    MovieAdapter secondHomeFragmentAdapter;

    @Inject
    MovieAdapter thirdHomeFragmentAdapter;

    @Inject
    MovieAdapter fourthHomeFragmentAdapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    public HomeFragment() {
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        homeFragmentViewModel = new ViewModelProvider(getActivity(),providerFactory).get(HomeFragmentViewModel.class);

        init();

        getData();

        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void getData() {

        subscribeTrendingObservers();
        subscribeNowPlayingObservers();
        subscribeUpcominObservers();
        subscribeTopRatedObservers();
    }

    private void init() {
        Log.e(TAG, "init: " );
        firstrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        firstrecyclerView.setAdapter(homeFragmentAdapter);

        secondRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        secondRecyclerView.setAdapter(secondHomeFragmentAdapter);

        thirdRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        thirdRecyclerView.setAdapter(thirdHomeFragmentAdapter);

        fourthRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        fourthRecyclerView.setAdapter(fourthHomeFragmentAdapter);

    }
    private void subscribeNowPlayingObservers() {
        Log.e(TAG, "subscribeObservers: reach to subscribe ");
        homeFragmentViewModel.observeNowPlayingMovieLists().removeObservers(getViewLifecycleOwner());
        homeFragmentViewModel.observeNowPlayingMovieLists().observe(getViewLifecycleOwner(), new Observer<Resource<MovieListModel>>() {
            @Override
            public void onChanged(Resource<MovieListModel> movieListModelResource) {
                if (movieListModelResource != null) {
                    switch (movieListModelResource.status) {
                        case LOADING:
                            secondHomeFragmentAdapter.showLoading();
                            Log.e(TAG, "onChanged: case loading" );
                            break;
                        case SUCCESS:
                            Log.e(TAG, "onChanged: case success" );
                            secondHomeFragmentAdapter.clear();
                            for (MovieListInfo movieListInfo : movieListModelResource.data.getResults()){
                                secondHomeFragmentAdapter.add(movieListInfo);
                            }
                            break;
                        case ERROR:
                            Log.e(TAG, "onChanged: case error" );
                            Toast.makeText(getContext(), "Error...", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + movieListModelResource.status);
                    }
                }
            }
        });
    }


    private void subscribeTrendingObservers() {
        Log.e(TAG, "subscribeObservers: reach to subscribe ");
        homeFragmentViewModel.observeTrendingMovieLists().removeObservers(getViewLifecycleOwner());
        homeFragmentViewModel.observeTrendingMovieLists().observe(getViewLifecycleOwner(), new Observer<Resource<MovieListModel>>() {
            @Override
            public void onChanged(Resource<MovieListModel> movieListModelResource) {
                if (movieListModelResource != null) {
                    switch (movieListModelResource.status) {
                        case LOADING:
                            Log.e(TAG, "onChanged: case loading" );
                            homeFragmentAdapter.showLoading();
                            break;
                        case SUCCESS:
                            Log.e(TAG, "onChanged: case success" );
                            homeFragmentAdapter.clear();
                            for (MovieListInfo movieListInfo : movieListModelResource.data.getResults()){
                                homeFragmentAdapter.add(movieListInfo);
                            }
                            break;
                        case ERROR:
                            Log.e(TAG, "onChanged: case error" );
                            Toast.makeText(getContext(), "Error...", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + movieListModelResource.status);
                    }
                }
            }
        });
    }
    private void subscribeUpcominObservers() {
        Log.e(TAG, "subscribeObservers: reach to subscribe ");
        homeFragmentViewModel.observeUpcominMovieLists().removeObservers(getViewLifecycleOwner());
        homeFragmentViewModel.observeUpcominMovieLists().observe(getViewLifecycleOwner(), new Observer<Resource<MovieListModel>>() {
            @Override
            public void onChanged(Resource<MovieListModel> movieListModelResource) {
                if (movieListModelResource != null) {
                    switch (movieListModelResource.status) {
                        case LOADING:
                            Log.e(TAG, "onChanged: case loading" );
                            thirdHomeFragmentAdapter.showLoading();
                            break;
                        case SUCCESS:
                            Log.e(TAG, "onChanged: case success" );
                            thirdHomeFragmentAdapter.clear();
                            for (MovieListInfo movieListInfo : movieListModelResource.data.getResults()){
                                thirdHomeFragmentAdapter.add(movieListInfo);
                            }
                            break;
                        case ERROR:
                            Log.e(TAG, "onChanged: case error" );
                            Toast.makeText(getContext(), "Error...", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + movieListModelResource.status);
                    }
                }
            }
        });
    }
    private void subscribeTopRatedObservers() {
        Log.e(TAG, "subscribeObservers: reach to subscribe ");
        homeFragmentViewModel.observeTopRatedMovieLists().removeObservers(getViewLifecycleOwner());
        homeFragmentViewModel.observeTopRatedMovieLists().observe(getViewLifecycleOwner(), new Observer<Resource<MovieListModel>>() {
            @Override
            public void onChanged(Resource<MovieListModel> movieListModelResource) {
                if (movieListModelResource != null) {
                    switch (movieListModelResource.status) {
                        case LOADING:
                            Log.e(TAG, "onChanged: case loading" );
                            fourthHomeFragmentAdapter.showLoading();
                            break;
                        case SUCCESS:
                            Log.e(TAG, "onChanged: case success" );
                            fourthHomeFragmentAdapter.clear();
                            for (MovieListInfo movieListInfo : movieListModelResource.data.getResults()){
                                fourthHomeFragmentAdapter.add(movieListInfo);
                            }
                            break;
                        case ERROR:
                            Log.e(TAG, "onChanged: case error" );
                            Toast.makeText(getContext(), "Error...", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + movieListModelResource.status);
                    }
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        this.getData();
    }
}
