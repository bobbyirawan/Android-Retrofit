package com.example.retrofitlearn.repository

import com.example.retrofitlearn.api.ApiInstance
import com.example.retrofitlearn.model.PostsWithoutArrayModel
import com.example.retrofitlearn.model.posts.Posts
import com.example.retrofitlearn.model.soal.SoalTest
import retrofit2.Response

class Repository {

    suspend fun getPosts(): Response<Posts> {
        return ApiInstance.api.getPosts()
    }

    suspend fun getPosts2(number: Int): Response<PostsWithoutArrayModel> {
        return ApiInstance.api.getPosts2(number)
    }

    suspend fun getCustomPosts(userId: Int, sort: String, order: String): Response<Posts> {
        return ApiInstance.api.getCustomPosts(userId, sort, order)
    }

    suspend fun getCustomPosts2(userId: Int, options: Map<String, String>): Response<Posts> {
        return ApiInstance.api.getCustomPosts2(userId, options)
    }

    suspend fun pushPost(post: PostsWithoutArrayModel): Response<PostsWithoutArrayModel> {
        return ApiInstance.api.pushPost(post)
    }

    suspend fun pushPost2(
        userId: Int,
        id: Int,
        title: String,
        body: String
    ): Response<PostsWithoutArrayModel> {
        return ApiInstance.api.pushPost2(userId, id, title, body)
    }

    // untuk soal netha
    suspend fun getTest(): Response<SoalTest> {
        return ApiInstance.soalApi.getTest()
    }

}