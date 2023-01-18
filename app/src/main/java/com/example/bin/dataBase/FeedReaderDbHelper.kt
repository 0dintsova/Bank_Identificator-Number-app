package com.example.bin.dataBase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.bin.data.Fields

class FeedReaderDbHelper(context : Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    override fun onCreate(p0: SQLiteDatabase) {
        p0.execSQL(SQL_CREATE_ENTRIES)
    }
    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {
        p0.execSQL(SQL_DELETE_ENTRIES)
        onCreate(p0)
    }
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "CardsDb.db"
        const val TABLE_NAME = "CardsDB"
        const val BIN = "bin"

        const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "$BIN TEXT," +
                    "${Fields.SCHEME} TEXT," +
                    "${Fields.TYPE} TEXT," +
                    "${Fields.BRAND} TEXT," +
                    "${Fields.PREPAID} TEXT," +
                    "${Fields.CARD_LENGTH} TEXT," +
                    "${Fields.CARD_LUHN} TEXT," +
                    "${Fields.COUNTRY} TEXT," +
                    "${Fields.COUNTRY_LONGITUDE} TEXT," +
                    "${Fields.COUNTRY_LATITUDE} TEXT," +
                    "${Fields.BANK_NAME} TEXT," +
                    "${Fields.BANK_URL} TEXT," +
                    "${Fields.BANK_CITY} TEXT," +
                    "${Fields.BANK_PHONE_NUMBER} TEXT)"

        const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}