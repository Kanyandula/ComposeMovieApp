package com.example.composemovieapp.interactors.movie

import com.example.composemovieapp.cache.AppDatabaseFake
import com.example.composemovieapp.cache.MovieDaoFake
import com.example.composemovieapp.cache.model.MovieEntityMapper
import com.example.composemovieapp.domain.model.Movie
import com.example.composemovieapp.interactors.movie_detail.GetMovie
import com.example.composemovieapp.interactors.movie_list.SearchMovies
import com.example.composemovieapp.network.RetrofitService
import com.example.composemovieapp.network.data.MockWebServerResponse
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

class GetMoviesTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val appDatabase = AppDatabaseFake()
    private val DUMMY_TOKEN = "fdnkghdjdkjdfh"
    private val DUMMY_QUERY = "doesn't matter"

    //system in test
    private lateinit var  getMovie: GetMovie
    private val MOVIE_ID = 460465


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
        getMovie = GetMovie(
            movieDao = movieDao,
            entityMapper = entityMapper,
            movieDtoMapper = dtoMapper,
            retrofitService = retrofitService

        )
    }

    /**
     * 1. Get some movies from the network and insert into cache
     * 2. Try to movies  by their specific movie id
     */

    @Test
    fun getMoviesFromNetwork_getMovieById(): Unit = runBlocking {
        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponse.movieListResponse)
        )

        // confirm the cache is empty to start
        assert(movieDao.getAllMovies(1, 20).isEmpty())

        // get movies from network and insert into cache
        val searchResult = searchMovies.execute(DUMMY_TOKEN, 1, DUMMY_QUERY, true).toList()

        // confirm the cache is no longer empty
        assert(movieDao.getAllMovies(1, 20).isNotEmpty())

        // run use case
        val  movieAsFlow = getMovie.execute(MOVIE_ID,  true).toList()

        // first emission should be `loading`
        assert( movieAsFlow[0].loading)

        // second emission should be the movie
        val movie =  movieAsFlow[1].data
        assert(movie?.id == MOVIE_ID)

        // confirm it is actually a Movie object
        assert(movie is Movie)

        // 'loading' should be false now
        assert(!movieAsFlow[1].loading)
    }

    /**
     * 1. Try to get a movie that does not exist in the cache
     * Result should be:
     * 1. Movie is retrieved from network and inserted into cache
     * 2. Movie is returned as flow from cache
     */

    @Test
    fun attemptGetNullMovieFromNetwork_emitMoviesFromCache(): Unit = runBlocking{
        //condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponse.movieWithId460465)
        )
        //confirm the cache is empty to start
        assert(movieDao.getAllMovies(1,20).isEmpty())

        val movieAsFlow = getMovie.execute(MOVIE_ID, true).toList()


        // first emission should be `loading`
        assert(movieAsFlow[0].loading)

        // second emission should be the movie
        val movie = movieAsFlow[1].data
        assert(movie?.id == MOVIE_ID)

        // confirm the movie is in the cache now
        assert(movieDao.getMovieById(MOVIE_ID)?.id == MOVIE_ID)

        // confirm it is actually a Movie object
        assert(movie is Movie)

        //Ensure loading is false now
        assert(!movieAsFlow[1].loading)
    }



    @AfterEach
    fun tearDown(){
        mockWebServer.shutdown()
    }

}