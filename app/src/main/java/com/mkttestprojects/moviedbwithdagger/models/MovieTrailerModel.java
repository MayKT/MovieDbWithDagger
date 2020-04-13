package com.mkttestprojects.moviedbwithdagger.models;

import com.mkttestprojects.moviedbwithdagger.Pageable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovieTrailerModel implements Serializable, Pageable {
    int id;
    List<MovieTrailerInfoModel> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setResults(ArrayList<MovieTrailerInfoModel> results) {
        this.results = results;
    }

    public List<MovieTrailerInfoModel> getResults() {
        return results;
    }
}