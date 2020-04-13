package com.mkttestprojects.moviedbwithdagger.ui.moviedetail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.mkttestprojects.moviedbwithdagger.R;
import com.mkttestprojects.moviedbwithdagger.models.MovieListModel;
import com.mkttestprojects.moviedbwithdagger.network.moviedetail.MovieDetailApi;
import com.mkttestprojects.moviedbwithdagger.ui.main.Resource;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.mkttestprojects.moviedbwithdagger.util.AppConstant.DEVELOPER_KEY;

public class MovieDetailViewModel extends ViewModel {

    private static final String TAG = "MovieDetailViewModel";
    private MovieDetailApi movieDetailApi;

    private MediatorLiveData<Resource<MovieListModel>> moviedetails;
    private MediatorLiveData<Resource<MovieListModel>> similarmovies;

    @Inject
    public MovieDetailViewModel(MovieDetailApi movieDetailApi) {
        this.movieDetailApi = movieDetailApi;
        Log.e(TAG, "MovieDetailViewModel: View Model is working" );
    }


    public LiveData<Resource<MovieListModel>> observeMovieDetails(int movieId){
        if(moviedetails == null){
            moviedetails = new MediatorLiveData<>();
            moviedetails.setValue(Resource.loading(null));
            final LiveData<Resource<MovieListModel>> resourceLiveData = LiveDataReactiveStreams.fromPublisher(
                    movieDetailApi.getMovieDetails(movieId,DEVELOPER_KEY)
                    .onErrorReturn(new Function<Throwable, MovieListModel>() {
                        @Override
                        public MovieListModel apply(Throwable throwable) throws Exception {
                            Log.e(TAG, "apply: error getting movie detail " );
                            return null;
                        }
                    })
                    .map(new Function<MovieListModel, Resource<MovieListModel>>() {
                        @Override
                        public Resource<MovieListModel> apply(MovieListModel movieListModel) throws Exception {
                            if(movieListModel == null){
                                    return Resource.error("Something went wrong",null);
                            }
                                return Resource.success(movieListModel);

                        }
                    })
                    .subscribeOn(Schedulers.io())
            );
            moviedetails.addSource(resourceLiveData, new Observer<Resource<MovieListModel>>() {
                @Override
                public void onChanged(Resource<MovieListModel> movieListModelResource) {
                    moviedetails.setValue(movieListModelResource);
                    moviedetails.removeSource(resourceLiveData);
                }
            });
        }
        return moviedetails;
    }

    public LiveData<Resource<MovieListModel>> observeSimilarMovies(int movieID){
        if(similarmovies == null){
            similarmovies = new MediatorLiveData<>();
            similarmovies.setValue(Resource.loading(null));
            final LiveData<Resource<MovieListModel>> resourceLiveData = LiveDataReactiveStreams.fromPublisher(
                    movieDetailApi.getSimilarMoives(movieID,DEVELOPER_KEY)
                    .onErrorReturn(new Function<Throwable, MovieListModel>() {
                        @Override
                        public MovieListModel apply(Throwable throwable) throws Exception {
                            return null;
                        }
                    })
                    .map(new Function<MovieListModel, Resource<MovieListModel>>() {
                        @Override
                        public Resource<MovieListModel> apply(MovieListModel movieListModel) throws Exception {
                            if(movieListModel == null)
                            {
                                return Resource.error("Something went wrong",null);
                            }
                               return Resource.success(movieListModel);
                        }
                    })
                    .subscribeOn(Schedulers.io())
            );
            similarmovies.addSource(resourceLiveData, new Observer<Resource<MovieListModel>>() {
                @Override
                public void onChanged(Resource<MovieListModel> movieListModelResource) {
                    similarmovies.setValue(movieListModelResource);
                    similarmovies.removeSource(resourceLiveData);
                }
            });
        }
        return similarmovies;
    }
}
