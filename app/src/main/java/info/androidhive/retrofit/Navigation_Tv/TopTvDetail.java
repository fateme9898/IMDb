package info.androidhive.retrofit.Navigation_Tv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

import java.util.List;

import info.androidhive.retrofit.Navigation_Movie.TopMovieDetail;
import info.androidhive.retrofit.R;
import info.androidhive.retrofit.activity.MainActivity;
import info.androidhive.retrofit.activity.PeapleDetail;
import info.androidhive.retrofit.activity.Saved;
import info.androidhive.retrofit.activity.Search;
import info.androidhive.retrofit.activity.TvDetail;
import info.androidhive.retrofit.adapter.SimilarTvAdapter;
import info.androidhive.retrofit.another.ItemTouchListener;
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

public class TopTvDetail extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    private final static String API_KEY = "53f93d98fdababca6efda85ba6ccc57a";
    private int querytrailer;
    TextView text_info_moviedetail;
    TextView title_info_moviedetail;
    ImageView image_info_moviedetail;

    private static final String TAG = TopTvDetail.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_tv_detail);




        Intent getid = getIntent();
        Bundle bundle = getid.getExtras();
        querytrailer = bundle.getInt("TYPE");


        getQueryInformation();
        image_info_moviedetail = findViewById(R.id.image_info_tvtop);
        text_info_moviedetail = findViewById(R.id.text_info_tvtop);
        title_info_moviedetail = findViewById(R.id.title_info_tvtop);

        getQueryTrailer();
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_top_tv);
        youTubePlayerView.initialize(ApiClient.YOUTUBE_API_KEY , this);

getQueryInformationSimilar();


        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(TopTvDetail.this , MainActivity.class);
                startActivity(intent);
            }
        });


        ImageView user = findViewById(R.id.user);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopTvDetail.this, Saved.class);
                startActivity(intent);
            }
        });

        ImageView search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopTvDetail.this, Search.class);
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

        recyclerView3.addOnItemTouchListener(new ItemTouchListener(recyclerView3) {
            @Override
            public boolean onClick(RecyclerView parent, View view, int position, long id) {
                SimilarTvAdapter similarTvAdapter = (SimilarTvAdapter) recyclerView3.getAdapter();
                Tv tv = SimilarTvAdapter.tvs.get(position);
                Intent intent = new Intent(TopTvDetail.this, TopTvDetail.class);

                intent.putExtra("TYPE", tv.getId());
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onLongClick(RecyclerView parent, View view, int position, long id) {
                return false;
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });



        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call <TvResponse> call3 = apiService.getSimilarTv(querytrailer);

        call3.enqueue(new Callback <TvResponse>() {

            @Override
            public void onResponse(Call <TvResponse> call, Response <TvResponse> response) {
                int statusCode = response.code();
                List <Tv> tv = response.body().getResults();
                recyclerView3.setAdapter(new SimilarTvAdapter(tv, R.layout.list_item_similar_tv,
                        getApplicationContext()));


            }

            @Override
            public void onFailure(Call <TvResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }

        });
    }

}
