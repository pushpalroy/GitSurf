package com.gitsurfer.gitsurf.model.roomdatabase.base

/*
* This abstract class is a blueprint for any database to be used in the app
* Currently, the app uses Room Database, so it is a sub class of this.
* Later on, if Room is to be swapped with some other db, it will also extend from this.
* The functions defined here will be common for any database.
*/
abstract class BaseDatabase<T> {
  /*
  * Get the instance of database
  */
  abstract fun roomDatabase(): T

  /*
  * Close the connection to database
  */
  abstract fun closeDatabase()
}