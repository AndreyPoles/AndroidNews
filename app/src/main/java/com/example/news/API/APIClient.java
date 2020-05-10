package com.example.news.API;

import com.example.news.API.Models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIClient {

    @GET("/")
    Call<List<Post>> getPosts();

}
