package com.mkttestprojects.moviedbwithdagger.models;

import java.io.Serializable;

public class RateRequestBody implements Serializable,Pageable {
    float value;
    public RateRequestBody(float value){
        this.value=value;
    }
}
