package info.androidhive.retrofit.activity;

import android.arch.persistence.room.Room;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.retrofit.Navigation_Movie.NavigationMovie;
import info.androidhive.retrofit.Navigation_Tv.NavigationTv;
import info.androidhive.retrofit.R;
import info.androidhive.retrofit.adapter.MoviesAdapter;
import info.androidhive.retrofit.adapter.PeapleAdapter;
import info.androidhive.retrofit.adapter.NowPlayingAdapter;
import info.androidhive.retrofit.adapter.TvAdapter;
import info.androidhive.retrofit.another.ItemTouchListener;
import info.androidhive.retrofit.db.FavoriteDatabase;
import info.androidhive.retrofit.db.FavoriteList;
import info.androidhive.retrofit.model.Movie.Movie;
import info.androidhive.retrofit.model.Movie.MoviesResponse;
import info.androidhive.retrofit.model.people.Peaple;
import info.androidhive.retrofit.model.people.PeapleResponse;
import info.androidhive.retrofit.model.NowPlaying.NowPlaying;
import info.androidhive.retrofit.model.NowPlaying.NowPlayingResponse;
import info.androidhive.retrofit.model.Tv.Tv;
import info.androidhive.retrofit.model.Tv.TvResponse;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.ViewHolder.ItemClickListener {

    ////////////////////////////////////////////////////////////////////////////
    Spinner spinner ;
    ImageView fave_btn;
    MoviesAdapter moviesAdapter;
    private List<Movie> movies;

    private static final String TAG = MainActivity.class.getSimpleName();


    // TODO - insert your themoviedb.org API KEY here
    private final static String API_KEY = "53f93d98fdababca6efda85ba6ccc57a";
    private List<Movie>product_lists;
    public static FavoriteDatabase favoriteDatabase;

    ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        product_lists=new ArrayList<>();
        movies=new ArrayList <>();
///////////////////////Intent///////////////////////////////////////////////////////////////////////

        ImageView imdb = findViewById(R.id.imdb);
        imdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });


        ImageView search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Search.class);
                startActivity(intent);
            }
        });

        RelativeLayout gemma=findViewById(R.id.gemma);
        gemma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wegotthiscovered.com/movies/gemma-chan-mcu-actor-played-roles/?utm_source=zergnet.com&utm_medium=referral&utm_campaign=zergnet_4375809"));
                startActivity(browserIntent);
            }
        });

        RelativeLayout hagrid=findViewById(R.id.hagrid);
        hagrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nickiswift.com/138681/the-real-reason-you-dont-hear-from-hagrid-anymore/?utm_source=zergnet.com&utm_medium=referral&utm_campaign=zergnet_4376903&utm_content=3"));
                startActivity(browserIntent);
            }
        });



        RelativeLayout jakob=findViewById(R.id.jakob);
        jakob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nickiswift.com/14377/hollywood-wont-cast-taylor-lautner-anymore/?utm_source=zergnet.com&utm_medium=referral&utm_campaign=zergnet_4350955&utm_content=3"));
                startActivity(browserIntent);
            }
        });


//        ImageView account=findViewById(R.id.account);
//        account.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(MainActivity.this , Saved.class);
//                startActivity(intent);
//            }
//        });
//

        TextView see_movie=findViewById(R.id.see_movie);
        see_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this ,NavigationMovie.class );
                startActivity(intent);
            }
        });


        TextView see_tv=findViewById(R.id.see_tv);
        see_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this ,NavigationTv.class );
                startActivity(intent);
            }
        });




        //////////////////////////////////////follow app/////////////////////////////////////////////////////

ImageView instagram=findViewById(R.id.instagram);
instagram.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Uri uri = Uri.parse("https://www.instagram.com/imdb/");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/imdb/")));
        }
    }
});

        ImageView twitter=findViewById(R.id.twitter);
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://twitter.com/IMDb?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.twitter.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://twitter.com/IMDb?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor")));
                }
            }
        });



        ImageView facebook=findViewById(R.id.facebook);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.facebook.com/imdb/");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.facebook.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.facebook.com/imdb/")));
                }
            }
        });

///////////////////////////spinner/////////////////////////////////////////////////////////////////////////
        ImageView account = findViewById(R.id.saved);
account.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent  intent = new Intent(MainActivity.this, Saved.class);
        startActivity(intent);


    }
});


        ////////////////////////////////////////////////////////////////////////////////////////
        trailerMethod();
        favoriteDatabase= Room.databaseBuilder(getApplicationContext(),FavoriteDatabase.class,"myfavdb").allowMainThreadQueries().build();

        movieMethod();
        tvMethod();
        peopleMethod();
    }


/////////////////////////////////////METHODS////////////////////////////////////////////////////////
    public  void  trailerMethod(){
            if (API_KEY.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please obtain your API KEY from " +
                        "themoviedb.org first!", Toast.LENGTH_LONG).show();
                return;
            }

            final RecyclerView recyclerView4 = (RecyclerView) findViewById(R.id.trailer_recycler_view);
            recyclerView4.setLayoutManager(new LinearLayoutManager(this));
            recyclerView4.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                    LinearLayoutManager.HORIZONTAL, false));

            recyclerView4.addOnItemTouchListener(new ItemTouchListener(recyclerView4) {
                @Override
                public boolean onClick(RecyclerView parent, View view, int position, long id) {
                    NowPlayingAdapter trailerAdapter = (NowPlayingAdapter) recyclerView4.getAdapter();
                    NowPlaying trailer = trailerAdapter.trailer.get(position);
                    Intent intent = new Intent(MainActivity.this, Trailer_page.class);

                    intent.putExtra("TYPE", trailer.getId());
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
            Call <NowPlayingResponse> call4 = apiService.getNowPlaying(API_KEY);
            call4.enqueue(new Callback <NowPlayingResponse>() {
                @Override
                public void onResponse(Call <NowPlayingResponse> call, Response <NowPlayingResponse>
                        response) {
                    int statusCode = response.code();
                    List <NowPlaying> trailers = response.body().getResults();

                    recyclerView4.setAdapter(new NowPlayingAdapter(trailers, R.layout.now_playing,
                            getApplicationContext()));
                }

                @Override
                public void onFailure(Call <NowPlayingResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });


        }



    ////////////////////////////////////////////////////////////////////////////////////////////////

    public  void  movieMethod(){

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from " +
                    "themovied.org first!", Toast.LENGTH_LONG).show();
            return;
        }

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false));
//
        moviesAdapter = new MoviesAdapter(movies ,R.layout.list_item_movie, this,this);
        recyclerView.setAdapter(moviesAdapter);
//        moviesAdapter.setClickListener(this);


//        recyclerView.addOnItemTouchListener(new ItemTouchListener(recyclerView) {
//            @Override
//            public boolean onClick(RecyclerView parent, View view, int position, long id) {
//                MoviesAdapter moviesAdapter = (MoviesAdapter) recyclerView.getAdapter();
//                Movie movie = moviesAdapter.product_lists.get(position);
//                Intent intent = new Intent(MainActivity.this, MovieDetail.class);
//
//                intent.putExtra("TYPE", movie.getId());
//                startActivity(intent);
//                return false;
//            }
//
//            @Override
//            public boolean onLongClick(RecyclerView parent, View view, int position, long id) {
//                return false;
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean b) {
//
//            }
//        });
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call <MoviesResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback <MoviesResponse>() {
            @Override
            public void onResponse(Call <MoviesResponse> call, Response <MoviesResponse>
                    response) {
                int statusCode = response.code();
                movies.addAll(response.body().getResults());
                moviesAdapter.notifyDataSetChanged();

//                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie,
//                        getApplicationContext(),));
            }

            @Override
            public void onFailure(Call <MoviesResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    public  void  tvMethod(){
        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from " +
                    "themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }

        final RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.movies_recycler_view2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false));



        recyclerView2.addOnItemTouchListener(new ItemTouchListener(recyclerView2) {
            @Override
            public boolean onClick(RecyclerView parent, View view, int position, long id) {
                TvAdapter tvAdapter = (TvAdapter) recyclerView2.getAdapter();
                Tv tv = TvAdapter.tv.get(position);
                Intent intent = new Intent(MainActivity.this, TvDetail.class);

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
        Call <TvResponse> call2 = apiService.getTopRatedTv(API_KEY);
        call2.enqueue(new Callback <TvResponse>() {
            @Override
            public void onResponse(Call <TvResponse> call, Response <TvResponse> response) {
                int statusCode = response.code();
                List <Tv> tv = response.body().getResults();
                recyclerView2.setAdapter(new TvAdapter(tv, R.layout.list_item_tv,
                        getApplicationContext()));
            }

            @Override
            public void onFailure(Call <TvResponse> call2, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public  void  peopleMethod(){
        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from" +
                    " themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }

        final RecyclerView recyclerView3 = (RecyclerView) findViewById(R.id.movies_recycler_view3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));

        recyclerView3.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false));


        recyclerView3.addOnItemTouchListener(new ItemTouchListener(recyclerView3) {
            @Override
            public boolean onClick(RecyclerView parent, View view, int position, long id) {
                PeapleAdapter peapleAdapter = (PeapleAdapter) recyclerView3.getAdapter();
                Peaple peaple = PeapleAdapter.peaple.get(position);
                Intent intent = new Intent(MainActivity.this, PeapleDetail.class);

                intent.putExtra("TYPE", peaple.getId());
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
        Call <PeapleResponse> call3 = apiService.getTopRatedPeaple(API_KEY);
        call3.enqueue(new Callback <PeapleResponse>() {
            @Override
            public void onResponse(Call <PeapleResponse> call, Response <PeapleResponse> response) {
                int statusCode = response.code();
                List <Peaple> peaples = response.body().getResults();
                recyclerView3.setAdapter(new PeapleAdapter(peaples, R.layout.list_item_peaple,
                        getApplicationContext()));


            }

            @Override
            public void onFailure(Call <PeapleResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

    }

    @Override
    public void onClick(View view, int position) {

        final Movie movie = movies.get(position);
        Intent i = new Intent(this, MovieDetail.class);


        i.putExtra("city", movie.getId());
        startActivity(i);

    }
//
//    @Override
//    public void onClick(View view, int position) {
//        final Movie movie = movies.get(position);
//        Intent i = new Intent(this, MovieDetail.class);
//        i.putExtra("city", movie.getId());
//
//        startActivity(i);
//
//    }
}




