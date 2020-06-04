package com.gitsurfer.gitsurf.model.roomdatabase.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.gitsurfer.gitsurf.model.roomdatabase.base.BaseDAO
import com.gitsurfer.gitsurf.model.roomdatabase.models.RoomFeed

@Dao
abstract class FeedDao : BaseDAO<RoomFeed> {

  @Query("SELECT * FROM feed_table WHERE id = :id limit 1")
  abstract suspend fun getFeed(id: String): RoomFeed?

  /**
   * The Int type parameter tells Room to use a PositionalDataSource
   * object, with position-based loading under the hood.
   */
  @Query("SELECT * FROM feed_table")
  abstract fun getAllFeeds(): DataSource.Factory<Int, RoomFeed?>
}