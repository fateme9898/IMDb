package info.androidhive.retrofit.rest;

import info.androidhive.retrofit.model.Credit_people.Example;
import info.androidhive.retrofit.model.Movie.Movie;
import info.androidhive.retrofit.model.Movie.MoviesResponse;
import info.androidhive.retrofit.model.Pop_movie.PopMovieResponse;
import info.androidhive.retrofit.model.Top_movie.TopMovieResponse;
import info.androidhive.retrofit.model.Tv.Tv;
import info.androidhive.retrofit.model.UpComming.UpCommingResponse;
import info.androidhive.retrofit.model.filter_search.FilterMovieResponse;
import info.androidhive.retrofit.model.filter_search.FilterPeopleResponse;
import info.androidhive.retrofit.model.filter_search.FilterTvResponse;
import info.androidhive.retrofit.model.people.KnownFor;
import info.androidhive.retrofit.model.people.Peaple;
import info.androidhive.retrofit.model.people.PeapleResponse;

import info.androidhive.retrofit.model.people.PeopleDetailModel;
import info.androidhive.retrofit.model.search.SearchResponse;
import info.androidhive.retrofit.model.NowPlaying.NowPlaying;
import info.androidhive.retrofit.model.NowPlaying.NowPlayingResponse;
import info.androidhive.retrofit.model.trailer.TrailerResponse;
import info.androidhive.retrofit.model.Tv.TvResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {
    String API_KEY="?api_key="+"53f93d98fdababca6efda85ba6ccc57a";



    @GET("movie/now_playing")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);


    @GET("movie/{movie_id}"+API_KEY)
    Call<Movie> getMovieDetail(@Path("movie_id") int id);


    @GET("movie/{movie_id}/similar"+API_KEY)
    Call <MoviesResponse> getSimilarMovie(@Path("movie_id") int id);

    @GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);


    /////////////////////////////////////////////////////////////////////////////////////////////////
    @GET("tv/top_rated")
    Call<TvResponse> getTopRatedTv(@Query("api_key") String apiKey);

    @GET("tv/{tv_id}"+API_KEY)
    Call<Tv> getTvDetail(@Path("tv_id") int id);

    @GET("tv/{tv_id}/similar"+API_KEY)
    Call <TvResponse> getSimilarTv(@Path("tv_id") int id);
////////////////////////////////////////////////////////////////////////////////////////////////////

    @GET("person/popular")
    Call<PeapleResponse> getTopRatedPeaple(@Query("api_key") String apiKey);

    @GET("person/{person_id}"+API_KEY)
    Call<Peaple> getPeopleDetail(@Path("person_id") int id);

    @GET("person/{person_id}"+API_KEY)
    Call<KnownFor> getPeopleDetail2(@Path("person_id") int id);

    @GET("person/{person_id}"+API_KEY)
    Call<PeopleDetailModel> getPeopleDetail3(@Path("person_id") int id);

    @GET("person/{person_id}/movie_credits"+API_KEY)
    Call <Example> getCredit(@Path("person_id") int id);

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @GET("movie/now_playing")
    Call<NowPlayingResponse> getNowPlaying(@Query("api_key") String apiKey);


    @GET("movie/{movie_id}"+API_KEY)
    Call<NowPlaying> getNowPlaying2(@Path("movie_id") int id);

    ///////////////////////////////////////////////////////////////////////////////////////////////
    @GET("movie/{movie_id}/videos"+API_KEY)
    Call<TrailerResponse> getTrailer(@Path("movie_id") int id );


    @GET("tv/{tv_id}/videos"+API_KEY)
    Call<TrailerResponse> getTrailerTv(@Path("tv_id") int id );



////////////////////////////////////////////////////////////////////////////////////////////////////

    @GET("search/multi")
    Call<SearchResponse> getSearch(@Query("api_key") String apiKey, @Query("query") String query);

    @GET("search/movie")
    Call<FilterMovieResponse> getSearchMovie(@Query("api_key") String apiKey, @Query("query") String query);

    @GET("search/person")
    Call<FilterPeopleResponse> getSearchPeople(@Query("api_key") String apiKey, @Query("query") String query);

    @GET("search/tv")
    Call<FilterTvResponse> getSearchTv(@Query("api_key") String apiKey, @Query("query") String query);

////////////Navigation Movie//////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GET("movie/upcoming")
    Call <UpCommingResponse> getUpMovie(@Query("api_key") String apiKey);


    @GET("movie/top_rated")
    Call <TopMovieResponse> getTopMovie(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call <PopMovieResponse> getPopMovie(@Query("api_key") String apiKey);

///////////Navigation Tv//////////////////////////////////////////////////////////////////////////////////

    @GET("tv/on_the_air")
    Call <TvResponse> getAirTv(@Query("api_key") String apiKey);


    @GET("tv/top_rated")
        Call <TvResponse> getTopTv(@Query("api_key") String apiKey);

    @GET("tv/popular")
    Call <TvResponse> getPopTv(@Query("api_key") String apiKey);


}
