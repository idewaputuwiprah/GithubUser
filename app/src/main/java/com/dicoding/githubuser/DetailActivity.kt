package com.dicoding.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubuser.database.UserFavorite
import com.dicoding.githubuser.database.UserFavoriteViewModel
import com.dicoding.githubuser.retrofit.UserDetailResponse
import com.dicoding.githubuser.viewmodel.ViewModelDetail
import kotlinx.android.synthetic.main.activity_detail.img_detail
import kotlinx.android.synthetic.main.activity_detail.toolbar_detail
import kotlinx.android.synthetic.main.activity_detail.tv_company
import kotlinx.android.synthetic.main.activity_detail.tv_detailnama
import kotlinx.android.synthetic.main.activity_detail.tv_detailusername
import kotlinx.android.synthetic.main.activity_detail.tv_location
import kotlinx.android.synthetic.main.activity_detail2.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }
    private lateinit var vm: ViewModelDetail
    private var username: String? = ""
    private var id: Int = 0
    private var avatarUrl = ""
    private lateinit var userFavoriteViewModel: UserFavoriteViewModel
    private var isFavorite = false
    private var favorite: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail2)
        setSupportActionBar(toolbar_detail)
        supportActionBar?.apply {
            title = "Detail User"
            setDisplayHomeAsUpEnabled(true)
            elevation = 0f
        }

        userFavoriteViewModel = ViewModelProvider(this).get(UserFavoriteViewModel::class.java)

        username = intent.getStringExtra(EXTRA_USER)
        val pagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = pagerAdapter
        tabs.setupWithViewPager(view_pager)
        vm = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ViewModelDetail::class.java)
        vm.setUserDetail(username as String)
        vm.getUserDetail().observe(this, Observer{ item->
            view(item)
        })
    }

    private fun view(item: UserDetailResponse) {
        Glide.with(this)
            .load(item.avatar_url)
            .apply(RequestOptions().override(130,130))
            .into(img_detail)
        avatarUrl = item.avatar_url as String
        tv_detailnama.text = item.name
        tv_detailusername.text = item.login
        tv_location.text = item.location
        tv_company.text = item.company
        tv_repo2.text = item.public_repos.toString()
        tv_followers2.text = item.followers.toString()
        tv_following2.text = item.following.toString()

        updateIconFav(item.login as String)
    }

    private fun updateIconFav(username: String) {
        userFavoriteViewModel.readAllData.observe(this, Observer { users->
            val thisUser = username
            for (user in users) {
                if (user.username.toString().equals(thisUser, true)) {
                    id = user.id
                    isFavorite = true
                    break
                }
            }
            invalidateOptionsMenu()
        })
    }

    fun getUser(): String {
        return username as String
    }

    fun getViewModel(): ViewModelDetail {
        return vm
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_fav -> {
                if (!isFavorite) addUser()
                else deleteUser()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        favorite = menu?.findItem(R.id.action_add_fav)
        favorite?.icon =
            if (isFavorite)
                ContextCompat.getDrawable(applicationContext, R.drawable.ic_favorite_filled)
            else
                ContextCompat.getDrawable(applicationContext, R.drawable.ic_favorite_not_filled)
        return super.onPrepareOptionsMenu(menu)
    }

    private fun addUser() {
        val userFavorite = getModel()
        userFavoriteViewModel.addUser(userFavorite)
        isFavorite = true
        invalidateOptionsMenu()
    }

    private fun deleteUser() {
        val userFavorite = getModel()
        userFavoriteViewModel.deleteUser(userFavorite)
        isFavorite = false
        invalidateOptionsMenu()
    }

    private fun getModel(): UserFavorite {
        return UserFavorite(
            id = id,
            name = tv_detailnama.text.toString(),
            username = tv_detailusername.text.toString(),
            public_repos = tv_repo2.text.toString(),
            followers = tv_followers2.text.toString(),
            following = tv_following2.text.toString(),
            company = tv_company.text.toString(),
            location = tv_location.text.toString(),
            avatar_url = avatarUrl
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        this.onBackPressed()
        return super.onSupportNavigateUp()
    }
}