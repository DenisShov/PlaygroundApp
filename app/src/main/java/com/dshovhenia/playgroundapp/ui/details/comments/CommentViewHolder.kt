package com.dshovhenia.playgroundapp.ui.details.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.item_comment.view.*
import com.dshovhenia.playgroundapp.GlideApp
import com.dshovhenia.playgroundapp.R
import com.dshovhenia.playgroundapp.data.model.comment.Comment
import com.dshovhenia.playgroundapp.paging.base.list_item.ListItemViewHolder
import com.dshovhenia.playgroundapp.util.VimeoTextUtil
import java.util.*

class CommentViewHolder(baseFragment: Fragment, inflater: LayoutInflater, parent: ViewGroup) :
  ListItemViewHolder<Comment>(
    baseFragment, inflater.inflate(R.layout.item_comment, parent, false)
  ) {

  override fun bind(listItem: Comment, onItemClick: ((Comment) -> Unit)?) {
    itemView.run {
      item_comment_name_textview!!.text = listItem.user!!.name
      val commentAge = String.format(
        Locale.getDefault(),
        " ⋅ %s",
        VimeoTextUtil.dateCreatedToTimePassed(listItem.created_on!!)
      )
      item_comment_age_textview!!.text = commentAge
      item_comment_comment_textview!!.text = listItem.text

      GlideApp.with(mBaseFragment).load(listItem.user!!.cachedPictures!!.sizes[1].link)
        .placeholder(R.drawable.user_image_placeholder).fallback(R.drawable.user_image_placeholder)
        .circleCrop().into(item_comment_imageview!!)
    }
  }

}
