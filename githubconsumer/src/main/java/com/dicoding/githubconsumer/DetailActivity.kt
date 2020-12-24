package com.dicoding.githubconsumer

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubconsumer.retrofit.UserDetailResponse
import com.dicoding.githubconsumer.userFavorite.DatabaseContract.UserColumns.Companion.AVATAR_URL
import com.dicoding.githubconsumer.userFavorite.DatabaseContract.UserColumns.Companion.COMPANY
import com.dicoding.githubconsumer.userFavorite.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.dicoding.githubconsumer.userFavorite.DatabaseContract.UserColumns.Companion.FOLLOWERS
import com.dicoding.githubconsumer.userFavorite.DatabaseContract.UserColumns.Companion.FOLLOWING
import com.dicoding.githubconsumer.userFavorite.DatabaseContract.UserColumns.Companion.LOCATION
import com.dicoding.githubconsumer.userFavorite.DatabaseContract.UserColumns.Companion.NAME
import com.dicoding.githubconsumer.userFavorite.DatabaseContract.UserColumns.Companion.PUBLIC_REPOS
import com.dicoding.githubconsumer.userFavorite.DatabaseContract.UserColumns.Companion.USERNAME
import com.dicoding.githubconsumer.userFavorite.DatabaseContract.UserColumns.Companion._ID
import com.dicoding.githubconsumer.viewmodel.UserViewModel
import com.dicoding.githubconsumer.viewmodel.ViewModelDetail
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }
    private lateinit var vm: ViewModelDetail
    private var username: String? = ""
    private var id: Int = 0
    private var avatarUrl = ""
    private lateinit var userViewModel: UserViewModel
    private var isFavorite = false
    private var favorite: MenuItem? = null
    private lateinit var uriWithId: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.apply {
            title = "Detail User"
            setDisplayHomeAsUpEnabled(true)
            elevation = 0f
        }

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

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
        userViewModel.getData().observe(this, Observer { users->
            val thisUser = username
            for (user in users) {
                if (user.username.toString().equals(thisUser, true)) {
                    id = user._id
                    isFavorite = true
                    break
                }
            }
            invalidateOptionsMenu()
        })
    }

    fun getUser(): String {
        return username.orEmpty()
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
                if (!isFavorite) addUserCR()
                else deleteUserCR()
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

    private fun addUserCR() {
        val values = getValues()
        userViewModel.addUserCR(CONTENT_URI, values)
        isFavorite = true
        invalidateOptionsMenu()
    }

    private fun deleteUserCR() {
        uriWithId = Uri.parse("$CONTENT_URI/$id")
        userViewModel.deleteUserCR(uriWithId)
        isFavorite = false
        invalidateOptionsMenu()
    }

    private fun getValues(): ContentValues {
        val values = ContentValues()
        values.apply {
            put(_ID, id)
            put(NAME, tv_detailnama.text.toString())
            put(USERNAME, tv_detailusername.text.toString())
            put(PUBLIC_REPOS, tv_repo2.text.toString())
            put(FOLLOWERS, tv_followers2.text.toString())
            put(FOLLOWING, tv_following2.text.toString())
            put(COMPANY, tv_company.text.toString())
            put(LOCATION, tv_location.text.toString())
            put(AVATAR_URL, avatarUrl)
        }
        return values
    }

    override fun onSupportNavigateUp(): Boolean {
        this.onBackPressed()
        return super.onSupportNavigateUp()
    }
}