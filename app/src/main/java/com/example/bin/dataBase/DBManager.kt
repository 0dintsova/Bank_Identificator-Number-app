package com.example.bin.dataBase

import android.content.ContentValues
import android.content.Context
import com.example.bin.list.Card
import com.example.bin.data.Fields

class DBManager(context: Context) {
    val dbHelper = FeedReaderDbHelper(context)

    fun insertToDb(scheme :String, type: String, brand: String, prepaid: String, length: String,
                   luhn: String, country: String, longitude: String, latitude: String,name: String,
                   url: String,phone: String){
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(Fields.SCHEME, scheme)
            put(Fields.TYPE, type)
            put(Fields.BRAND, brand)
            put(Fields.PREPAID, prepaid)
            put(Fields.CARD_LENGTH, length)
            put(Fields.CARD_LUHN, luhn)
            put(Fields.COUNTRY, country)
            put(Fields.COUNTRY_LONGITUDE, longitude)
            put(Fields.COUNTRY_LATITUDE, latitude)
            put(Fields.BANK_NAME, name)
            put(Fields.BANK_URL, url)
            put(Fields.BANK_PHONE_NUMBER, phone)
        }
        db?.insert(FeedReaderDbHelper.TABLE_NAME,null, values)
    }

    fun readDbData(): ArrayList<Card>{
        val db = dbHelper.readableDatabase
        val dataList = ArrayList<Card>()
        val cursor = db.query(FeedReaderDbHelper.TABLE_NAME,null, null, null, null,
            null,null)
        with(cursor) {
            while (moveToNext()) {
                val card = Card(
                    getString(getColumnIndexOrThrow(Fields.SCHEME)),
                    getString(getColumnIndexOrThrow(Fields.TYPE)),
                    getString(getColumnIndexOrThrow(Fields.BRAND)),
                    getString(getColumnIndexOrThrow(Fields.PREPAID)),
                    getString(getColumnIndexOrThrow(Fields.CARD_LENGTH)),
                    getString(getColumnIndexOrThrow(Fields.CARD_LUHN)),
                    getString(getColumnIndexOrThrow(Fields.COUNTRY)),
                    getString(getColumnIndexOrThrow(Fields.COUNTRY_LONGITUDE)),
                    getString(getColumnIndexOrThrow(Fields.COUNTRY_LATITUDE)),
                    getString(getColumnIndexOrThrow(Fields.BANK_NAME)),
                    getString(getColumnIndexOrThrow(Fields.BANK_URL)),
                    getString(getColumnIndexOrThrow(Fields.BANK_PHONE_NUMBER))
                )
                dataList.add(card)
            }
        }
        cursor.close()
        closeDb()
        return dataList
    }
    fun closeDb(){
        dbHelper.close()
    }
}
