package info.androidhive.retrofit.activity;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import java.util.List;

import info.androidhive.retrofit.Navigation_Movie.TopMovieDetail;
import info.androidhive.retrofit.R;
import info.androidhive.retrofit.adapter.FavoriteAdapter;
import info.androidhive.retrofit.adapter.FilterPeopleAdapter;
import info.androidhive.retrofit.adapter.MoviesAdapter;
import info.androidhive.retrofit.another.ItemTouchListener;
import info.androidhive.retrofit.db.FavoriteDatabase;
import info.androidhive.retrofit.db.FavoriteList;
import info.androidhive.retrofit.model.filter_search.FilterPeople;


public class Saved extends AppCompatActivity implements FavoriteAdapter.ViewHolder.ItemClickListener {
    private RecyclerView rv;

    public static FavoriteDatabase favoriteDatabase;
    ImageView back, search;
    FavoriteAdapter favoriteAdapter;
    public List <FavoriteList> favoriteLists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        favoriteLists = new ArrayList <>();

        getFavData();

        favoriteDatabase = Room.databaseBuilder(getApplicationContext(), FavoriteDatabase.class, "myfavdb").allowMainThreadQueries().build();

        back = findViewById(R.id.back_saved);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Saved.this, MainActivity.class);
                startActivity(intent);
            }
        });
        search = findViewById(R.id.search_saved);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Saved.this, Search.class);
                startActivity(intent);
            }
        });


    }


    private void getFavData() {

        rv = (RecyclerView) findViewById(R.id.rec);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        favoriteLists = MainActivity.favoriteDatabase.favoriteDao().getFavoriteData();

        favoriteAdapter = new FavoriteAdapter(favoriteLists, R.layout.favorites_list, this, this);
        rv.setAdapter(favoriteAdapter);

    }

    @Override
    public void onClick(int position) {
        final FavoriteList favoriteList = favoriteLists.get(position);
        Intent intent = new Intent(this, TopMovieDetail.class);
        intent.putExtra("TYPE", favoriteList.getId());
        startActivity(intent);
    }
}
