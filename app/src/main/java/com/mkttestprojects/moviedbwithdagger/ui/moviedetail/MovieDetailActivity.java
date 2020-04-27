package com.mkttestprojects.moviedbwithdagger.ui.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jgabrielfreitas.core.BlurImageView;
import com.mkttestprojects.moviedbwithdagger.common.BaseActivity;
import com.mkttestprojects.moviedbwithdagger.R;
import com.mkttestprojects.moviedbwithdagger.custom_control.BlurImage;
import com.mkttestprojects.moviedbwithdagger.custom_control.MyanProgressDialog;
import com.mkttestprojects.moviedbwithdagger.models.MovieListInfo;
import com.mkttestprojects.moviedbwithdagger.models.MovieListModel;
import com.mkttestprojects.moviedbwithdagger.models.RateRequestBody;
import com.mkttestprojects.moviedbwithdagger.models.RatingModel;
import com.mkttestprojects.moviedbwithdagger.models.WatchListModel;
import com.mkttestprojects.moviedbwithdagger.models.WatchListRequestBody;
import com.mkttestprojects.moviedbwithdagger.ui.MovieAdapter;
import com.mkttestprojects.moviedbwithdagger.ui.login.LoginActivity;
import com.mkttestprojects.moviedbwithdagger.ui.main.Resource;
import com.mkttestprojects.moviedbwithdagger.ui.movietrailer.PlayMovieTrailerActivity;
import com.mkttestprojects.moviedbwithdagger.util.SharePreferenceHelper;
import com.mkttestprojects.moviedbwithdagger.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import butterknife.BindView;

import static com.mkttestprojects.moviedbwithdagger.util.AppConstant.BASE_IMG_URL;

public class MovieDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MovieDetailActivity";

    @BindView(R.id.iv_poster)
    ImageView ivMoviePoster;

    @BindView(R.id.txt_releaseyear)
    TextView txtReleasedate;

    @BindView(R.id.txt_isAdult)
    TextView isAdult;

    @BindView(R.id.txt_duration)
    TextView txtDuration;

    @BindView(R.id.txt_movieOverview)
    TextView txtMovieOverview;

    @BindView(R.id.similar_movie_rv)
    RecyclerView rvSimilarMovie;

    @BindView(R.id.layout_play)
    LinearLayout layoutPlay;

    @BindView(R.id.layout_rating)
    LinearLayout layoutRating;

    @BindView(R.id.layout_mylist)
    LinearLayout layoutMylist;

    @BindView(R.id.txt_movietitle)
    TextView txtMovieTitle;

    @BindView(R.id.iv_poster_bg)
    BlurImageView ivPosterBG;

    @BindView(R.id.rb_movierating)
    RatingBar rbMovieRating;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    RequestManager requestManager;

    @Inject
    MovieAdapter adapter;

    private MovieDetailViewModel movieDetailViewModel;

    public static int mmovieId;

    private int runtime;
    private String duration;

    private MyanProgressDialog mdialog;

    private String moviePosterpath;

    private String sessionId;

    private SharePreferenceHelper mSharePreference;

    public static Intent MovieDetailActivityIntent(Context context, int movieId) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        mmovieId = movieId;
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
//        setupToolbar(true);
//        setupToolbarText("");
        movieDetailViewModel = new ViewModelProvider(this, providerFactory).get(MovieDetailViewModel.class);
        mdialog = new MyanProgressDialog(this);
        mSharePreference = new SharePreferenceHelper(this);
        sessionId = mSharePreference.getSessionId();
        init();
    }

    @Override
    protected void showLoading() {
        if (!isFinishing()) {
            mdialog.showDialog();
        }
    }

    @Override
    protected void hideloading() {
        if (!isFinishing()) {
            mdialog.hideDialog();
        }
    }

    @Override
    protected void showDialogMsg(String msg) {

    }

    private void init() {

        rvSimilarMovie.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvSimilarMovie.setAdapter(adapter);

        getMovieDetails(mmovieId);
        getSimilarMovies(mmovieId);

        layoutPlay.setOnClickListener(this);

        layoutMylist.setOnClickListener(this);

        layoutRating.setOnClickListener(this);
    }

    private void getSimilarMovies(int mmovieId) {
        movieDetailViewModel.observeSimilarMovies(mmovieId).observe(this, new Observer<Resource<MovieListModel>>() {
            @Override
            public void onChanged(Resource<MovieListModel> movieListModelResource) {
                if (movieListModelResource != null) {
                    switch (movieListModelResource.status) {
                        case LOADING:
                            adapter.showLoading();
                            break;
                        case SUCCESS:
                            adapter.clear();
                            for (MovieListInfo movieListInfo : movieListModelResource.data.getResults()) {
                                adapter.add(movieListInfo);
                            }
                            break;
                        case ERROR:
                            Log.e(TAG, "onChanged: error");
                            break;
                    }
                }
            }
        });
    }

    private void getMovieDetails(int mmovieId) {
        movieDetailViewModel.observeMovieDetails(mmovieId).observe(this, new Observer<Resource<MovieListModel>>() {
            @Override
            public void onChanged(Resource<MovieListModel> movieListModelResource) {
                if (movieListModelResource != null) {
                    switch (movieListModelResource.status) {
                        case LOADING:
                            showLoading();
                            Log.e(TAG, "onChanged: loading");
                            break;
                        case SUCCESS:
                            moviePosterpath = movieListModelResource.data.getPosterpath();

//                            requestManager
//                                    .load(BASE_IMG_URL + movieListModelResource.data.getPosterpath())
//                                    .into(ivPosterBG);
//                            ivPosterBG.setBlur(5);
//                            Bitmap bitmapImage = BlurImage.fastblur(BitmapFactory.decodeResource(getResources(), R.drawable.img_placeholder), (float) 0.08, 5);
//
//                            requestManager.load(bitmapImage)
//                                    .into(ivPosterBG);

//                            Blurry.with(MovieDetailActivity.this)
//                                    .from(BitmapFactory.decodeFile(BASE_IMG_URL+ movieListModelResource.data.getPosterpath()))
//                                    .into(ivPosterBG);

//                            requestManager
//                                    .load(BASE_IMG_URL + movieListModelResource.data.getPosterpath())
//                                    .apply(bitmapTransform(new BlurTransformation(500)))
//                                    .into(ivPosterBG);
//                            requestManager
//                                    .load(BASE_IMG_URL + movieListModelResource.data.getPosterpath())
//                                    .into(ivMoviePoster);
                            hideloading();
                            requestManager
                                    .asBitmap()
                                    .load(BASE_IMG_URL + movieListModelResource.data.getPosterpath())
                                    .into(new CustomTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            Bitmap bitmapImage = BlurImage.fastblur(resource, (float) 0.08, 5);
//                        movieDetailLayout.setBackground(new BitmapDrawable(getResources(), bitmapImage));

                                            requestManager
                                                    .load(resource)
                                                    .into(ivMoviePoster);

                                            requestManager
                                                    .load(bitmapImage)
                                                    .into(ivPosterBG);

                                        }

                                        @Override
                                        public void onLoadCleared(@Nullable Drawable placeholder) {
                                        }
                                    });
                            txtReleasedate.setText(movieListModelResource.data.getRelease_date());
                            if (movieListModelResource.data.isAdult()) {
                                isAdult.setText("18+");
                            } else {
                                isAdult.setText("");
                            }
                            runtime = movieListModelResource.data.getRuntime();
                            duration = runtime / 60 + "h " + runtime % 60 + "m";
                            txtDuration.setText(duration);
                            txtMovieOverview.setText(movieListModelResource.data.getOverview());
                            txtMovieTitle.setText(movieListModelResource.data.getTitle());
                            break;
                        case ERROR:
                            Log.e(TAG, "onChanged: error");
                            break;
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_play:
                startActivity(PlayMovieTrailerActivity.PlayMovieTrailerIntent(MovieDetailActivity.this, mmovieId));
                break;
            case R.id.layout_mylist:
                addToWatchList(sessionId, mmovieId);
                break;
            case R.id.layout_rating:
                setRating(sessionId, mmovieId);
                break;

        }
    }

    private void addToWatchList(String sessionId, int mmovieId) {
        if (mSharePreference.isLogin()) {
            movieDetailViewModel.observeWatchListPostData(sessionId, new WatchListRequestBody("movie", mmovieId, true))
                    .observe(this, new Observer<Resource<WatchListModel>>() {
                        @Override
                        public void onChanged(Resource<WatchListModel> watchListModelResource) {
                            if (watchListModelResource != null) {
                                switch (watchListModelResource.status) {
                                    case LOADING:
                                        mdialog.showDialog();
                                        break;
                                    case SUCCESS:
                                        mdialog.hideDialog();
                                        showToastMsg("Successfully added to watchlist");
                                        break;
                                    case ERROR:
                                        mdialog.hideDialog();
                                        showToastMsg("Error adding to watchlist");
                                        break;
                                }
                            }
                        }
                    });
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("You must login to add to watchlist");
            alertDialogBuilder.setPositiveButton("OK",
                    (arg0, arg1) -> startActivity(LoginActivity.LoginActivityIntent(this)));

            alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.dismiss();
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    private void showToastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    private void setRating(String sessionId, int mmovieId) {
        if (mSharePreference.isLogin()) {
            AlertDialog.Builder popupDialog = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View dialoglayout = inflater.inflate(R.layout.rating_dialog, null);
            RatingBar ratingBar = dialoglayout.findViewById(R.id.rb_rating);
            popupDialog.setView(dialoglayout);
            popupDialog.setTitle("Rating");
            popupDialog.setPositiveButton("Rate", (dialogInterface, i) -> {

                rbMovieRating.setVisibility(View.VISIBLE);
                rbMovieRating.setRating(ratingBar.getRating());

                RateRequestBody rateRequestBody = new RateRequestBody(ratingBar.getRating());

                movieDetailViewModel.observeRatingPostData(sessionId, mmovieId, rateRequestBody).observe(this, new Observer<Resource<RatingModel>>() {
                    @Override
                    public void onChanged(Resource<RatingModel> ratingModelResource) {
                        if (ratingModelResource != null) {
                            switch (ratingModelResource.status) {
                                case LOADING:
                                    mdialog.showDialog();
                                    break;
                                case SUCCESS:
                                    mdialog.hideDialog();
                                    showToastMsg("Rating successful");
                                    break;
                                case ERROR:
                                    mdialog.hideDialog();
                                    showToastMsg("Error rating");
                                    break;
                            }
                        }
                    }
                });

            });
            popupDialog.show();
        } else {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("You must login to add to watchlist");
            alertDialogBuilder.setPositiveButton("OK",
                    (arg0, arg1) -> startActivity(LoginActivity.LoginActivityIntent(this)));

            alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> {

            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }

    }
}
