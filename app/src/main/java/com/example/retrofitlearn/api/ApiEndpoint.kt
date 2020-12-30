package com.example.retrofitlearn.api

import com.example.retrofitlearn.model.PostsWithoutArrayModel
import com.example.retrofitlearn.model.posts.Posts
import retrofit2.Response
import retrofit2.http.*

interface ApiEndpoint {

    //Model posts menghasilkan nilai aray
    //Model postWithPath nilai satuan.

    // example https://jsonplaceholder.typicode.com/posts
    @GET("posts")
    suspend fun getPosts(): Response<Posts>

    // example https://jsonplaceholder.typicode.com/posts/3
    @GET("posts/{numberPath}")
    suspend fun getPosts2(
        @Path("numberPath") number: Int
    ): Response<PostsWithoutArrayModel>

    // example https://jsonplaceholder.typicode.com/posts?userId=3
    // example https://jsonplaceholder.typicode.com/posts?userId=3&_sort=id&_order=desc
    @GET("posts")
    suspend fun getCustomPosts(
        @Query("userId") userId: Int,
        @Query("_sort") sort: String,
        @Query("_order") desc: String
    ): Response<Posts>

    // example https://jsonplaceholder.typicode.com/posts?userId=3&_sort=id&_order=desc
    @GET("posts")
    suspend fun getCustomPosts2(
        @Query("userId") userId: Int,
        @QueryMap options: Map<String, String>
    ): Response<Posts>


    //    POST
    @POST("posts")
    suspend fun pushPost(
        @Body post: PostsWithoutArrayModel
    ): Response<PostsWithoutArrayModel>

    @FormUrlEncoded
    @POST("posts")
    suspend fun pushPost2(
        @Field("userId") userId: Int,
        @Field("id") id: Int,
        @Field("title") title: String,
        @Field("body") body: String
        ): Response<PostsWithoutArrayModel>
}
