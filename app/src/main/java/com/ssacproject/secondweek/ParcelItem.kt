package com.ssacproject.secondweek

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.math.acos

@Parcelize
class ParcelItem(val title: String?, val rating: Double?, val genre: String?, val showtime: String?, val audit: String?,
                 val country: String?, val year: String?, val director: String?, val actors: String?,
                 val overview: String?, val poster: String?) : Parcelable {

}