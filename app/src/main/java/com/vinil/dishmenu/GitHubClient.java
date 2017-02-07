package com.vinil.dishmenu;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by vinil on 6/2/17.
 */
public interface GitHubClient {

    @GET("/user/{user}/repos")
    Call<List<GitHubRepo>> reposForUser(@Path("user") String user);
}
