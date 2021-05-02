package com.example.composemovieapp.interactors.movie_list

import com.example.composemovieapp.cache.AppDatabaseFake
import com.example.composemovieapp.cache.MovieDaoFake
import com.example.composemovieapp.cache.model.MovieEntityMapper
import com.example.composemovieapp.domain.model.Movie
import com.example.composemovieapp.network.RetrofitService
import com.example.composemovieapp.network.data.MockWebServerResponse
import com.example.composemovieapp.network.model.MovieDtoMapper
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class RestoreMoviesTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val appDatabase = AppDatabaseFake()
    private val DUMMY_TOKEN = "fdnkghdjdkjdfh"
    private val DUMMY_QUERY = "doesn't matter"

    //system in test
    private lateinit var  restoreMovies: RestoreMovies


    //Dependencies
    private lateinit var  searchMovies: SearchMovies
    private lateinit var  retrofitService: RetrofitService
    private lateinit var  movieDao: MovieDaoFake
    private val dtoMapper = MovieDtoMapper()
    private val entityMapper = MovieEntityMapper()


    @BeforeEach
    fun  setUp(){
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/3/")
        retrofitService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                .create()))
            .build()
            .create(RetrofitService::class.java)
        movieDao = MovieDaoFake(appDatabaseFake = appDatabase)

        searchMovies = SearchMovies(
            movieDao = movieDao,
            retrofitService = retrofitService,
            dtoMapper = dtoMapper,
            entityMapper = entityMapper
        )

        //instantiate the system in test
        restoreMovies = RestoreMovies(
            movieDao = movieDao,
            entityMapper = entityMapper
        )
    }


    /**
     * 1. Get so,e movies from the network and insert into the cache
     * 2. Restore and show movies are  retrieved from cache
     */

    @Test
    fun getMoviesFromNetwork_restoreFromCache(): Unit = runBlocking {

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponse.movieListResponse)
        )

        // confirm the cache is empty to start
        assert(movieDao.getAllMovies(1,20).isEmpty())

       val searchResult = searchMovies.execute(DUMMY_TOKEN,1,DUMMY_QUERY, true).toList()

        // confirm the cache is no longer empty
        assert(movieDao.getAllMovies(1,20).isNotEmpty())

        // run our use case
        val flowItems = restoreMovies.execute(1, DUMMY_QUERY).toList()

        // first emission should be `loading`
        assert(flowItems[0].loading)

        val  movies = flowItems[1].data
        assert(movies?.size?: 0 > 0)

        assert(value = movies?.get(index =  0) is Movie)

        assert(!flowItems[1].loading) // loading should be false now

    }

    @AfterEach
    fun tearDown(){
        mockWebServer.shutdown()
    }

}