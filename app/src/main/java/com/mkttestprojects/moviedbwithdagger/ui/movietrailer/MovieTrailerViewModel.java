package com.mkttestprojects.moviedbwithdagger.ui.movietrailer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.mkttestprojects.moviedbwithdagger.models.MovieTrailerModel;
import com.mkttestprojects.moviedbwithdagger.network.moviedetail.MovieTrailerApi;
import com.mkttestprojects.moviedbwithdagger.ui.main.Resource;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.mkttestprojects.moviedbwithdagger.util.AppConstant.DEVELOPER_KEY;

public class MovieTrailerViewModel extends ViewModel {

    private MovieTrailerApi movieTrailerApi;
MediatorLiveData<Resource<MovieTrailerModel>> movietrailer;

@Inject
    public MovieTrailerViewModel(MovieTrailerApi movieTrailerApi) {
        this.movieTrailerApi = movieTrailerApi;
    }

    public LiveData<Resource<MovieTrailerModel>> observeMovieTrailer(int movieId){
    if(movietrailer == null){
        movietrailer = new MediatorLiveData<>();
        movietrailer.setValue(Resource.loading(null));
        final LiveData<Resource<MovieTrailerModel>> resourceLiveData = LiveDataReactiveStreams.fromPublisher(
                movieTrailerApi.getMovieTrailer(movieId,DEVELOPER_KEY)
                .onErrorReturn(new Function<Throwable, MovieTrailerModel>() {
                    @Override
                    public MovieTrailerModel apply(Throwable throwable) throws Exception {
                        return null;
                    }
                })
                .map(new Function<MovieTrailerModel, Resource<MovieTrailerModel>>() {
                    @Override
                    public Resource<MovieTrailerModel> apply(MovieTrailerModel movieTrailerModel) throws Exception {
                        if(movieTrailerModel == null){
                            return Resource.error("Something went wrong",null);
                        }
                        return Resource.success(movieTrailerModel);
                    }
                })
                .subscribeOn(Schedulers.io())
        );
        movietrailer.addSource(resourceLiveData, new Observer<Resource<MovieTrailerModel>>() {
            @Override
            public void onChanged(Resource<MovieTrailerModel> movieTrailerModelResource) {
                movietrailer.setValue(movieTrailerModelResource);
                movietrailer.removeSource(resourceLiveData);
            }
        });
    }
    return movietrailer;
    }
}
