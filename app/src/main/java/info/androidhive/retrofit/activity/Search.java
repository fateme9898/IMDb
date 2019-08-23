package info.androidhive.retrofit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.adapter.FilterMovieAdapter;
import info.androidhive.retrofit.adapter.FilterPeopleAdapter;
import info.androidhive.retrofit.adapter.FilterTvAdapter;
import info.androidhive.retrofit.adapter.SearchAdapter;
import info.androidhive.retrofit.adapter.TvAdapter;
import info.androidhive.retrofit.model.filter_search.FilterMovie;
import info.androidhive.retrofit.model.filter_search.FilterMovieResponse;
import info.androidhive.retrofit.model.filter_search.FilterPeople;
import info.androidhive.retrofit.model.filter_search.FilterPeopleResponse;
import info.androidhive.retrofit.model.filter_search.FilterTv;
import info.androidhive.retrofit.model.filter_search.FilterTvResponse;
import info.androidhive.retrofit.model.search.Search2;
import info.androidhive.retrofit.model.search.SearchResponse;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search extends AppCompatActivity {
    private EditText text;
    private String querySearch;
    private FilterTvAdapter adapter_tv;
    private FilterMovieAdapter adapter_movie;
    private FilterPeopleAdapter adapter_people;
    private SearchAdapter adapter_multi;

    private static final String TAG = Search.class.getSimpleName();


    // TODO - insert your themoviedb.org API KEY here
    private final static String API_KEY = "53f93d98fdababca6efda85ba6ccc57a";
    List <FilterTv> tvList;
    List <FilterMovie> movieList;
    List <FilterPeople> peopleList;
    List <Search2> searchList;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        tvList = new ArrayList <>();
        movieList = new ArrayList <>();
        peopleList = new ArrayList <>();
        searchList = new ArrayList <>();

////////////////////////////////////////////////////////////////////////////////////////////////////

        final ToggleButton toggle_movie = (ToggleButton) findViewById(R.id.check_movie);

        final ToggleButton toggle_tv = (ToggleButton) findViewById(R.id.check_tv);

        final ToggleButton toggle_people = (ToggleButton) findViewById(R.id.check_paople);


////////////////////////////////////////////////////////////////////////////////////////////////////
        text = findViewById(R.id.search_edittext);
        ImageButton button = findViewById(R.id.search_button);


        recyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false));


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                querySearch = text.getText().toString().trim();
                if (toggle_movie.isChecked()) {

                    getQuerySearch_movie();

                }

                if (toggle_tv.isChecked()) {

                    getQuerySearch_tv();

                }


                if (toggle_people.isChecked()) {

                    getQuerySearch_people();

                }
                else {

                    getQuerySearch();
                }


            }
        });


    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public void getQuerySearch() {

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API" +
                    " KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call <SearchResponse> call = apiService.getSearch(API_KEY, querySearch);
        call.enqueue(new Callback <SearchResponse>() {
            @Override
            public void onResponse(Call <SearchResponse> call, Response <SearchResponse> response) {
                int statusCode = response.code();
                searchList = response.body().getResults();
                adapter_multi = new SearchAdapter(searchList, R.layout.list_item_search, getApplicationContext());
                recyclerView.setAdapter(adapter_multi);
                adapter_multi.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call <SearchResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void getQuerySearch_tv() {


        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your " +
                    "API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call <FilterTvResponse> call = apiService.getSearchTv(API_KEY, querySearch);
        call.enqueue(new Callback <FilterTvResponse>() {
            @Override
            public void onResponse(Call <FilterTvResponse> call, Response <FilterTvResponse> response) {
                int statusCode = response.code();
                tvList = response.body().getResults();
                adapter_tv = new FilterTvAdapter(tvList, R.layout.list_item_filtertv, getApplicationContext());
                recyclerView.setAdapter(adapter_tv);
                adapter_tv.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call <FilterTvResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }


        });

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void getQuerySearch_movie() {

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call <FilterMovieResponse> call = apiService.getSearchMovie(API_KEY, querySearch);
        call.enqueue(new Callback <FilterMovieResponse>() {
            @Override
            public void onResponse(Call <FilterMovieResponse> call, Response <FilterMovieResponse> response) {
                int statusCode = response.code();
                movieList = response.body().getResults();
                adapter_movie = new FilterMovieAdapter(movieList, R.layout.list_item_filtermovie, getApplicationContext());
                recyclerView.setAdapter(adapter_movie);
                adapter_movie.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call <FilterMovieResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void getQuerySearch_people() {

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call <FilterPeopleResponse> call = apiService.getSearchPeople(API_KEY, querySearch);
        call.enqueue(new Callback <FilterPeopleResponse>() {
            @Override
            public void onResponse(Call <FilterPeopleResponse> call, Response <FilterPeopleResponse> response) {
                int statusCode = response.code();
                peopleList = response.body().getResults();
                adapter_people = new FilterPeopleAdapter(peopleList, R.layout.list_item_filterpeople, getApplicationContext());
                recyclerView.setAdapter(adapter_people);
                adapter_people.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call <FilterPeopleResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

    }
}
