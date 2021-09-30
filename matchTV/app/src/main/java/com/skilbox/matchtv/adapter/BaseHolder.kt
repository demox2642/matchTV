package com.skilbox.matchtv.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.skilbox.matchtv.MatchLinc
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.match_linc_list_item.*
import kotlinx.android.synthetic.main.match_linc_list_item.view.*

abstract class BaseHolder(
    view: View,
    private var onItemCkick: (videoLinc: String) -> Unit
) : RecyclerView.ViewHolder(view), LayoutContainer {



    protected fun bindMainInfo(
        matchLinc: MatchLinc
    ) {


        itemView.quality_video.text = "quality: ${matchLinc.quality} folder: ${matchLinc.folder}"
        itemView.button.setOnClickListener {
            onItemCkick(matchLinc.url)
        }
    }
}
