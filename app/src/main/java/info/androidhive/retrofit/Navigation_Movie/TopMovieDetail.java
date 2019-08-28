package info.androidhive.retrofit.Navigation_Movie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.List;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.activity.Search;
import info.androidhive.retrofit.model.Movie.Movie;
import info.androidhive.retrofit.model.trailer.Trailer;
import info.androidhive.retrofit.model.trailer.TrailerResponse;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopMovieDetail  extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private final static String API_KEY = "53f93d98fdababca6efda85ba6ccc57a";
    private int querytrailer;
    TextView text_info_moviedetail;
    TextView title_info_moviedetail;
    ImageView image_info_moviedetail;

    private static final String TAG = TopMovieDetail.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_movie_detail);



    Intent getid = getIntent();
    Bundle bundle = getid.getExtras();
    querytrailer = bundle.getInt("TYPE");


    getQueryInformation();
    image_info_moviedetail = findViewById(R.id.image_info_movietop);
    text_info_moviedetail = findViewById(R.id.text_info_movietop);
    title_info_moviedetail = findViewById(R.id.title_info_movitop);

        getQueryTrailer();
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_top_movie);
        youTubePlayerView.initialize(ApiClient.YOUTUBE_API_KEY , this);


}


    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void getQueryInformation() {

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Movie> call = apiService.getMovieDetail(querytrailer);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call <Movie> call, Response<Movie> response) {

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

    ///////////////////////////////////////////////////////////////////////////////////////////////////

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
                List<Trailer> trailers = trailerResponse.getResults();


            }

            @Override
            public void onFailure(Call <TrailerResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

    }



    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final  YouTubePlayer youTubePlayer, boolean b) {


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call <TrailerResponse> call = apiService.getTrailer(querytrailer);
        call.enqueue(new Callback <TrailerResponse>() {
            @Override
            public void onResponse(Call <TrailerResponse> call, Response <TrailerResponse> response) {
                int statusCode = response.code();
                TrailerResponse trailerResponse = response.body();
                List <Trailer> trailers = trailerResponse.getResults();
                if (trailers.size() > 0)
                    youTubePlayer.loadVideo(trailers.get(0).getKey());

            }

            @Override
            public void onFailure(Call <TrailerResponse> call, Throwable t) {

            }
        });


    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
