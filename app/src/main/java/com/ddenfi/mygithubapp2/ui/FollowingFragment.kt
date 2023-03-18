package com.ddenfi.mygithubapp2.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddenfi.mygithubapp2.adapter.ListFollowAdapter
import com.ddenfi.mygithubapp2.database.DetailUser
import com.ddenfi.mygithubapp2.databinding.FragmentFollowBinding
import com.ddenfi.mygithubapp2.viewmodel.FollowViewModel

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var followerViewModel: FollowViewModel
    private val adapter by lazy {
        ListFollowAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("FollowerFragment", "Hallo From Folowerfragment")
        followerViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FollowViewModel::class.java]

        binding.pbFollow.visibility = View.GONE

        val user =
            activity?.intent?.getParcelableExtra<DetailUser>(DetailUserActivity.EXTRA_DATAUSER)
        followerViewModel.getFollowing(user?.login)

        followerViewModel.users.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        followerViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        setRecyclerView()
    }

    private fun setRecyclerView() {
        binding.rvFollow.layoutManager = LinearLayoutManager(requireContext())
        val listFollowAdapter = adapter
        binding.rvFollow.adapter = listFollowAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pbFollow.visibility = View.VISIBLE
        } else {
            binding.pbFollow.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}