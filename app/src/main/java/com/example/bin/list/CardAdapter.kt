package com.example.bin.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bin.R
import com.example.bin.databinding.CardItemBinding

class CardAdapter: RecyclerView.Adapter<CardAdapter.CardHolder>(){
    val cardlist = ArrayList<Card>()
    class CardHolder(item: View): RecyclerView.ViewHolder(item){
        val binding = CardItemBinding.bind(item)

        fun bind(card: Card,position: Int) = with(binding){
            scheme.text = "SCHEME: ${card.scheme}"
            type.text = "TYPE: ${card.type}"
            brand.text = "BRAND: ${card.brand}"
            prepaid.text = "PREPAID: ${if(card.prepaid == "true") "Yes" else "No"}"
            cardNumberLength.text = "Length: ${card.cardNumberLength}"
            cardNumberLuhn.text = "Luhn: ${if(card.cardNumberLuhn == "true") "Yes" else "No"}"
            country.text = "COUNTRY: ${card.country}"
            countryLongitude.text = "Longitude: ${card.countryLongitude}"
            countryLatitude.text = "Latitude: ${card.countryLatitude}"
            bankName.text = "BANK: ${card.bankName}"
            bankUrl.text = card.bankUrl
            bankPhoneNum.text = card.bankPhoneNum
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item,parent,false)
        return CardHolder(view)
    }
    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.bind(cardlist[position], position)
    }
    override fun getItemCount(): Int {
        return cardlist.size
    }
    fun addCardItem(item:Card){
        cardlist.add(0, item)
        notifyDataSetChanged()
    }
}