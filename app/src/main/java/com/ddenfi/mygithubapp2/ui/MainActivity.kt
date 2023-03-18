package com.ddenfi.mygithubapp2.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddenfi.mygithubapp2.R
import com.ddenfi.mygithubapp2.adapter.ListUserAdapter
import com.ddenfi.mygithubapp2.database.DetailUser
import com.ddenfi.mygithubapp2.databinding.ActivityMainBinding
import com.ddenfi.mygithubapp2.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private val adapter by lazy {
        ListUserAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mainViewModel.users.observe(this) {
            adapter.setData(it)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        setRecyclerView()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)


        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.clearFocus()
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.searchUser(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) mainViewModel.cancelSearch()
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite -> {
                val intent = Intent(this@MainActivity, FavUserActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.setting -> {
                val intent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            else -> false
        }
    }

    private fun setRecyclerView() {
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = adapter
        binding.rvUsers.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallBack(object : ListUserAdapter.OnItemCLickCallBack {
            override fun onItemClicked(user: DetailUser) {
                val toDetailActivity = Intent(this@MainActivity, DetailUserActivity::class.java)
                toDetailActivity.putExtra(DetailUserActivity.EXTRA_DATAUSER, user)
                startActivity(toDetailActivity)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.ltAnim.visibility = View.VISIBLE
        } else {
            binding.ltAnim.visibility = View.GONE
        }
    }

}