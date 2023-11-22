package com.dicoding.submissionone.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionone.databinding.FragmentFollowBinding
import com.dicoding.submissionone.ui.detail.FollowViewModel
import com.dicoding.submissionone.ui.main.UserAdapter

class FollowFragment : Fragment() {

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    private var position = 0
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private lateinit var username: String
    private lateinit var viewModel: FollowViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowViewModel::class.java]

        adapter = UserAdapter()

        if (position == 1){
            viewModel.findFollowers(username)
        } else {
            viewModel.findFollowing(username)
        }

        viewModel.followers.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setUsers(it)
                binding.rvFollow.adapter = adapter
            }
        }

        viewModel.following.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setUsers(it)
                binding.rvFollow.adapter = adapter
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        binding.apply {
            rvFollow.setHasFixedSize(true)
            rvFollow.layoutManager = LinearLayoutManager(requireActivity())
            rvFollow.adapter = adapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.LoadingFollow.visibility = View.VISIBLE
        } else {
            binding.LoadingFollow.visibility = View.GONE
        }
    }
}