package info.androidhive.retrofit.activity;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.adapter.MoviesAdapter;
import info.androidhive.retrofit.adapter.SimilarMovieAdapter;
import info.androidhive.retrofit.another.ItemTouchListener;
import info.androidhive.retrofit.db.FavoriteDatabase;
import info.androidhive.retrofit.model.Movie.Movie;
import info.androidhive.retrofit.model.Movie.MoviesResponse;
import info.androidhive.retrofit.model.trailer.Trailer;
import info.androidhive.retrofit.model.trailer.TrailerResponse;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetail extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, SimilarMovieAdapter.ViewHolder.ItemClickListener {

    private final static String API_KEY = "53f93d98fdababca6efda85ba6ccc57a";
    private int querytrailer;
    TextView text_info_moviedetail;
    TextView title_info_moviedetail;
    ImageView image_info_moviedetail;
    private List <Movie> movies;
    SimilarMovieAdapter similarMovieAdapter;
    public  static      FavoriteDatabase favoriteDatabase;

    private static final String TAG = Search.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movies = new ArrayList <>();


        Intent getid = getIntent();
        Bundle bundle = getid.getExtras();
        querytrailer = bundle.getInt("TYPE");


        getQueryInformation();
        image_info_moviedetail = findViewById(R.id.image_info_moviedetail);
        text_info_moviedetail = findViewById(R.id.text_info_moviedetail);
        title_info_moviedetail = findViewById(R.id.title_info_moviedetail);


        getQueryTrailer();
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_movie);
        youTubePlayerView.initialize(ApiClient.YOUTUBE_API_KEY, this);

         favoriteDatabase= Room.databaseBuilder(getApplicationContext(),FavoriteDatabase.class,"myfavdb").allowMainThreadQueries().build();

        getQueryInformationSimilar();


        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MovieDetail.this, MainActivity.class);
                startActivity(intent);
            }
        });


        ImageView user = findViewById(R.id.user);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetail.this, Saved.class);
                startActivity(intent);
            }
        });

        ImageView search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetail.this, Search.class);
                startActivity(intent);
            }
        });
    }


    public void getQueryTrailer() {

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call <TrailerResponse> call = apiService.getTrailer(querytrailer);
        call.enqueue(new Callback <TrailerResponse>() {
            @Override
            public void onResponse(Call <TrailerResponse> call, Response <TrailerResponse> response) {
                int statusCode = response.code();
                TrailerResponse trailerResponse = response.body();
                List <Trailer> trailers = trailerResponse.getResults();


            }

            @Override
            public void onFailure(Call <TrailerResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });


    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void getQueryInformation() {

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call <Movie> call = apiService.getMovieDetail(querytrailer);
        call.enqueue(new Callback <Movie>() {
            @Override
            public void onResponse(Call <Movie> call, Response <Movie> response) {

                int statusCode = response.code();
                Movie movie = response.body();
                bindView(movie);

            }

            @Override
            public void onFailure(Call <Movie> call, Throwable t) {

            }
        });

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void bindView(Movie movie) {
        text_info_moviedetail.setText(movie.getOverview());
        title_info_moviedetail.setText(movie.getTitle());
        Picasso.with(getBaseContext()).load("https://image.tmdb.org/t/p/original" + movie.getPosterPath()).into(image_info_moviedetail);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call <TrailerResponse> call = apiService.getTrailer(querytrailer);
        call.enqueue(new Callback <TrailerResponse>() {
            @Override
            public void onResponse(Call <TrailerResponse> call, Response <TrailerResponse> response) {
                try {


                int statusCode = response.code();
                TrailerResponse trailerResponse = response.body();
                List <Trailer> trailers = trailerResponse.getResults();
                if (trailers.size() > 0)
                    youTubePlayer.loadVideo(trailers.get(0).getKey());
                }catch (Exception e){}

            }

            @Override
            public void onFailure(Call <TrailerResponse> call, Throwable t) {

            }
        });


    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }


///////////////////////////////////////////////////////////////////////////////////////////////////

    public void getQueryInformationSimilar() {

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }

        final RecyclerView recyclerView3 = (RecyclerView) findViewById(R.id.similar_movie_recycler);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));

        recyclerView3.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false));

        similarMovieAdapter = new SimilarMovieAdapter(movies ,R.layout.list_item_similar_movie, this,this);
        recyclerView3.setAdapter(similarMovieAdapter);


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call <MoviesResponse> call3 = apiService.getSimilarMovie(querytrailer);

        call3.enqueue(new Callback <MoviesResponse>() {

            @Override
            public void onResponse(Call <MoviesResponse> call, Response <MoviesResponse> response) {
                int statusCode = response.code();
                try {
                    movies.addAll(response.body().getResults());
                    similarMovieAdapter.notifyDataSetChanged();

//                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie,
//                        getApplicationContext(),));
                }catch (Exception e)
                {}


            }

            @Override
            public void onFailure(Call <MoviesResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }

        });


    }

    @Override
    public void onClick(View view, int position) {
        final Movie movie = movies.get(position);
        Intent i = new Intent(this, MovieDetail.class);


        i.putExtra("TYPE", movie.getId());
        startActivity(i);
    }
}
