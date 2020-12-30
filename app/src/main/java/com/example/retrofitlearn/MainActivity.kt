package com.example.retrofitlearn

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitlearn.adapter.PostAdapter
import com.example.retrofitlearn.databinding.ActivityMainBinding
import com.example.retrofitlearn.model.PostsWithoutArrayModel
import com.example.retrofitlearn.model.posts.Posts
import com.example.retrofitlearn.repository.Repository

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: MainViewModel
    private val tag: String = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private val postAdapter by lazy { PostAdapter() }

    companion object {
        private const val STATE_RESULT = "state_result"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.pushPost2(2, 2, "Boboy", "Software Developer")
        viewModel.postsWithPathResponse.observe(this, Observer {
            if(it.isSuccessful) {
                Log.d("Main", it.body().toString())
                Log.d("Main", it.code().toString())
                Log.d("Main", "mantap")
            }else {
                Toast.makeText(this, it.code(), Toast.LENGTH_SHORT).show()
            }
        })


        getCustomPosts(2, "id", "desc")
        binding.btnGet.setOnClickListener(this)
        if (savedInstanceState != null) {
            val result = savedInstanceState.getString(STATE_RESULT)
            binding.tvResponseText.text = result
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_RESULT, binding.tvResponseText.text.toString())
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_get) {
            val number = binding.edtNumberPath.text.toString()

            getCustomPosts(Integer.parseInt(number), "id", "asc")

            getDataPostsWithPath(Integer.parseInt(number))

            val options: HashMap<String, String> = HashMap()
            options["_sort"] = "id"
            options["_order"] = "desc"
            getCustomPosts2(Integer.parseInt(number), options)
        }
    }


    private fun getDataPosts() {
        viewModel.getPosts()
        viewModel.postsResponse.observe(this, { response ->
            if (response.isSuccessful) {
                val result = response.body()
                showPosts(result!!)
            } else {
                showLog(response.errorBody().toString())
            }
        })
    }

    private fun getDataPostsWithPath(number: Int) {
        viewModel.getPosts2(number)
        viewModel.postsWithPathResponse.observe(this, { response ->
            if (response.isSuccessful) {
                val result = response.body().toString()
                binding.tvResponseText.text = result

            } else {
                showLog(response.errorBody().toString())
            }

        })
    }

    private fun getCustomPosts(number: Int, sort: String, order: String) {
        viewModel.getCustomPosts(number, sort, order)
        viewModel.customPostsResponse.observe(this, { response ->
            if (response.isSuccessful) {
                val result = response.body()
                binding.tvResponseText.text = result.toString()
                result?.let { postAdapter.setData(it) }
                showPosts(result!!)
            } else {
                showLog(response.errorBody().toString())
            }
        })
    }

    private fun getCustomPosts2(number: Int, options: Map<String, String>) {
        viewModel.getCustomPosts(number, options)
        viewModel.customPostsResponse2.observe(this, { response ->
            if (response.isSuccessful) {
                val result = response.body()
                binding.tvResponseText.text = result.toString()
                showPosts(result!!)
            } else {
                showLog(response.errorBody().toString())
            }
        })
    }

    private fun setUpRecyclerView() {
        binding.rvMainActivity.adapter = postAdapter
        binding.rvMainActivity.layoutManager = LinearLayoutManager(this)
    }

    private fun showPosts(posts: Posts) {
        posts.forEach {
            showLog("USER ID: ${it.userId}")
            showLog("ID: ${it.id}")
            showLog("TITLE: ${it.title}")
            showLog("BODY: ${it.body}")
        }
    }

    private fun showLog(message: String) {
        Log.d(tag, message)
    }


}