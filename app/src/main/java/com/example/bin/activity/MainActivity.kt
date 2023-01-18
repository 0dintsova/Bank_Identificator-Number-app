package com.example.bin.activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.bin.dataBase.DBManager
import com.example.bin.list.CardAdapter
import com.example.bin.data.Fields
import com.example.bin.databinding.ActivityMainBinding
import com.example.bin.list.Card
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var bindingMain : ActivityMainBinding
    val adapter = CardAdapter()
    val dbManager = DBManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)
        bindingMain.list.layoutManager = GridLayoutManager(this@MainActivity, 1)
        bindingMain.list.adapter = adapter
        bindingMain.getBinButton.setOnClickListener(){
            getBin(bindingMain.editBin.text.toString())
        }
    }
    override fun onResume() {
        super.onResume()
        val dbList = dbManager.readDbData()
        for(card in dbList){
            adapter.addCardItem(card)
        }
    }
    override fun onDestroy() {
        dbManager.closeDb()
        super.onDestroy()
    }
    fun checkKeyExists(obj: JSONObject?, key:String):String{
        return if (obj?.has(key) == true) obj.getString(key).toString() else "-"
    }
    fun getBin(bin : String){
        val queue = Volley.newRequestQueue(this)
        val url = "https://lookup.binlist.net/$bin"
        val stringRequest = StringRequest(
            com.android.volley.Request.Method.GET,url,
            {
                    response->
                try {
                    val obj = JSONObject(response)
                    val number = if (obj.has("number")) obj.getJSONObject("number") else null
                    val country = if(obj.has("country")) obj.getJSONObject("country") else null
                    val bank = if(obj.has("bank")) obj.getJSONObject("bank") else null

                    val card = Card(
                        checkKeyExists(obj, Fields.SCHEME), checkKeyExists(obj, Fields.TYPE),
                        checkKeyExists(obj, Fields.BRAND), checkKeyExists(obj, Fields.PREPAID),
                        checkKeyExists(number, Fields.CARD_LENGTH), checkKeyExists(number, Fields.CARD_LUHN),
                        checkKeyExists(country, Fields.COUNTRY), checkKeyExists(country, Fields.COUNTRY_LATITUDE),
                        checkKeyExists(country, Fields.COUNTRY_LONGITUDE),
                        checkKeyExists(bank, Fields.BANK_NAME) + if (bank?.has(Fields.BANK_CITY) == true)
                            " " + obj.getString(Fields.BANK_CITY).toString() else " ",
                        checkKeyExists(bank, Fields.BANK_URL), checkKeyExists(bank, Fields.BANK_PHONE_NUMBER),
                        )
                    adapter.addCardItem(card)
                    saveData(card)
                }catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this,"Не удалось загрузить данные",Toast.LENGTH_SHORT).show()
                }
            },
            {
                Log.d("MyLog","$it ")
                Toast.makeText(this,"Не удалось загрузить данные",Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(stringRequest)
    }
    fun saveData(card:Card){
            dbManager.insertToDb(card.scheme.toString(),card.type.toString(),card.brand.toString(),card.prepaid.toString(),
                card.cardNumberLength.toString(),card.cardNumberLuhn.toString(), card.country.toString(),
                card.countryLongitude.toString(), card.countryLatitude.toString(), card.bankName.toString(),
                card.bankUrl.toString(),card.bankPhoneNum.toString())
    }

}
