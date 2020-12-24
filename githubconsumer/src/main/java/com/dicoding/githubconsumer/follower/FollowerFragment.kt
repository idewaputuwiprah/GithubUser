package com.dicoding.githubconsumer.follower

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
import kotlinx.android.synthetic.main.fragment_follower.*

class FollowerFragment : Fragment() {
    private lateinit var vm: ViewModelDetail
    private lateinit var detailActivity: DetailActivity
    private lateinit var followerAdapter: FollowerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailActivity = activity as DetailActivity

        followerAdapter = FollowerAdapter()

        rv_followers.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = followerAdapter
        }

        vm = detailActivity.getViewModel()
        vm.setFollowers(detailActivity.getUser())
        showLoading(true)
        vm.getFollowers().observe(viewLifecycleOwner, Observer { items ->
            if (items != null) {
                followerAdapter.setData(items)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pb_follower.visibility = View.VISIBLE
        } else {
            pb_follower.visibility = View.GONE
        }
    }
}