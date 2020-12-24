package com.dicoding.githubconsumer

import android.content.Intent
import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.githubconsumer.userFavorite.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.dicoding.githubconsumer.userFavorite.UserFavorite
import com.dicoding.githubconsumer.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Github Consumer"
            setDisplayHomeAsUpEnabled(true)
        }

        val line = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        userAdapter = UserAdapter()
        userAdapter.setItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClick(data: UserModel) {
                val username = data.username
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USER, username)
                startActivity(intent)
            }
        })

        recyclerView = rv_list
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(line)
            adapter = userAdapter
        }

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.getData().observe(this, Observer { users->
            setView(users)
        })

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                userViewModel.setData()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
    }

    private fun setView(users: List<UserFavorite>) {
        val usersFav = arrayListOf<UserModel>()
        var model: UserModel
        for (user in users) {
            model = UserModel(
                id = user._id,
                username = user.username as String,
                name = user.name as String,
                avatar_string = user.avatar_url,
                avatar_int = null
            )
            usersFav.add(model)
        }
        userAdapter.setData(usersFav)
    }
}