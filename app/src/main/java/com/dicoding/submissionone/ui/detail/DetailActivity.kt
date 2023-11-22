package com.dicoding.submissionone.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ToggleButton
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.submissionone.R
import com.dicoding.submissionone.data.response.DetailUserResponse
import com.dicoding.submissionone.databinding.ActivityDetailBinding
import com.dicoding.submissionone.ui.main.UserAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()

        val id = intent.getIntExtra(EXTRA_ID, 0)
        val username = intent.getStringExtra(EXTRA_USER).toString()
        val avatarUrl = intent.getStringExtra(EXTRA_URL).toString()
        val btnFavorite: ToggleButton = findViewById(R.id.btnFavorite)
        val sectionsPagerAdapter = DetailAdapter(this, username)
        val viewPager: ViewPager2 = findViewById(R.id.viewpager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tablayouts)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.detailUser.observe(this) { user ->
            setDetail(user)
        }
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        intent.getStringExtra(EXTRA_USER)?.let { viewModel.findDetail(it) }
        adapter = UserAdapter()

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch{
            val count = viewModel.checkFavorite(id)
            withContext(Dispatchers.Main) {
                /*_isChecked = count ?: 0>0*/
                if(count != null) {
                    if(count > 0) {
                        binding.btnFavorite.isChecked = true
                        _isChecked = true
                    }else{
                        binding.btnFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.btnFavorite.setOnClickListener{
            _isChecked = !_isChecked
            if(_isChecked) {
                viewModel.insert(username, avatarUrl, id)
            } else {
                viewModel.remove(id)
            }
            binding.btnFavorite.isChecked = _isChecked
        }
    }

    private fun setDetail(user: DetailUserResponse) {
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(user.avatarUrl)
                .circleCrop()
                .into(imgprofile)
            namauser.text = user.name
            namausername.text = user.login
            idfollowerssize.text = user.followers.toString()
            idfollowingsize.text = user.following.toString()
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.LoadingDetail.visibility = View.VISIBLE
        } else {
            binding.LoadingDetail.visibility = View.GONE
        }
    }
}