package com.mkttestprojects.moviedbwithdagger.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.mkttestprojects.moviedbwithdagger.BaseAdapter;
import com.mkttestprojects.moviedbwithdagger.R;
import com.mkttestprojects.moviedbwithdagger.models.MovieListInfo;
import com.mkttestprojects.moviedbwithdagger.ui.moviedetail.MovieDetailActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mkttestprojects.moviedbwithdagger.util.AppConstant.BASE_IMG_URL;

public class MovieAdapter extends BaseAdapter {

    private static final String TAG = "HomeFragmentAdapter";

    private MovieListInfo posts;

    @Override
    protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizonal_movie_item, parent, false);
        return new MovieAdapter.ViewHolder(view);
    }

    @Override
    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MovieAdapter.ViewHolder) holder).bind((MovieListInfo) getItemsList().get(position));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindCustomHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
    class ViewHolder extends RecyclerView.ViewHolder {

        Context context;

        @Inject
        RequestManager requestManager;

        @BindView(R.id.card_view_home)
        CardView item;

        @BindView(R.id.ll_item)
        LinearLayout llItem;

        @BindView(R.id.image_view_movie)
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            context = view.getContext();
            ButterKnife.bind(this, view);

        }

        public void bind(MovieListInfo movieListInfo) {
            Display display = ((Activity) item.getContext()).getWindowManager().getDefaultDisplay();
            Point size = new Point();
            try {
                display.getRealSize(size);
            } catch (NoSuchMethodError err) {
                display.getSize(size);
            }
            int width = size.x;

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((width/3),
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 0, 0);
            llItem.setLayoutParams(lp);
            Glide.with(context).load(BASE_IMG_URL + movieListInfo.getPosterpath()).into(imageView);

            itemView.setOnClickListener(v -> {
                context.startActivity(MovieDetailActivity.MovieDetailActivityIntent(context, movieListInfo.getId()));
            });
        }
    }

}
