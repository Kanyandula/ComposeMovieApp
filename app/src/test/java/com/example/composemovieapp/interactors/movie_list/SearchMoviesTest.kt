package com.example.composemovieapp.interactors.movie_list

import com.example.composemovieapp.cache.AppDatabaseFake
import com.example.composemovieapp.cache.MovieDaoFake
import com.example.composemovieapp.cache.model.MovieEntityMapper
import com.example.composemovieapp.domain.model.Movie
import com.example.composemovieapp.network.RetrofitService
import com.example.composemovieapp.network.data.MockWebServerResponse.movieListResponse
import com.example.composemovieapp.network.model.MovieDtoMapper
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class SearchMoviesTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val appDatabase = AppDatabaseFake()
    private val DUMMY_TOKEN = "fdnkghdjdkjdfh"
    private val DUMMY_QUERY = "doesn't matter"

    //system in test
    private lateinit var  searchMovies: SearchMovies

    //Dependencies
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
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
                .create()))
            .build()
            .create(RetrofitService::class.java)
        movieDao = MovieDaoFake(appDatabaseFake = appDatabase)

        //instantiate the system in test
        searchMovies = SearchMovies(
            movieDao = movieDao,
            retrofitService = retrofitService,
            dtoMapper = dtoMapper,
            entityMapper = entityMapper

        )
    }

    /**
     * 1. Are the movies retrieved from the network?
     * 2. Are the movies inserted into the cache?
     * 3. Are the movies then emitted as a FLOW from the cache to the UI
     */

    @Test
    fun getMoviesFromNetwork_emitMoviesFromCache(): Unit = runBlocking(){
        //condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(movieListResponse)
        )
        //confirm the cache is empty to start

        assert(movieDao.getAllMovies(1,20).isEmpty())

        val flowItem = searchMovies.execute(DUMMY_TOKEN, 1,
            DUMMY_QUERY, true).toList()

        // confirm the cache is not empty
        assert(movieDao.getAllMovies(1,20).isNotEmpty())

        //first emission should be LOADING
        assert(flowItem[0].loading)

        // second emission should be the list of movies
        val movies = flowItem[1].data
        assert(movies?.size?: 0 > 0)

        //confirm they are actually Movie objects
        assert(movies?.get(index = 0) is Movie)

        //Ensure loading is false now
        assert(!flowItem[1].loading)
    }

    @Test
    fun getMoviesFromNetwork_emitHttpError() : Unit = runBlocking {
        //condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .setBody("{}")
        )

        val flowItems = searchMovies.execute(DUMMY_TOKEN,1,
        DUMMY_QUERY,true).toList()

        assert(flowItems[0].loading)

        val error = flowItems[1].error
        assert(error != null)

        assert(!flowItems[1].loading)
    }


    @AfterEach
    fun tearDown(){
        mockWebServer.shutdown()
    }
}