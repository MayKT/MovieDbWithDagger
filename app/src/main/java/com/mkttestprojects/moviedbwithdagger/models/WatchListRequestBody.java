package com.mkttestprojects.moviedbwithdagger.models;

public class WatchListRequestBody {

    String media_type;
    int media_id;
    boolean watchlist;

    public WatchListRequestBody(String media_type, int media_id, boolean watchlist){
        this.media_type=media_type;
        this.media_id=media_id;
        this.watchlist=watchlist;
    }
}
