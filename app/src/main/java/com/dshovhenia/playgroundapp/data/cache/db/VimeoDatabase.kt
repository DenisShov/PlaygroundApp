package com.dshovhenia.playgroundapp.data.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dshovhenia.playgroundapp.data.cache.dao.comment.CommentDao
import com.dshovhenia.playgroundapp.data.cache.dao.pictures.PictureSizesDao
import com.dshovhenia.playgroundapp.data.cache.dao.user.UserDao
import com.dshovhenia.playgroundapp.data.cache.dao.video.VideoDao
import com.dshovhenia.playgroundapp.data.cache.model.comment.CachedComment
import com.dshovhenia.playgroundapp.data.cache.model.pictures.CachedPictureSizes
import com.dshovhenia.playgroundapp.data.cache.model.user.CachedUser
import com.dshovhenia.playgroundapp.data.cache.model.video.CachedVideo

@Database(
  entities = [CachedComment::class, CachedVideo::class,
    CachedPictureSizes::class, CachedUser::class
  ], version = 1
)
@TypeConverters(DateConverter::class)
abstract class VimeoDatabase : RoomDatabase() {
  abstract fun commentDao(): CommentDao
  abstract fun videoDao(): VideoDao
  abstract fun userDao(): UserDao
  abstract fun pictureSizesDao(): PictureSizesDao
}