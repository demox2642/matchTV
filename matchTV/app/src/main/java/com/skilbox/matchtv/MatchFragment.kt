package com.skilbox.matchtv

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.skilbox.matchtv.adapter.MatchVideoAdapter
import com.skilbox.matchtv.databinding.FragmentMatchBinding
import com.skilbox.matchtv.network.plugins.ViewBindingFragment
import com.skilbox.matchtv.network.viewmodel.MatchViewModel
import java.util.*

class MatchFragment : ViewBindingFragment<FragmentMatchBinding>(FragmentMatchBinding::inflate) {
    private val viewModel: MatchViewModel by viewModels()
    private var matchVideoAdapter: MatchVideoAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        viewModel.getDataForMatch()
        initList()

    }

    private fun initList() {

        matchVideoAdapter = MatchVideoAdapter { url -> watchMatch(url) }
        with(binding.matchVideoList) {
            adapter = matchVideoAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun watchMatch(url: String) {

        val args = MatchFragmentDirections.actionMatchFragmentToShowMatchFragment(url)
        findNavController().navigate(args)
    }

    private fun bindViewModel() {
        viewModel.matchVideoForFragment.observe(viewLifecycleOwner) {
            matchVideoAdapter?.items = it
        }
       viewModel.matchInfoForFragment.observe(viewLifecycleOwner, ::updateMatchInfo)
    }

    private fun updateMatchInfo(matchInfo: MatchInfo) {
        val lang = Locale.getDefault()

        Log.e("Locale","$lang")//ru_RU
        when (lang.toString()){
            "ru_RU"->{
                binding.matchName.text = matchInfo.tournament.name_rus
                binding.team1Name.text= matchInfo.team1.name_rus
                binding.team2Name.text =matchInfo.team2.name_rus
            }
            else->{
                binding.matchName.text = matchInfo.tournament.name_eng
                binding.team1Name.text= matchInfo.team1.name_eng
                binding.team2Name.text =matchInfo.team2.name_eng
            }
        }

        binding.matchBateTime.text = matchInfo.date.toString()
        binding.team1Score.text = matchInfo.team1.score.toString()
        binding.team2Score.text = matchInfo.team2.score.toString()
    }
}
