package com.mkttestprojects.moviedbwithdagger.ui.main.mylist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.mkttestprojects.moviedbwithdagger.models.MovieListModel;
import com.mkttestprojects.moviedbwithdagger.network.main.mylist.MyListApi;
import com.mkttestprojects.moviedbwithdagger.ui.main.Resource;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.mkttestprojects.moviedbwithdagger.util.AppConstant.DEVELOPER_KEY;

public class MyListFragmentViewModel extends ViewModel {

    private MediatorLiveData<Resource<MovieListModel>> mywatchlist;
    private MediatorLiveData<Resource<MovieListModel>> ratedmovies;
    private MyListApi myListApi;

    @Inject
    public  MyListFragmentViewModel(MyListApi myListApi){
        this.myListApi = myListApi;
    }

    public LiveData<Resource<MovieListModel>> observeWatchList(String sessionId){
        mywatchlist = new MediatorLiveData<>();
        mywatchlist.setValue(Resource.loading(null));
        final LiveData<Resource<MovieListModel>> resourceLiveData = LiveDataReactiveStreams.fromPublisher(
                myListApi.getWatchListMovies(DEVELOPER_KEY,sessionId)
                .onErrorReturn(new Function<Throwable, MovieListModel>() {
                    @Override
                    public MovieListModel apply(Throwable throwable) throws Exception {
                        return null;
                    }
                })
                .map(new Function<MovieListModel, Resource<MovieListModel>>() {
                    @Override
                    public Resource<MovieListModel> apply(MovieListModel movieListModel) throws Exception {
                        if(movieListModel != null){
                            return Resource.success(movieListModel);
                        }
                        return Resource.error("Something went wrong",null);
                    }
                })
                .subscribeOn(Schedulers.io())
        );
        mywatchlist.addSource(resourceLiveData, new Observer<Resource<MovieListModel>>() {
            @Override
            public void onChanged(Resource<MovieListModel> movieListModelResource) {
                mywatchlist.setValue(movieListModelResource);
                mywatchlist.removeSource(resourceLiveData);
            }
        });
        return mywatchlist;
    }

    public LiveData<Resource<MovieListModel>> observeRatedMovie(String sessionID){
        ratedmovies = new MediatorLiveData<>();
        ratedmovies.setValue(Resource.loading(null));
        final LiveData<Resource<MovieListModel>> resourceLiveData = LiveDataReactiveStreams.fromPublisher(
                myListApi.getRatedMovies(DEVELOPER_KEY,sessionID)
                .onErrorReturn(new Function<Throwable, MovieListModel>() {
                    @Override
                    public MovieListModel apply(Throwable throwable) throws Exception {
                        return null;
                    }
                })
                .map(new Function<MovieListModel, Resource<MovieListModel>>() {
                    @Override
                    public Resource<MovieListModel> apply(MovieListModel movieListModel) throws Exception {
                        if(movieListModel != null){
                            return Resource.success(movieListModel);
                        }
                        return Resource.error("Something went wrong",null);
                    }
                })
                .subscribeOn(Schedulers.io())
        );
        ratedmovies.addSource(resourceLiveData, new Observer<Resource<MovieListModel>>() {
            @Override
            public void onChanged(Resource<MovieListModel> movieListModelResource) {
                ratedmovies.setValue(movieListModelResource);
                ratedmovies.removeSource(resourceLiveData);
            }
        });
        return ratedmovies;
    }

}
