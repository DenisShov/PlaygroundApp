package com.dshovhenia.playgroundapp.data.model.video

import android.os.Parcelable
import com.dshovhenia.playgroundapp.data.model.pictures.Pictures
import com.dshovhenia.playgroundapp.data.model.user.User
import com.dshovhenia.playgroundapp.util.parceler.DateParceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import java.util.*

@Parcelize
@TypeParceler<Date, DateParceler>
@Suppress("LongParameterList")
class Video(
  var uri: String = "",
  var name: String = "",
  var description: String?,
  var duration: Int = 0,
  var createdTime: Date?,
  var nextPage: String = "",
  var user: User?,
  var pictures: Pictures?,
  var metadata: VideoMetadata?,
  var stats: VideoStats?
) : Parcelable
