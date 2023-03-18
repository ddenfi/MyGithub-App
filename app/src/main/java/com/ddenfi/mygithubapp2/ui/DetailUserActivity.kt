package com.ddenfi.mygithubapp2.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.ddenfi.mygithubapp2.R
import com.ddenfi.mygithubapp2.adapter.DetailFollowAdapter
import com.ddenfi.mygithubapp2.databinding.ActivityDetailUserBinding
import com.ddenfi.mygithubapp2.database.DetailUser
import com.ddenfi.mygithubapp2.utils.Results
import com.ddenfi.mygithubapp2.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f

        binding.pbUser.visibility = View.GONE

        val user = intent.getParcelableExtra<DetailUser>(EXTRA_DATAUSER)

        CoroutineScope(Dispatchers.Main).launch {
            user?.let { mUser ->
                detailViewModel.getDetailUser(mUser).observe(this@DetailUserActivity) {
                    when (it) {
                        is Results.Loading -> showLoading()
                        is Results.Error -> showError()
                        is Results.Success -> it.data?.let { user ->
                            getDetailData(user)
                            setFavIcon(user)
                            title = user.login
                        }
                    }
                }
            }
        }
        setViewPager()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setFavIcon(user: DetailUser) {
        binding.ivFav.visibility = View.VISIBLE
        binding.pbUser.visibility = View.GONE

        if (user.isFav) {
            binding.ivFav.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite))
        } else {
            binding.ivFav.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_border))
        }

        binding.ivFav.setOnClickListener {
            if (user.isFav) {
                user.isFav = false
                detailViewModel.deleteFavUser(user)
                binding.ivFav.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_border))
                Toast.makeText(this, "Dihapus dari Daftar Favorite!", Toast.LENGTH_SHORT).show()
            } else {
                detailViewModel.setFavUser(user)
                binding.ivFav.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite))
                Toast.makeText(this, "Ditambahkan ke Daftar Favorite!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getDetailData(user: DetailUser) {
        Glide.with(this)
            .load(user.avatarUrl)
            .circleCrop()
            .into(binding.imgDetailPhoto)
        binding.tvDetailUsername.text = "@" + user.login ?: "-"
        binding.tvDetailName.text = user.name ?: "-"
        binding.tvDetailFollowers.text = user.followers.toString()
        binding.tvDetailFollowing.text = user.following.toString()
        binding.tvDetailRepositories.text = user.publicRepos.toString()
        binding.tvDetailCompany.text = user.company ?: "-"
        binding.tvDetailLocation.text = user.location ?: "-"

    }

    private fun setViewPager() {
        val detailFollowAdapter = DetailFollowAdapter(this)
        val viewPager: ViewPager2 = binding.vpDetailFollow
        viewPager.adapter = detailFollowAdapter
        val tabs: TabLayout = binding.tabsDetailFollow

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showLoading() {
        binding.pbUser.visibility = View.VISIBLE
    }

    private fun showError() {
        binding.pbUser.visibility = View.GONE
    }

    companion object {
        const val EXTRA_DATAUSER = "extra_datauser"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2

        )
    }
}