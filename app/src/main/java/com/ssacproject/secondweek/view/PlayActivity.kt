package com.ssacproject.secondweek.view

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.gson.Gson
import com.ssacproject.secondweek.*
import com.ssacproject.secondweek.data.CustomMovieList
import com.ssacproject.secondweek.data.MovieInfoDetails
import com.ssacproject.secondweek.data.TmdbMovieDetails

class PlayActivity : AppCompatActivity() {
    lateinit var image2: ImageButton
    lateinit var detailExplain: ConstraintLayout
    lateinit var explainIcon: ImageView
    lateinit var detail: TableLayout
    lateinit var movieTitle: TextView
    lateinit var rating: TextView
    lateinit var genre: TextView
    lateinit var showtime: TextView
    lateinit var audit: TextView
    lateinit var poster: ImageView
    lateinit var countryAndYear: TextView
    lateinit var year: TextView
    lateinit var detail_genre: TextView
    lateinit var director: TextView
    lateinit var actors: TextView
    lateinit var overview: TextView
    lateinit var urls: HashMap<String, String>
    lateinit var share: ImageButton
    lateinit var custom1: CustomRecommendView
    lateinit var custom2: CustomRecommendView
    lateinit var custom3: CustomRecommendView
    lateinit var videobox: RelativeLayout
    lateinit var playerView: PlayerView
    lateinit var simpleExoPlayer: SimpleExoPlayer
    lateinit var exoFullScreen: ImageButton
    var currentTitle: String? = null
    var state_flag: Boolean = false
    var parcelItem: ParcelItem? = null
    var duration: Int = 0
    var posterPath: String? = null
    private val kobisApiKey: String = "6ddebf77f1bb19cef16e7f57c7e579c0"

    // 화면에 한 번만 발생하는 것들을 모두 정리
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        urls = HashMap<String, String>()
        urls["주토피아"] = "https://cdn.kapwing.com/final_6141cd3752415e00feef56b4_170276.mp4"
        urls["매드 맥스: 분노의 도로"] = "https://cdn.kapwing.com/final_6141ce1e1c793d0044e30d32_155181.mp4"
        urls["스파이더맨"] = "https://cdn.kapwing.com/final_613d6a8e856f4500ed61a130_836060.mp4"
        urls["스파이더맨 2"] = "https://cdn.kapwing.com/final_613d6577b205cc010e5bb069_748565.mp4"
        urls["스파이더맨 3"] = "https://cdn.kapwing.com/final_613d651011db5e00b1c1493d_690179.mp4"
        urls["찰리와 초콜릿 공장"] = "https://cdn.kapwing.com/final_613d6a4a12652901501c9433_36216.mp4"
        urls["소스 코드"] = "https://cdn.kapwing.com/final_613d643af583a00069f1002a_151420.mp4"
        urls["엑스맨: 퍼스트 클래스"] = "https://cdn.kapwing.com/final_613d698c69c9200071d1956e_678514.mp4"
        urls["스텝업4 : 레볼루션"] = "https://cdn.kapwing.com/final_613d671a10c76c0090ad198b_3785.mp4"
        urls["스텝 업 3D"] = "https://cdn.kapwing.com/final_613d64a2ce0bfc005986fc96_318088.mp4"
        urls["진격의 거인: 홍련의 화살"] = "https://cdn.kapwing.com/final_613d8a10d41fe1006f3693d3_728061.mp4"


        movieTitle = findViewById(R.id.movieTitle)
        rating = findViewById(R.id.rating)
        genre = findViewById(R.id.genre)
        showtime = findViewById(R.id.showtime)
        audit = findViewById(R.id.audit)
        poster = findViewById(R.id.poster)
        countryAndYear = findViewById(R.id.countryAndYear)
        year = findViewById(R.id.year)
        detail_genre = findViewById(R.id.detail_genre)
        director = findViewById(R.id.director)
        actors = findViewById(R.id.actors)
        overview = findViewById(R.id.overview)
        playerView = findViewById(R.id.playerView)
        share = findViewById(R.id.share)
        image2 = findViewById(R.id.image2)
        videobox = findViewById(R.id.videobox)

        // 인텐트에서 온 정보로 요소 설정
        parcelItem = intent.getParcelableExtra<ParcelItem>("parcelItem")
        movieTitle.setText(parcelItem?.title)
        rating.setText(parcelItem?.rating.toString())
        genre.setText(parcelItem?.genre)
        showtime.setText(parcelItem?.showtime)
        audit.setText(parcelItem?.audit)
        val cny = "${parcelItem?.country}, ${parcelItem?.year}"
        countryAndYear.setText(cny)
        year.setText(parcelItem?.year)
        detail_genre.setText(parcelItem?.genre)
        director.setText(parcelItem?.director)
        actors.setText(parcelItem?.actors)
        overview.setText(parcelItem?.overview)
        val imgId: String? = parcelItem?.poster
        posterPath = "http://image.tmdb.org/t/p/w200${imgId}"
        Glide.with(this)
            .load(posterPath)
            .fitCenter()
            .into(poster)

        currentTitle = parcelItem?.title
        // 비디오 URL 설정은 한 번만 하면 되기 때문에 여기서 한다.
        simpleExoPlayer = SimpleExoPlayer.Builder(this).build()
        playerView.player = simpleExoPlayer
        val videoUrl = urls.get(movieTitle.text.toString())
        val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
        val userAgent = Util.getUserAgent(this, this.applicationInfo.name)
        val factory = DefaultDataSourceFactory(this, userAgent)
        val progressiveMediaSource = ProgressiveMediaSource.Factory(factory).createMediaSource(mediaItem)
        simpleExoPlayer.setMediaSource(progressiveMediaSource)
        simpleExoPlayer.prepare()

        // 버튼 누르면 영화 제목에 맞는 영상 틀도록 설정
        val btnPlay: Button = findViewById(R.id.btnPlay)
        btnPlay.setOnClickListener({
            poster.visibility = View.INVISIBLE
            videobox.visibility = View.VISIBLE
            simpleExoPlayer.play()
        })

        exoFullScreen = playerView.findViewById(R.id.exo_fullscreen)
        exoFullScreen.setOnClickListener {
            val intent = Intent(this, FullScreenActivity::class.java)
            intent.putExtra("url", videoUrl)
            setSharedData("prefPlay", "position${currentTitle}", simpleExoPlayer.currentPosition.toInt())
            intent.putExtra("title", currentTitle)
            startActivity(intent)
        }
        
        // 리싸이클러뷰에 요소들 설정
        custom1 = findViewById(R.id.custom1)
        val mLayoutManager1: LinearLayoutManager = LinearLayoutManager(this)
        mLayoutManager1.orientation = LinearLayoutManager.HORIZONTAL
        val recycler1 = custom1.recycler
        recycler1.layoutManager = mLayoutManager1
        val mAdapter1: HorizontalAdapter = HorizontalAdapter()
        val data1: ArrayList<Movies> = ArrayList<Movies>()
        data1.add(Movies(R.drawable.poster_chocolate, "찰리와 초콜릿 공장"))
        data1.add(Movies(R.drawable.poster_stepup3, "스텝 업 3D"))
        data1.add(Movies(R.drawable.poster_stepup4, "스텝업4 : 레볼루션"))
        data1.add(Movies(R.drawable.poster_spiderman, "스파이더맨"))
        data1.add(Movies(R.drawable.poster_spiderman2, "스파이더맨 2"))
        data1.add(Movies(R.drawable.poster_spiderman3, "스파이더맨 3"))
        mAdapter1.setData(data1)
        mAdapter1.setOnItemClickListener(object: HorizontalAdapter.ItemClickListener {
            override fun onItemClick(view: View, pos: Int) {
                findMovieCode(data1.get(pos).getText(), Item())
            }
        })
        recycler1.adapter = mAdapter1

        custom2 = findViewById(R.id.custom2)
        val mLayoutManager2: LinearLayoutManager = LinearLayoutManager(this)
        mLayoutManager2.orientation = LinearLayoutManager.HORIZONTAL
        val recycler2 = custom2.recycler
        recycler2.layoutManager = mLayoutManager2
        val mAdapter2: HorizontalAdapter = HorizontalAdapter()
        val data2: ArrayList<Movies> = ArrayList<Movies>()
        data2.add(Movies(R.drawable.poster_inception, "인셉션"))
        data2.add(Movies(R.drawable.poster_madmax, "매드 맥스: 분노의 도로"))
        data2.add(Movies(R.drawable.poster_zootopia, "주토피아"))
        data2.add(Movies(R.drawable.poster_xman_first, "엑스맨: 퍼스트 클래스"))
        data2.add(Movies(R.drawable.poster_sourcecode, "소스 코드"))
        mAdapter2.setData(data2)
        mAdapter2.setOnItemClickListener(object: HorizontalAdapter.ItemClickListener {
            override fun onItemClick(view: View, pos: Int) {
                findMovieCode(data2.get(pos).getText(), Item())
            }
        })
        recycler2.adapter = mAdapter2

        custom3 = findViewById(R.id.custom3)
        var mLayoutManager3 = LinearLayoutManager(this)
        mLayoutManager3.orientation = LinearLayoutManager.HORIZONTAL
        var recycler3 = custom3.recycler
        recycler3.layoutManager = mLayoutManager3
        var mAdapter3 = HorizontalAdapter()
        var data3: ArrayList<Movies> = ArrayList<Movies>()
        data3.add(Movies(R.drawable.poster, "수어사이드 스쿼드"))
        data3.add(Movies(R.drawable.poster_attack, "진격의 거인: 홍련의 화살"))
        data3.add(Movies(R.drawable.poster_midnight, "심야괴담회"))
        data3.add(Movies(R.drawable.poster_callcenter, "사랑의 콜센타"))
        data3.add(Movies(R.drawable.poster_parasite, "기생충"))
        data3.add(Movies(R.drawable.poster_hocc, "당신이 혹하는 사이"))
        mAdapter3.setOnItemClickListener(object: HorizontalAdapter.ItemClickListener {
            override fun onItemClick(view: View, pos: Int) {
                findMovieCode(data3.get(pos).getText(), Item())
            }
        })
        mAdapter3.setData(data3)
        recycler3.adapter = mAdapter3

        image2.setOnClickListener({
            if (state_flag==false) {
                image2.setImageResource(R.drawable.heart_selected)
                Toast.makeText(applicationContext, "영화를 찜했어요", Toast.LENGTH_SHORT).show()
                state_flag = true
            }
            else {
                image2.setImageResource(R.drawable.heart_unselected)
                Toast.makeText(applicationContext, "영화 찜하기를 취소했어요", Toast.LENGTH_SHORT).show()
                state_flag = false
            }
        })
        
        // 아이콘 클릭하면 자세한 설명 나오도록 설정
        detailExplain = findViewById(R.id.detailExplain)
        explainIcon = findViewById(R.id.explainIcon)
        explainIcon.setImageResource(R.drawable.ic_show_explain)
        detail = findViewById(R.id.detail)
        var showFlag:Boolean = false
        detailExplain.setOnClickListener({
            if (showFlag == false) {
                detail.visibility = View.VISIBLE
                explainIcon.setImageResource(R.drawable.ic_close_explain)
                showFlag = true
            }
            else {
                detail.visibility = View.GONE
                explainIcon.setImageResource(R.drawable.ic_show_explain)
                showFlag = false
            }
        })
        // sharesheet을 이용해 영화 제목 공유하기
        share.setOnClickListener({
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, parcelItem?.title)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        })
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        parcelItem = intent!!.getParcelableExtra<ParcelItem>("parcelItem")
        movieTitle.setText(parcelItem?.title)
        rating.setText(parcelItem?.rating.toString())
        genre.setText(parcelItem?.genre)
        showtime.setText(parcelItem?.showtime)
        audit.setText(parcelItem?.audit)
        val cny = "${parcelItem?.country}, ${parcelItem?.year}"
        countryAndYear.setText(cny)
        year.setText(parcelItem?.year)
        detail_genre.setText(parcelItem?.genre)
        director.setText(parcelItem?.director)
        actors.setText(parcelItem?.actors)
        overview.setText(parcelItem?.overview)
        val imgId: String? = parcelItem?.poster
        posterPath = "http://image.tmdb.org/t/p/w200${imgId}"
        Glide.with(this)
            .load(posterPath)
            .fitCenter()
            .into(poster)

        currentTitle = parcelItem?.title
        val videoUrl = urls.get(movieTitle.text.toString())
        val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
        val userAgent = Util.getUserAgent(this, this.applicationInfo.name)
        val factory = DefaultDataSourceFactory(this, userAgent)
        val progressiveMediaSource = ProgressiveMediaSource.Factory(factory).createMediaSource(mediaItem)
        simpleExoPlayer.setMediaSource(progressiveMediaSource)
        simpleExoPlayer.prepare()
        val position = getSharedIntData("prefPlay", "position${currentTitle}")
        simpleExoPlayer.seekTo(position.toLong())

        exoFullScreen.setOnClickListener {
            val newintent = Intent(this, FullScreenActivity::class.java)
            newintent.putExtra("url", videoUrl)
            setSharedData("prefPlay", "position${currentTitle}", simpleExoPlayer.currentPosition.toInt())
            newintent.putExtra("title", currentTitle)
            startActivity(newintent)
        }

        state_flag = getSharedBooleanData("prefHeart", "heart${currentTitle}")
        // 하트 누르면 찜하기 되도록 설정
        if (state_flag == false) {
            image2.setImageResource(R.drawable.heart_unselected)
        } else {
            image2.setImageResource(R.drawable.heart_selected)
        }
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onStart() {
        super.onStart()
        state_flag = getSharedBooleanData("prefHeart", "heart${currentTitle}")
        // 하트 누르면 찜하기 되도록 설정
        if (state_flag == false) {
            image2.setImageResource(R.drawable.heart_unselected)
        } else {
            image2.setImageResource(R.drawable.heart_selected)
        }
    }
    // onResume() 메서드가 호출되면 비디오 재생 위치 기억하고 해당 위치 찾아가기
    // 이렇게 한 이유는 onPause() 메서드가 호출된 다음에도 동영상 재생 위치를 기억해 여기서부터 영상을 재생해야 하기 때문이다.
    override fun onResume() {
        super.onResume()
        val position = getSharedIntData("prefPlay", "position${currentTitle}")
        simpleExoPlayer.seekTo(position.toLong())

    }

    override fun onPause() {
        super.onPause()
        simpleExoPlayer.stop()
        duration = simpleExoPlayer.duration.toInt()
        val progress = simpleExoPlayer.currentPosition.toInt()
        simpleExoPlayer.playWhenReady = true
        // 이건 이대로 설정한다. 계속해서 최신 상태를 업데이트해야 하기 때문이다.
        setSharedData("prefPlay", "position${currentTitle}", progress)
        setSharedData("prefHeart", "heart${currentTitle}", state_flag)
        simpleExoPlayer.playWhenReady = false

        val record = Record(null, currentTitle!!, posterPath!!, (progress/4200).toLong(), (duration/4200).toLong())
        val check = MainActivity2.helper!!.selectItemRecord(currentTitle!!)
        if (check != -1) {
            MainActivity2.helper!!.updateRecord(check, record)
        } else {
            MainActivity2.helper!!.insertRecord(record)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        simpleExoPlayer.release()
    }

    fun setSharedData(name: String, key: String, data: Int) {
        val pref: SharedPreferences = getSharedPreferences(name, Activity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putInt(key, data)
        editor.apply()
    }
    fun setSharedData(name: String, key: String, data: Boolean) {
        val pref: SharedPreferences = getSharedPreferences(name, Activity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putBoolean(key, data)
        editor.apply()
    }

    fun getSharedIntData(name: String, key: String): Int {
        val pref: SharedPreferences = getSharedPreferences(name, Activity.MODE_PRIVATE)
        return pref.getInt(key, 0)
    }
    fun getSharedBooleanData(name: String, key: String): Boolean {
        val pref: SharedPreferences = getSharedPreferences(name, Activity.MODE_PRIVATE)
        return pref.getBoolean(key, false)
    }

    fun setSharedData(name: String, key: String, data: String) {
        val pref: SharedPreferences = getSharedPreferences(name, Activity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putString(key, data)
        editor.apply()
    }

    // 영화 이름 가지고 영화 코드 가져오는 첫 번째 메서드
    fun findMovieCode(movieName: String, item: Item) {
        val url = "https://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=${kobisApiKey}&movieNm=${movieName}"
        val request = object: StringRequest(
            Request.Method.GET,
            url,
            {
                if (it.indexOf("faultInfo") > -1) {
                    var builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setTitle("키 사용량 초과").setMessage("아래 사이트에 가입 후 키를 발급받아 그 키로 사용하세요\nhttp://kobis.or.kr/kobisopenapi")
                    var dialog: AlertDialog = builder.create()
                    dialog.show()
                } else {
                    processResponseCustom(it, item, movieName)
                }
            },
            {
                Log.e("Error", "오류 발생 -> ${it.message}")
            }
        ) {}
        request.setShouldCache(false)
        MainActivity2.requestQueue?.add(request)
    }
    // 영화 이름 가지고 영화 코드 가져오기
    fun processResponseCustom(response: String, item: Item, movieName: String) {
        val gson = Gson()
        val customMovieList = gson.fromJson(response, CustomMovieList::class.java)
        val movielist = customMovieList.movieListResult.movieList
        for (i in 0 until(movielist!!.size)) {
            var mItem = movielist?.get(i)
            var tmptitle = mItem.movieNm
            if (tmptitle.equals(movieName)) {
                sendDetailsCustom(movielist?.get(i)?.movieCd, item)
            }
        }
    }
    // 영화 코드 가지고 영화 상세정보 얻어오기
    fun sendDetailsCustom(movieCd: String?, item: Item) {
        if (movieCd != null) {
            val url = "https://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key=${kobisApiKey}&movieCd=${movieCd}"
            val request = object: StringRequest(
                Request.Method.GET,
                url,
                {
                    processDetailsResponseCutsom(it, item)
                },
                {
                    Log.e("Error", "오류 발생 -> ${it.message}")
                }
            ) {}
            request.setShouldCache(false)
            MainActivity2.requestQueue?.add(request)
        }
    }
    fun processDetailsResponseCutsom(response: String, item: Item) {
        val gson = Gson()
        val movieInfoDetails = gson.fromJson(response, MovieInfoDetails::class.java)
        val movieDetails = movieInfoDetails.movieInfoResult.movieInfo
        item.showtime = movieDetails.showTm
        item.title = movieDetails.movieNm
        item.year = movieDetails.openDt
        if (movieDetails.nations.size > 0) {
            item.country = movieDetails.nations.get(0).nationNm
        }
        if (movieDetails.genres.size > 0) {
            item.genre = movieDetails.genres.get(0).genreNm
        }
        if (movieDetails.directors.size > 0) {
            item.director = movieDetails.directors.get(0).peopleNm
        }
        if (movieDetails.actors.size > 0) {
            item.actors = movieDetails.actors.get(0).peopleNm
        }
        if (movieDetails.audits.size > 0) {
            item.audit = movieDetails.audits.get(0).watchGradeNm
        }
        sendTMDBSearchCustom(item.title, item)

    }
    // 영화 이름을 바탕으로 TMDB에서 영화 정보 탐색 메서드
    fun sendTMDBSearchCustom(movieName: String?, item: Item) {
        if(movieName != null) {
            val apiKey = "52023190e7574a346ef78d1397106945"
            val url = "https://api.themoviedb.org/3/search/movie?api_key=${apiKey}&query=${movieName}&language=ko-KR&page=1"
            val request = object: StringRequest(
                Request.Method.GET,
                url,
                {
                    processTMDBSearchResponseCustom(it, item)
                },
                {
                    Log.e("Error", "오류 발생 -> ${it.message}")
                }
            ) {}
            request.setShouldCache(false)
            MainActivity2.requestQueue?.add(request)
        }
    }
    fun processTMDBSearchResponseCustom(response: String, item: Item) {
        val gson = Gson()
        val tmdbMovieDetails = gson.fromJson(response, TmdbMovieDetails::class.java)
        val movieResult = tmdbMovieDetails.results
        for (i in 0 until(movieResult.size)) {
            var movieresult = movieResult.get(i)
            var tmptitle = movieresult.title?.replace(" ", "")
            var notitle = item.title?.replace(" ", "")

            if (tmptitle.equals(notitle)) {
                item.poster = movieresult.poster_path
                item.overview = movieresult.overview
                item.rating = movieresult.vote_average

                var intent: Intent = Intent(applicationContext, PlayActivity::class.java)
                var parcelItem: ParcelItem = ParcelItem(item.title, item.rating, item.genre, item.showtime,
                    item.audit, item.country, item.year, item.director,
                    item.actors, item.overview, item.poster)
                intent.putExtra("parcelItem", parcelItem)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
            }
        }
    }
}