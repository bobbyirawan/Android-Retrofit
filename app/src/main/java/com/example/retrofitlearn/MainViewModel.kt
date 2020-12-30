package com.example.retrofitlearn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitlearn.model.PostsWithoutArrayModel
import com.example.retrofitlearn.model.posts.Posts
import com.example.retrofitlearn.model.soal.SoalTest
import com.example.retrofitlearn.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {

    val postsResponse: MutableLiveData<Response<Posts>> = MutableLiveData()
    val postsWithPathResponse: MutableLiveData<Response<PostsWithoutArrayModel>> = MutableLiveData()
    val customPostsResponse: MutableLiveData<Response<Posts>> = MutableLiveData()
    val customPostsResponse2: MutableLiveData<Response<Posts>> = MutableLiveData()

    // get soal netha
    val takeQuis: MutableLiveData<Response<SoalTest>> = MutableLiveData()
    fun getTest() {
        viewModelScope.launch {
            val response = repository.getTest()
            takeQuis.value = response
        }
    }

    //    post
    fun pushPost(post: PostsWithoutArrayModel) {
        viewModelScope.launch {
            val response = repository.pushPost(post)
            postsWithPathResponse.value = response
        }
    }

    fun pushPost2(userId: Int, id: Int, title: String, body: String) {
        viewModelScope.launch {
            val response = repository.pushPost2(userId, id, title, body)
            postsWithPathResponse.value = response
        }
    }

    //    get
    fun getPosts() {
        viewModelScope.launch {
            val response = repository.getPosts()
            postsResponse.value = response
        }
    }

    fun getPosts2(number: Int) {
        viewModelScope.launch {
            val response = repository.getPosts2(number)
            postsWithPathResponse.value = response
        }
    }

    fun getCustomPosts(userId: Int, sort: String, order: String) {
        viewModelScope.launch {
            val response = repository.getCustomPosts(userId, sort, order)
            customPostsResponse.value = response
        }
    }

    fun getCustomPosts2(userId: Int, options: Map<String, String>) {
        viewModelScope.launch {
            val response = repository.getCustomPosts2(userId, options)
            customPostsResponse2.value = response
        }
    }
}