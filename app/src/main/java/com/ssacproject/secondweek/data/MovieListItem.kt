package com.ssacproject.secondweek.data

import com.ssacproject.secondweek.data.CompanyInfo
import com.ssacproject.secondweek.data.DirectorInfo

data class MovieListItem(
    val movieCd: String?,
    val movieNm: String?,
    val movieNmEn: String?,
    val prdtYear: String?,
    val openDt: String?,
    val typeNm: String?,
    val prdtStatNm: String?,
    val nationAlt: String?,
    val genreAlt: String?,
    val repNationNm: String?,
    val repGenreNm: String?,
    val directors: ArrayList<DirectorInfo>?,
    val companys: ArrayList<CompanyInfo>?
)
