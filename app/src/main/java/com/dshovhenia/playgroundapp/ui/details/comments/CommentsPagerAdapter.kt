package com.dshovhenia.playgroundapp.ui.details.comments

import android.content.Context

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import com.dshovhenia.playgroundapp.R
import com.dshovhenia.playgroundapp.data.cache.model.video.CachedVideoMetadata

class CommentsPagerAdapter(
  private val mContext: Context, fm: FragmentManager, private var mCachedVideoMetadata: CachedVideoMetadata
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

  override fun getItem(position: Int): Fragment {
    return CommentsFragment.newInstance(mCachedVideoMetadata.commentsConnection!!)
  }

  override fun getCount(): Int {
    return NUMBER_TABS
  }

  override fun getPageTitle(position: Int): CharSequence? {
    return mContext.resources.getQuantityString(
      R.plurals.video_pager_adapter_comments_plural,
      mCachedVideoMetadata.commentsConnection!!.total,
      mCachedVideoMetadata.commentsConnection!!.total
    )
  }

  companion object {
    private val NUMBER_TABS = 1
  }
}
