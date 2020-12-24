package com.dicoding.githubuser.favorite

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
import com.dicoding.githubuser.DetailActivity
import com.dicoding.githubuser.R
import com.dicoding.githubuser.UserAdapter
import com.dicoding.githubuser.UserModel
import com.dicoding.githubuser.database.UserFavorite
import com.dicoding.githubuser.database.UserFavoriteViewModel
import com.dicoding.githubuser.provider.UserProvider.Companion.CONTENT_URI
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {
    private lateinit var userFavoriteViewModel: UserFavoriteViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        setSupportActionBar(toolbar_fav)
        supportActionBar?.apply {
            title = "Favorites User"
            setDisplayHomeAsUpEnabled(true)
        }

        val line = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        userAdapter = UserAdapter()
        userAdapter.setItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClick(data: UserModel) {
                val username = data.username
                val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USER, username)
                startActivity(intent)
            }
        })

        recyclerView = rv_list_favorite
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            addItemDecoration(line)
            adapter = userAdapter
        }

        userFavoriteViewModel = ViewModelProvider(this).get(UserFavoriteViewModel::class.java)
//        userFavoriteViewModel.readAllData.observe(this, Observer { users ->
//            setView(users)
//        })
        userFavoriteViewModel.getData().observe(this, Observer { users->
            setView(users)
        })

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                userFavoriteViewModel.setData()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
    }

    private fun setView(users: List<UserFavorite>) {
        val usersFav = arrayListOf<UserModel>()
        var model: UserModel
        for (user in users) {
            model = UserModel(
                id = user.id,
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