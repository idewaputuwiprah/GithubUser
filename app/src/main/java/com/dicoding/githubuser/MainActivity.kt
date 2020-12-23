package com.dicoding.githubuser

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.githubuser.favorite.FavoriteActivity
import com.dicoding.githubuser.retrofit.ApiService
import com.dicoding.githubuser.retrofit.SearchResponse
import com.dicoding.githubuser.retrofit.UserResponse
import com.dicoding.githubuser.setting.SettingActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_following.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var username: Array<String>
    private lateinit var name: Array<String>
    private lateinit var avatar: TypedArray
    private lateinit var company: Array<String>
    private lateinit var location: Array<String>
    private lateinit var repository: Array<String>
    private lateinit var follower: Array<String>
    private lateinit var following: Array<String>
    private lateinit var userAdapter: UserAdapter
    private val users = arrayListOf<UserModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.main_title)
        toolbar.setNavigationIcon(R.drawable.logo)

        recyclerView = rv_list
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val line = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(line)

        getItem()
        getListUser()

        userAdapter = UserAdapter()
        userAdapter.setItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClick(data: UserModel) {
                val username = data.username
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USER, username)
                startActivity(intent)
            }
        })
        recyclerView.adapter = userAdapter
        userAdapter.setData(users)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView

        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = resources.getString(R.string.search_hint) + "..."
            setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    search(query as String)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (query.isNullOrEmpty()) userAdapter.setData(users)
                    return false
                }

            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_option -> {
//                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
            R.id.action_fav -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun search(username: String) {
        showLoading(true)
        val token = "token bd285bb153303924cf64012554796f968bc06ca2"
        ApiService.endpoint.searchUsers(token = token, username = username).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                showLoading(false)
                val result = response.body() as SearchResponse
                val list = ArrayList<UserModel>()
                for (item in result.items) {
                    val model = UserModel(id = item.id, username = item.login.toString(), name = item.login.toString(), avatar_string = item.avatar_url, avatar_int = null)
                    list.add(model)
                }
                userAdapter.setData(list)
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                showLoading(false)
            }
        })
    }

    private fun getItem(){
        username = resources.getStringArray(R.array.username)
        name = resources.getStringArray(R.array.name)
        avatar = resources.obtainTypedArray(R.array.avatar)
        company = resources.getStringArray(R.array.company)
        location = resources.getStringArray(R.array.location)
        repository = resources.getStringArray(R.array.repository)
        follower = resources.getStringArray(R.array.followers)
        following = resources.getStringArray(R.array.following)
    }

    private fun getListUser(){
        var model: UserModel
        for (i in username.indices) {
            model = UserModel(
                username = username[i],
                name = name[i],
                avatar_int = avatar.getResourceId(i,-1),
                avatar_string = null,
                id = null)
            users.add(model)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pb_main.visibility = View.VISIBLE
        } else {
            pb_main.visibility = View.GONE
        }
    }
}