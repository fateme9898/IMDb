package info.androidhive.retrofit.activity;

import android.content.Intent;
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
import info.androidhive.retrofit.model.NowPlaying.NowPlaying;
import info.androidhive.retrofit.model.trailer.Trailer;
import info.androidhive.retrofit.model.trailer.TrailerResponse;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Trailer_page extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    int id;
    private final static String API_KEY = "53f93d98fdababca6efda85ba6ccc57a";
    private int querytrailer;
    TextView text_info_trailerpage;
    TextView title_info_trailerpage;
    ImageView image_info_trailerpage;

    private static final String TAG = Search.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer_page);


        Intent getid = getIntent();
        Bundle bundle = getid.getExtras();
        querytrailer = bundle.getInt("TYPE");



        getQueryInformation();
       image_info_trailerpage=findViewById(R.id.image_info_trailerpage);
        text_info_trailerpage  =findViewById(R.id.text_info_trailerpage);
        title_info_trailerpage  =findViewById(R.id.title_info_trailerpage);



        getQueryTrailer();
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube);
        youTubePlayerView.initialize(ApiClient.YOUTUBE_API_KEY , this);


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

   Call<NowPlaying> call=apiService.getNowPlaying2(querytrailer);
   call.enqueue(new Callback <NowPlaying>() {
       @Override
       public void onResponse(Call <NowPlaying> call, Response <NowPlaying> response) {

               int statusCode = response.code();
               NowPlaying nowPlaying = response.body();
               bindView(nowPlaying);

       }

       @Override
       public void onFailure(Call <NowPlaying> call, Throwable t) {

       }
   });

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public  void bindView(NowPlaying nowPlaying){
        text_info_trailerpage.setText(nowPlaying.getOverview());
        title_info_trailerpage.setText(nowPlaying.getTitle());
        Picasso.with(getBaseContext()).load("https://image.tmdb.org/t/p/original"+ nowPlaying.getPosterPath()).into(image_info_trailerpage);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

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
///////////////////////////////////////////////////////////////////////////////////////////////////