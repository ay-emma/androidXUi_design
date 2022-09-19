package com.example.globaltour.city

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.globaltour.R

class CityAdapter(val context : Context, var cityList : ArrayList<City> ) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val itemView = LayoutInflater.from(context).inflate( R.layout.list_item_city, parent, false )
        Log.i("CityAdapter", "onCreatViewHolder: viewHolder created")
        return CityViewHolder(itemView)
    }

    override fun onBindViewHolder(cityViewHolder: CityViewHolder, position: Int) {
        val city = cityList[position]
        Log.i("CityAdapter", "onBindViewHolder: position: $position")

        cityViewHolder.setData( city, position )
        cityViewHolder.setListeners()

    }

    override fun getItemCount(): Int = cityList.size

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var currentPosition : Int = -1
        private var currentCity : City? = null

        private var txvCityName = itemView.findViewById<TextView>(R.id.txv_city_name)
        private var imvCityImage = itemView.findViewById<ImageView>(R.id.imv_city)
        private var imvDelete = itemView.findViewById<ImageView>(R.id.imv_delete)
        private var imvFavourite = itemView.findViewById<ImageView>(R.id.imv_favorite)

        private val icFavouriteFilledImage = ResourcesCompat.getDrawable(context.resources, R.drawable.ic_favorite_filled, null )
        private val icFavouriteBorderImage = ResourcesCompat.getDrawable(context.resources, R.drawable.ic_favorite_bordered, null )


        fun setData( city:City, position: Int   ){
            txvCityName.text = city.name
            imvCityImage.setImageResource(city.imageId)

            if(city.isFavorite){
                imvFavourite.setImageDrawable(icFavouriteFilledImage)
            } else {
                imvFavourite.setImageDrawable(icFavouriteBorderImage)
            }

            this.currentPosition = position
            this.currentCity = city
        }

        fun setListeners() {
            imvDelete.setOnClickListener(this@CityViewHolder)
            imvFavourite.setOnClickListener(this@CityViewHolder)
        }

        override fun onClick(v: View?) {
            when(v!!.id){
                R.id.imv_delete -> deleteItem()
                R.id.imv_favorite -> addFavourite()
            }
        }

         private fun addFavourite() {
            currentCity?.isFavorite = !(currentCity?.isFavorite!!)

             if (currentCity?.isFavorite!!){ // update icon and add to favouritecity list
                imvFavourite.setImageDrawable(icFavouriteFilledImage)
                 VacationSpots.favoriteCityList.add(currentCity!!)

             } else { // Update icon and remove object form favourite list
                 imvFavourite.setImageDrawable(icFavouriteBorderImage)
                 VacationSpots.favoriteCityList.remove(currentCity!!)

             }
        }

         private fun deleteItem() {
             cityList.removeAt(currentPosition)
             notifyItemRemoved(currentPosition)
             notifyItemRangeChanged(currentPosition, cityList.size )
             VacationSpots.favoriteCityList.remove(currentCity!!)

         }
    }
}