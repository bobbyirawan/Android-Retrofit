package com.example.retrofitlearn

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitlearn.adapter.PostAdapter
import com.example.retrofitlearn.databinding.ActivityMainBinding
import com.example.retrofitlearn.model.posts.Posts
import com.example.retrofitlearn.repository.Repository

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: MainViewModel
    private val tag: String = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private val postAdapter by lazy { PostAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getTest()
        viewModel.takeQuis.observe(this, {
            if (it.isSuccessful) {
                Log.d(tag, it.body().toString())
                Log.d(tag, it.code().toString())
                Log.d(tag, it.headers().toString())
            } else {
                Log.d(tag, it.headers().toString())
            }
        })


//        viewModel.pushPost2(2, 2, "Boboy", "Software Developer")
//        viewModel.postsWithPathResponse.observe(this, Observer {
//            if(it.isSuccessful) {
//                Log.d("Main", it.body().toString())
//                Log.d("Main", it.code().toString())
//                Log.d("Main", "mantap")
//            }else {
//                Toast.makeText(this, it.code(), Toast.LENGTH_SHORT).show()
//            }
//        })
//
//
//        getCustomPosts(2, "id", "desc")

        binding.btnGet.setOnClickListener(this)
        getCustomPosts2()
        getCustomPosts()

    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_get) {
            val number = binding.edtNumberPath.text.toString()

            val options: HashMap<String, String> = HashMap()
            options["_sort"] = "id"
            options["_order"] = "desc"
            viewModel.getCustomPosts2(Integer.parseInt(number), options)
            getCustomPosts2()
            viewModel.getCustomPosts(Integer.parseInt(number), "id", "asc")
            getCustomPosts()
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

    private fun getCustomPosts() {
        viewModel.customPostsResponse.observe(this, { response ->
            if (response.isSuccessful) {
                val result = response.body()
//                binding.tvResponseText.text = result.toString()
                result?.let { postAdapter.setData(it) }
                showPosts(result!!)
            } else {
                showLog(response.errorBody().toString())
            }
        })
    }

    private fun getCustomPosts2() {
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