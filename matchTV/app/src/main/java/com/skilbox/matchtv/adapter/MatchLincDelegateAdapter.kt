package com.skilbox.matchtv.adapter

import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skilbox.flowsearchmovie.adapter.inflate
import com.skilbox.matchtv.MatchLinc
import com.skilbox.matchtv.R

class MatchLincDelegateAdapter(
    private val onItemClick: (videoLinc: String) -> Unit
) :
    AbsListItemAdapterDelegate<MatchLinc, MatchLinc, MatchLincDelegateAdapter.MatchLincViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): MatchLincViewHolder {
        return MatchLincViewHolder(parent.inflate(R.layout.match_linc_list_item), onItemClick)
    }

    class MatchLincViewHolder(
        view: View,
        onItemClick: (videoLinc: String) -> Unit
    ) : BaseHolder(view, onItemClick) {

        fun bind(matchLinc: MatchLinc) {
            bindMainInfo(matchLinc)
        }
        override val containerView: View
            get() = itemView
    }

    override fun isForViewType(
        item: MatchLinc,
        items: MutableList<MatchLinc>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onBindViewHolder(
        item: MatchLinc,
        holder: MatchLincViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}
