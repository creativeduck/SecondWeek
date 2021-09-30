package com.ssacproject.secondweek.data

data class MovieResult(
    val id: Int?,                   // 영화id(코드)
    val title: String?,             // 영화명
    val original_title: String?,    // 영화명(영문)
    val overview: String?,          // 줄거리
    val poster_path: String?,        // 포스터 이미지 파일명
    val vote_average: Double?          // 평점
)