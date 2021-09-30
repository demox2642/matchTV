package com.skilbox.matchtv.adapter

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skilbox.matchtv.MatchLinc
import kotlinx.android.synthetic.main.match_linc_list_item.view.*

class MatchVideoAdapter(
    onItemClick: (videoLinc: String) -> Unit
) : AsyncListDifferDelegationAdapter<MatchLinc>(MatchLincDiffUtilCallBack()) {
    init {
        delegatesManager.addDelegate(MatchLincDelegateAdapter(onItemClick))
    }
    class MatchLincDiffUtilCallBack : DiffUtil.ItemCallback<MatchLinc>() {
        override fun areItemsTheSame(oldItem: MatchLinc, newItem: MatchLinc): Boolean {
            return oldItem.server_id == newItem.server_id
        }

        override fun areContentsTheSame(oldItem: MatchLinc, newItem: MatchLinc): Boolean {
            return oldItem == newItem
        }
    }
}
