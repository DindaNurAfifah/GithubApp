package com.dicoding.submissionone.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionone.data.response.ItemsItem
import com.dicoding.submissionone.databinding.ActivityMainBinding
import com.dicoding.submissionone.ui.favorite.FavoriteActivity
import com.dicoding.submissionone.ui.theme.SettingPreferences
import com.dicoding.submissionone.ui.theme.ThemeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private val viewModel by viewModels<UserViewModel> {
        UserViewModel.Factory(SettingPreferences(this)/*, Db(this)*/)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getThemeSettings().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        adapter = UserAdapter()

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            UserViewModel::class.java)
        viewModel.user.observe(this) { user ->
            setUsers(user)
        }

        binding.apply {
            rvAkun.setHasFixedSize(true)
            rvAkun.layoutManager = LinearLayoutManager(this@MainActivity)
            rvAkun.addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
            rvAkun.adapter = adapter

            btnSearch.setOnClickListener {
                searchUser()
            }

            SearchView.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun searchUser() {
        binding.apply {
            val query = SearchView.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.findUsers(query)
        }
    }

    private fun setUsers(userList: List<ItemsItem>) {
        adapter.setUsers(userList)
        adapter.notifyDataSetChanged()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.Loading.visibility = View.VISIBLE
        } else {
            binding.Loading.visibility = View.GONE
        }
    }

    fun onImageViewClick(view: View) {
        val intent = Intent(this, ThemeActivity::class.java)
        startActivity(intent)
    }

    fun onImageViewClick2(view: View) {
        val intent = Intent(this, FavoriteActivity::class.java)
        startActivity(intent)
    }
}