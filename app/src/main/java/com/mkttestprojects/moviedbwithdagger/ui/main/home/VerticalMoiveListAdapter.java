package com.mkttestprojects.moviedbwithdagger.ui.main.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mkttestprojects.moviedbwithdagger.common.BaseAdapter;
import com.mkttestprojects.moviedbwithdagger.R;
import com.mkttestprojects.moviedbwithdagger.models.MovieListInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mkttestprojects.moviedbwithdagger.util.AppConstant.BASE_IMG_URL;

public class VerticalMoiveListAdapter extends BaseAdapter {

    private static final String TAG = "VerticalMoiveListAdapte";

    @Override
    protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizonal_movie_item, parent, false);
        return new VerticalMoiveListAdapter.ViewHolder(view);
    }

    @Override
    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((VerticalMoiveListAdapter.ViewHolder) holder).bind((MovieListInfo) getItemsList().get(position));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindCustomHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Context context;
        @BindView(R.id.card_view_home)
        CardView cardView;

        @BindView(R.id.image_view_movie)
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            context = view.getContext();
            ButterKnife.bind(this, view);

        }

        public void bind(MovieListInfo movieListInfo) {
            Log.e(TAG, "bind: " );
            Glide.with(context).load(BASE_IMG_URL + movieListInfo.getPosterpath()).into(imageView);

            itemView.setOnClickListener(v -> {
//                context.startActivity(MovieDetailActivity.MovieDetailActivityIntent(context, model.getId()));
            });
        }
    }
}
