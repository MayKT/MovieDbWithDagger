package com.mkttestprojects.moviedbwithdagger.models;

import com.mkttestprojects.moviedbwithdagger.Pageable;

import java.io.Serializable;

public class AccountModel implements Serializable, Pageable {
    int id;
    String name;
    String username;

    public AccountModel(int id, String name){
        this.id=id;
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
