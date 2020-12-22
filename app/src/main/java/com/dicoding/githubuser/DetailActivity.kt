package com.dicoding.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubuser.retrofit.ApiService
import com.dicoding.githubuser.retrofit.UserDetailResponse
import com.dicoding.githubuser.retrofit.UserResponse
import com.dicoding.githubuser.viewmodel.ViewModelDetail
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.img_detail
import kotlinx.android.synthetic.main.activity_detail.toolbar_detail
import kotlinx.android.synthetic.main.activity_detail.tv_company
import kotlinx.android.synthetic.main.activity_detail.tv_detailnama
import kotlinx.android.synthetic.main.activity_detail.tv_detailusername
import kotlinx.android.synthetic.main.activity_detail.tv_location
import kotlinx.android.synthetic.main.activity_detail2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }
    private lateinit var vm: ViewModelDetail
    private var username: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail2)
        setSupportActionBar(toolbar_detail)
        supportActionBar?.title = "Detail User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f

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
        tv_detailnama.text = item.name
        tv_detailusername.text = item.login
        tv_location.text = item.location
        tv_company.text = item.company
        tv_repo2.text = item.public_repos.toString()
        tv_followers2.text = item.followers.toString()
        tv_following2.text = item.following.toString()
    }

    fun getUser(): String {
        return username as String
    }

    fun getViewModel(): ViewModelDetail {
        return vm
    }
}