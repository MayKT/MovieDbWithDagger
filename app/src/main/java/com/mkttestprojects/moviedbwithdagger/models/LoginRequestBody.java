package com.mkttestprojects.moviedbwithdagger.models;

public class LoginRequestBody {

    String username;
    String password;
    String request_token;

    public LoginRequestBody(String username, String password, String request_token) {
        this.username = username;
        this.password = password;
        this.request_token = request_token;
    }
}
