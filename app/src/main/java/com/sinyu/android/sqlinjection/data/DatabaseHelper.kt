package com.sinyu.android.sqlinjection.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper private constructor(
    context: Context,
    name: String,
    factory: CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table user(name varchar(20),psw varchar(20))")

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    companion object {

        private const val DATABASE_NAME = "TestSQLInjection.db"

        private const val DATABASE_VERSION = 1
        private var instance: DatabaseHelper? = null

        fun getInstance(context: Context): DatabaseHelper? {
            if (null == instance) {
                synchronized(DatabaseHelper::class.java) {
                    if (null == instance) {
                        instance = DatabaseHelper(
                            context,
                            DATABASE_NAME,
                            null,
                            DATABASE_VERSION
                        )
                    }
                }
            }
            return instance
        }
    }
}