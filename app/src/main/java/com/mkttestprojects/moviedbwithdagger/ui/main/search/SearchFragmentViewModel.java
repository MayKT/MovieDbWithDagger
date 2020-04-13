package com.mkttestprojects.moviedbwithdagger.ui.main.search;

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

public class SearchFragmentViewModel extends ViewModel {

    private MediatorLiveData<Resource<MovieListModel>> searchResults;
    private MainApi mainApi;

    @Inject
    public SearchFragmentViewModel(MainApi mainApi) {
        this.mainApi= mainApi;
    }

    public LiveData<Resource<MovieListModel>> observeSearchResult(String query){
        if(searchResults == null){
            searchResults = new MediatorLiveData<>();
            searchResults.setValue(Resource.loading(null));
            final LiveData<Resource<MovieListModel>> resourceLiveData = LiveDataReactiveStreams.fromPublisher(
                    mainApi.getSearchResults(DEVELOPER_KEY,"en-US",query,1)
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
                                        {
                                           if(movieListModel.getResults().isEmpty()){
                                               return Resource.error("Something went wrong",null);
                                           }
                                        }

                                    }
                                    return Resource.success(movieListModel);
                                }
                            })
                            .subscribeOn(Schedulers.io())
            );
            searchResults.addSource(resourceLiveData, new Observer<Resource<MovieListModel>>() {
                @Override
                public void onChanged(Resource<MovieListModel> movieListModelResource) {
                    searchResults.setValue(movieListModelResource);
                    searchResults.removeSource(resourceLiveData);
                }
            });
        }
        return searchResults;
    }

}
