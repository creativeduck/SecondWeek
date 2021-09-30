package com.ssacproject.secondweek.view

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.ssacproject.secondweek.*
import com.ssacproject.secondweek.data.CustomMovieList
import com.ssacproject.secondweek.data.MovieInfoDetails
import com.ssacproject.secondweek.data.TmdbMovieDetails

class MainActivity2 : AppCompatActivity()  {
    lateinit var custom1: CustomRecommendView
    lateinit var custom2: CustomRecommendView
    lateinit var custom3: CustomRecommendView
    private val kobisApiKey: String = "6ddebf77f1bb19cef16e7f57c7e579c0"
    companion object {
        var requestQueue: RequestQueue? = null
        var helper: SQLiteHelper? = null
    }
    var connReceiver: BroadcastReceiver? = null

    var mainMovieList = listOf<MainMovie>(
        MainMovie(R.drawable.poster_attack, "진격의 거인", "홍련의 화살", "내가 본 최고의 애니메이션", "진격의 거인: 홍련의 화살"),
        MainMovie(R.drawable.poster_callcenter, null, "사랑의 콜센타", "매주 목요일 밤 만나요", "사랑의 콜센타"),
        MainMovie(R.drawable.poster_parasite, null, "기생충", "봉준호 감독의 미친 작품", "기생충"),
        MainMovie(R.drawable.poster, "더 수어사이드", "스쿼드", "팀플레이 더 불가능한 빌런들!", "더 수어사이드 스쿼드"),
        MainMovie(R.drawable.poster_morga, null, "모가디슈", "코로나 시국 최고 흥행작", "모가디슈")
    )
    val adapterRecord = RecordAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        // 네트워크 상태 확인 브로드캐스트 리시버 등록
        configureReceiver()
        helper = SQLiteHelper(this, "record", 1)

        requestQueue = Volley.newRequestQueue(applicationContext)

        adapterRecord.mContext = this
        adapterRecord.helper = helper
        adapterRecord.recordList.addAll(helper!!.selectRecord())
        val recyclerRecord : RecyclerView = findViewById(R.id.recyclerRecord)
        recyclerRecord.adapter = adapterRecord
        val mLayoutManagerRecord = LinearLayoutManager(this)
        mLayoutManagerRecord.orientation = LinearLayoutManager.HORIZONTAL
        recyclerRecord.layoutManager = mLayoutManagerRecord
        adapterRecord.setOnItemClickListener(object: RecordAdapter.ItemClickListener {
            override fun onItemClick(view: View, pos: Int) {
                findMovieCode(adapterRecord.recordList.get(pos).title, Item())
            }
        })

        // ViewPager2에 이미지 설정. 또한 해당 아이템 선택하면 PlayActivity 시작하도록 설정하기
        val viewpager: ViewPager2 = findViewById(R.id.viewpager)
        val adapter = CustomPagerAdapter()
        adapter.mainMovieList = mainMovieList
        viewpager.adapter = adapter

        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewpager) {tab, position -> }.attach()

        // 커스텀뷰로 리싸이클러뷰 설정
        custom1 = findViewById(R.id.custom1)
        var mLayoutManager1 = LinearLayoutManager(this)
        mLayoutManager1.orientation = LinearLayoutManager.HORIZONTAL
        var recycler1 = custom1.recycler
        recycler1.layoutManager = mLayoutManager1
        var mAdapter1 = HorizontalAdapter()
        var data1: ArrayList<Movies> = ArrayList<Movies>()
        data1.add(Movies(R.drawable.poster_chocolate, "찰리와 초콜릿 공장"))
        data1.add(Movies(R.drawable.poster_stepup3, "스텝 업 3D"))
        data1.add(Movies(R.drawable.poster_stepup4, "스텝업4 : 레볼루션"))
        data1.add(Movies(R.drawable.poster_spiderman, "스파이더맨"))
        data1.add(Movies(R.drawable.poster_spiderman2, "스파이더맨 2"))
        data1.add(Movies(R.drawable.poster_spiderman3, "스파이더맨 3"))
        mAdapter1.setOnItemClickListener(object: HorizontalAdapter.ItemClickListener {
            override fun onItemClick(view: View, pos: Int) {
                findMovieCode(data1.get(pos).getText(), Item())
            }
        })
        mAdapter1.setData(data1)
        recycler1.adapter = mAdapter1

        custom2 = findViewById(R.id.custom2)
        var mLayoutManager2 = LinearLayoutManager(this)
        mLayoutManager2.orientation = LinearLayoutManager.HORIZONTAL
        var recycler2 = custom2.recycler
        recycler2.layoutManager = mLayoutManager2
        var mAdapter2 = HorizontalAdapter()
        var data2: ArrayList<Movies> = ArrayList<Movies>()
        data2.add(Movies(R.drawable.poster_inception, "인셉션"))
        data2.add(Movies(R.drawable.poster_madmax, "매드 맥스: 분노의 도로"))
        data2.add(Movies(R.drawable.poster_zootopia, "주토피아"))
        data2.add(Movies(R.drawable.poster_xman_first, "엑스맨: 퍼스트 클래스"))
        data2.add(Movies(R.drawable.poster_sourcecode, "소스 코드"))
        mAdapter2.setOnItemClickListener(object: HorizontalAdapter.ItemClickListener {
            override fun onItemClick(view: View, pos: Int) {
                findMovieCode(data2.get(pos).getText(), Item())
            }
        })
        mAdapter2.setData(data2)
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
    }
    // 사용자가 화면과 상호작용하기 전에 보고있던 영상들의 progress를 설정하기 위해서 여기서 호출했다.
    override fun onStart() {
        super.onStart()
        // onStart() 메서드가 호출되면 list의 목록만 갱신해서 리싸이클러뷰의 목록을 갱신한다.
        adapterRecord.recordList.clear()
        adapterRecord.recordList.addAll(helper!!.selectRecord())
        adapterRecord.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(connReceiver)
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
        requestQueue?.add(request)
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
            requestQueue?.add(request)
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
            requestQueue?.add(request)
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
                startActivity(intent)
            }
        }
    }

    fun configureReceiver() {
        connReceiver = ConnectivityReceiver()
        registerReceiver(connReceiver, IntentFilter(CONNECTIVITY_ACTION))
    }
}