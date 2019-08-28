package info.androidhive.retrofit;

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

import info.androidhive.retrofit.Navigation_Tv.TopTvDetail;
import info.androidhive.retrofit.model.Tv.Tv;
import info.androidhive.retrofit.model.trailer.Trailer;
import info.androidhive.retrofit.model.trailer.TrailerResponse;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AirTvDetail extends YouTubeBaseActivity implements  YouTubePlayer.OnInitializedListener {


    private final static String API_KEY = "53f93d98fdababca6efda85ba6ccc57a";
    private int querytrailer;
    TextView text_info_moviedetail;
    TextView title_info_moviedetail;
    ImageView image_info_moviedetail;

    private static final String TAG = AirTvDetail.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_tv_detail);





        Intent getid = getIntent();
        Bundle bundle = getid.getExtras();
        querytrailer = bundle.getInt("TYPE");


        getQueryInformation();
        image_info_moviedetail = findViewById(R.id.image_info_air);
        text_info_moviedetail = findViewById(R.id.text_info_air);
        title_info_moviedetail = findViewById(R.id.title_info_air);

        getQueryTrailer();
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_air_tv);
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

        Call<Tv> call = apiService.getTvDetail(querytrailer);
        call.enqueue(new Callback<Tv>() {
            @Override
            public void onResponse(Call <Tv> call, Response<Tv> response) {

                int statusCode = response.code();
                Tv tv = response.body();
                bindView(tv);

            }

            @Override
            public void onFailure(Call <Tv> call, Throwable t) {

            }
        });

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void bindView(Tv tv){
        text_info_moviedetail.setText(tv.getOverview());
        title_info_moviedetail.setText(tv.getName());
        Picasso.with(getBaseContext()).load("https://image.tmdb.org/t/p/original" + tv.getPosterPath()).into(image_info_moviedetail);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    public void getQueryTrailer() {

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call <TrailerResponse> call = apiService.getTrailerTv(querytrailer);
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

        Call <TrailerResponse> call = apiService.getTrailerTv(querytrailer);
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
