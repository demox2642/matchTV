package com.skilbox.matchtv.network.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skilbox.matchtv.MatchInfo
import com.skilbox.matchtv.MatchLinc
import com.skilbox.matchtv.network.ParamBody
import com.skilbox.matchtv.network.Params
import kotlinx.coroutines.launch

class MatchViewModel : ViewModel() {

    private val repositoty = MatchRepository()

    private val matchInfoLiveData = MutableLiveData <MatchInfo>()
    val matchInfoForFragment: LiveData<MatchInfo>
        get() = matchInfoLiveData
    private val matchVideoLiveData = MutableLiveData <List <MatchLinc>>()
    val matchVideoForFragment: LiveData<List <MatchLinc>>
        get() = matchVideoLiveData

    fun getDataForMatch() {
        val params = Params(1, 1724836) // mutableMapOf<String,Int>()
        val params2 = mutableMapOf<String, Int>() //  Params(1, 1724836)
        params2.put("match_id", 1724836)
        params2.put("sport_id", 1)
        val paramsBody = ParamBody("get_match_info", params)
        viewModelScope.launch {
            try {
                matchInfoLiveData.postValue(repositoty.getMatchInfo(paramsBody))
                matchVideoLiveData.postValue(repositoty.getMatchLinc(params2))
            } catch (t: Exception) {
                Log.e("MatchViewModel", "getTest Error:${t.message}")
            }
        }
    }

}
