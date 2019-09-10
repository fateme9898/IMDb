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
import info.androidhive.retrofit.adapter.SimilarMovieAdapter;
import info.androidhive.retrofit.adapter.SimilarTvAdapter;
import info.androidhive.retrofit.another.ItemTouchListener;
import info.androidhive.retrofit.db.FavoriteDatabase;
import info.androidhive.retrofit.model.Movie.Movie;
import info.androidhive.retrofit.model.Tv.Tv;
import info.androidhive.retrofit.model.Tv.TvResponse;
import info.androidhive.retrofit.model.trailer.Trailer;
import info.androidhive.retrofit.model.trailer.TrailerResponse;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvDetail extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, SimilarTvAdapter.ViewHolder.ItemClickListener {

    private final static String API_KEY = "53f93d98fdababca6efda85ba6ccc57a";
    private int querytrailer;
    TextView text_info_tvdetail;
    TextView title_info_tvdetail;
    ImageView image_info_tvdetail;
    public static FavoriteDatabase favoriteDatabase;
    private List <Tv> tvs;
    SimilarTvAdapter similarTvAdapter;

    private static final String TAG = Search.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_detail);

        tvs = new ArrayList <>();

        Intent getid = getIntent();
        Bundle bundle = getid.getExtras();
        querytrailer = bundle.getInt("TYPE");


        getQueryInformation();
        image_info_tvdetail = findViewById(R.id.image_info_tvdetail);
        text_info_tvdetail = findViewById(R.id.text_info_tvdetail);
        title_info_tvdetail = findViewById(R.id.title_info_tvdetail);


        getQueryTrailer();
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_tv);
        youTubePlayerView.initialize(ApiClient.YOUTUBE_API_KEY, this);


        favoriteDatabase= Room.databaseBuilder(getApplicationContext(),FavoriteDatabase.class,"myfavdb").allowMainThreadQueries().build();
        getQueryInformationSimilar();

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TvDetail.this, MainActivity.class);
                startActivity(intent);
            }
        });


        ImageView user = findViewById(R.id.user);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TvDetail.this, Saved.class);
                startActivity(intent);
            }
        });

        ImageView search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TvDetail.this, Search.class);
                startActivity(intent);
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

        Call <Tv> call = apiService.getTvDetail(querytrailer);
        call.enqueue(new Callback <Tv>() {
            @Override
            public void onResponse(Call <Tv> call, Response <Tv> response) {

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
    public void bindView(Tv tv) {
        text_info_tvdetail.setText(tv.getOverview());
        title_info_tvdetail.setText(tv.getName());
        Picasso.with(getBaseContext()).load("https://image.tmdb.org/t/p/original" + tv.getPosterPath()).into(image_info_tvdetail);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

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
                List <Trailer> trailers = trailerResponse.getResults();


            }

            @Override
            public void onFailure(Call <TrailerResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {


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

    /////////////////////////////////////////////////////////////////////////////////////////////////
    public void getQueryInformationSimilar() {

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }

        final RecyclerView recyclerView3 = (RecyclerView) findViewById(R.id.similar_tv_recycler);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));

        recyclerView3.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false));

       similarTvAdapter  = new SimilarTvAdapter(tvs ,R.layout.list_item_similar_tv, this,this);
        recyclerView3.setAdapter(similarTvAdapter);





        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call <TvResponse> call3 = apiService.getSimilarTv(querytrailer);

        call3.enqueue(new Callback <TvResponse>() {

            @Override
            public void onResponse(Call <TvResponse> call, Response <TvResponse> response) {
                int statusCode = response.code();
                try {
                    tvs.addAll(response.body().getResults());
                    similarTvAdapter.notifyDataSetChanged();

                }catch (Exception e)
                {}



            }

            @Override
            public void onFailure(Call <TvResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }

        });
    }

    @Override
    public void onClickTv(View view, int position) {
        final Tv tv = tvs.get(position);
        Intent i = new Intent(this, TvDetail.class);


        i.putExtra("TYPE", tv.getId());
        startActivity(i);
    }
}
///////////////////////////////////////////////////////////////////////////////////////////////////