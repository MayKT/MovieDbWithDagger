package com.mkttestprojects.moviedbwithdagger.ui.movietrailer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mkttestprojects.moviedbwithdagger.BaseActivity;
import com.mkttestprojects.moviedbwithdagger.R;
import com.mkttestprojects.moviedbwithdagger.models.MovieTrailerModel;
import com.mkttestprojects.moviedbwithdagger.ui.main.Resource;
import com.mkttestprojects.moviedbwithdagger.viewmodels.ViewModelProviderFactory;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import butterknife.BindView;

public class PlayMovieTrailerActivity extends BaseActivity {

    private static final String TAG = "PlayMovieTrailerActivit";
    
    @BindView(R.id.youtube_player)
    YouTubePlayerView youtubePlayer;

    @Inject
    ViewModelProviderFactory providerFactory;

    private MovieTrailerViewModel movieTrailerViewModel;

    public static int mmovieId;

    public static Intent PlayMovieTrailerIntent(Context context,int moiveId){
        Intent intent = new Intent(context,PlayMovieTrailerActivity.class);
        mmovieId = moiveId;
        return intent;
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_play_movie_trailer;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        movieTrailerViewModel = new ViewModelProvider(this,providerFactory).get(MovieTrailerViewModel.class);
        init();
    }

    private void init() {
        movieTrailerViewModel.observeMovieTrailer(mmovieId).observe(this, new Observer<Resource<MovieTrailerModel>>() {
            @Override
            public void onChanged(Resource<MovieTrailerModel> movieTrailerModelResource) {
                if(movieTrailerModelResource != null){
                    switch (movieTrailerModelResource.status){
                        case LOADING:
                            showLoading();
                            break;
                        case SUCCESS:
                            hideloading();
                            String movieid = movieTrailerModelResource.data.getResults().get(0).getKey();
                            getLifecycle().addObserver(youtubePlayer);
                            youtubePlayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                                @Override
                                public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                                    youTubePlayer.loadVideo(movieid,0);
                                }
                            });
                            break;
                        case ERROR:
                            Log.e(TAG, "onChanged: error trailer video" );
                            break;
                    }
                }
            }
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
}
