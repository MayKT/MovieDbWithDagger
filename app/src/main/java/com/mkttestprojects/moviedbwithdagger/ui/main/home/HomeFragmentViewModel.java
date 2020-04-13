package com.mkttestprojects.moviedbwithdagger.ui.main.home;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.mkttestprojects.moviedbwithdagger.models.MovieListModel;
import com.mkttestprojects.moviedbwithdagger.network.main.MainApi;
import com.mkttestprojects.moviedbwithdagger.ui.main.Resource;
import javax.inject.Inject;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import static com.mkttestprojects.moviedbwithdagger.util.AppConstant.DEVELOPER_KEY;

public class HomeFragmentViewModel extends ViewModel {

    private static final String TAG = "HomeFragmentViewModel";

    private MainApi mainApi;

    private MediatorLiveData<Resource<MovieListModel>> nowPlayingMovieLists;
    private MediatorLiveData<Resource<MovieListModel>> trendingMovieLists;
    private MediatorLiveData<Resource<MovieListModel>> upcomingMovieLists;
    private MediatorLiveData<Resource<MovieListModel>> topRatedMovieLists;


    @Inject
    public HomeFragmentViewModel(MainApi mainApi) {
        this.mainApi = mainApi;
        Log.e(TAG, "HomeFragmentViewModel: View Model is working");
    }

    public LiveData<Resource<MovieListModel>> observeNowPlayingMovieLists() {
        if (nowPlayingMovieLists == null) {
            nowPlayingMovieLists = new MediatorLiveData<>();
            nowPlayingMovieLists.setValue(Resource.loading(null));
            final LiveData<Resource<MovieListModel>> resourceLiveData = LiveDataReactiveStreams.fromPublisher(
                    mainApi.getNowPlayingMovies(DEVELOPER_KEY, "en-US", 1)
                            .onErrorReturn(new Function<Throwable, MovieListModel>() {
                                @Override
                                public MovieListModel apply(Throwable throwable) throws Exception {
                                    Log.e(TAG, "apply: observeNowPlayingMovieLists error" );
                                    return null;
                                }
                            })
                            .map(new Function<MovieListModel, Resource<MovieListModel>>() {
                                @Override
                                public Resource<MovieListModel> apply(MovieListModel movieListModel) throws Exception {
                                    Log.e(TAG, "apply: map" );
                                    if (movieListModel != null) {
                                        if (movieListModel.getResults().isEmpty()) {
                                            return Resource.error("Something went wrong", null);
                                        }
                                    }
                                    return Resource.success(movieListModel);
                                }
                            })
                            .subscribeOn(Schedulers.io())
            );
            nowPlayingMovieLists.addSource(resourceLiveData, new Observer<Resource<MovieListModel>>() {
                @Override
                public void onChanged(Resource<MovieListModel> movieListModelResource) {
                    nowPlayingMovieLists.setValue(movieListModelResource);
                    nowPlayingMovieLists.removeSource(resourceLiveData);
                }
            });
        }
        return nowPlayingMovieLists;
    }

    public LiveData<Resource<MovieListModel>> observeTrendingMovieLists() {
        if (trendingMovieLists == null) {
            trendingMovieLists = new MediatorLiveData<>();
            trendingMovieLists.setValue(Resource.loading(null));
            final LiveData<Resource<MovieListModel>> resourceLiveData = LiveDataReactiveStreams.fromPublisher(
                    mainApi.getTrendingMovies(DEVELOPER_KEY, "en-US", 1)
                            .onErrorReturn(new Function<Throwable, MovieListModel>() {
                                @Override
                                public MovieListModel apply(Throwable throwable) throws Exception {
                                    Log.e(TAG, "apply: observeNowPlayingMovieLists error" );
                                    return null;
                                }
                            })
                            .map(new Function<MovieListModel, Resource<MovieListModel>>() {
                                @Override
                                public Resource<MovieListModel> apply(MovieListModel movieListModel) throws Exception {
                                    Log.e(TAG, "apply: map" );
                                    if (movieListModel != null) {
                                        if (movieListModel.getResults().isEmpty()) {
                                            return Resource.error("Something went wrong", null);
                                        }
                                    }
                                    return Resource.success(movieListModel);
                                }
                            })
                            .subscribeOn(Schedulers.io())
            );
            trendingMovieLists.addSource(resourceLiveData, new Observer<Resource<MovieListModel>>() {
                @Override
                public void onChanged(Resource<MovieListModel> movieListModelResource) {
                    trendingMovieLists.setValue(movieListModelResource);
                    trendingMovieLists.removeSource(resourceLiveData);
                }
            });
        }
        return trendingMovieLists;
    }

    public LiveData<Resource<MovieListModel>> observeUpcominMovieLists() {
        if (upcomingMovieLists == null) {
            upcomingMovieLists = new MediatorLiveData<>();
            upcomingMovieLists.setValue(Resource.loading(null));
            final LiveData<Resource<MovieListModel>> resourceLiveData = LiveDataReactiveStreams.fromPublisher(
                    mainApi.getUpComingMovies(DEVELOPER_KEY, "en-US", 1)
                            .onErrorReturn(new Function<Throwable, MovieListModel>() {
                                @Override
                                public MovieListModel apply(Throwable throwable) throws Exception {
                                    Log.e(TAG, "apply: observeNowPlayingMovieLists error" );
                                    return null;
                                }
                            })
                            .map(new Function<MovieListModel, Resource<MovieListModel>>() {
                                @Override
                                public Resource<MovieListModel> apply(MovieListModel movieListModel) throws Exception {
                                    Log.e(TAG, "apply: map" );
                                    if (movieListModel != null) {
                                        if (movieListModel.getResults().isEmpty()) {
                                            return Resource.error("Something went wrong", null);
                                        }
                                    }
                                    return Resource.success(movieListModel);
                                }
                            })
                            .subscribeOn(Schedulers.io())
            );
            upcomingMovieLists.addSource(resourceLiveData, new Observer<Resource<MovieListModel>>() {
                @Override
                public void onChanged(Resource<MovieListModel> movieListModelResource) {
                    upcomingMovieLists.setValue(movieListModelResource);
                    upcomingMovieLists.removeSource(resourceLiveData);
                }
            });
        }
        return upcomingMovieLists;
    }

    public LiveData<Resource<MovieListModel>> observeTopRatedMovieLists() {
        if (topRatedMovieLists == null) {
            topRatedMovieLists = new MediatorLiveData<>();
            topRatedMovieLists.setValue(Resource.loading(null));
            final LiveData<Resource<MovieListModel>> resourceLiveData = LiveDataReactiveStreams.fromPublisher(
                    mainApi.getTopRatedMovies(DEVELOPER_KEY, "en-US", 1)
                            .onErrorReturn(new Function<Throwable, MovieListModel>() {
                                @Override
                                public MovieListModel apply(Throwable throwable) throws Exception {
                                    Log.e(TAG, "apply: observeNowPlayingMovieLists error" );
                                    return null;
                                }
                            })
                            .map(new Function<MovieListModel, Resource<MovieListModel>>() {
                                @Override
                                public Resource<MovieListModel> apply(MovieListModel movieListModel) throws Exception {
                                    Log.e(TAG, "apply: map" );
                                    if (movieListModel != null) {
                                        if (movieListModel.getResults().isEmpty()) {
                                            return Resource.error("Something went wrong", null);
                                        }
                                    }
                                    return Resource.success(movieListModel);
                                }
                            })
                            .subscribeOn(Schedulers.io())
            );
            topRatedMovieLists.addSource(resourceLiveData, new Observer<Resource<MovieListModel>>() {
                @Override
                public void onChanged(Resource<MovieListModel> movieListModelResource) {
                    topRatedMovieLists.setValue(movieListModelResource);
                    topRatedMovieLists.removeSource(resourceLiveData);
                }
            });
        }
        return topRatedMovieLists;
    }
}
