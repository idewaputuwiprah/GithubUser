package com.dicoding.githubconsumer.following

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubconsumer.DetailActivity
import com.dicoding.githubconsumer.R
import com.dicoding.githubconsumer.viewmodel.ViewModelDetail
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {
    private lateinit var vm: ViewModelDetail
    private lateinit var detailActivity: DetailActivity
    private lateinit var followingAdapter: FollowingAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailActivity = activity as DetailActivity

        followingAdapter = FollowingAdapter()

        rv_following.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = followingAdapter
        }

        vm = detailActivity.getViewModel()
        vm.setFollowing(detailActivity.getUser())
        showLoading(true)
        vm.getFollowing().observe(viewLifecycleOwner, Observer { items->
            if (items != null) {
                followingAdapter.setData(items)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pb_following.visibility = View.VISIBLE
        } else {
            pb_following.visibility = View.GONE
        }
    }
}