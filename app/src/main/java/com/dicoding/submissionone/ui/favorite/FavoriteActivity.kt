package com.dicoding.submissionone.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionone.databinding.ActivityFavoriteBinding
import com.dicoding.submissionone.ui.main.UserAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        binding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.adapter = adapter
         }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.loadAll()

        viewModel.note.observe(this) { note ->
            adapter.setUsers(note)
        }
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.Loading.visibility = View.VISIBLE
        } else {
            binding.Loading.visibility = View.GONE
        }
    }
}
