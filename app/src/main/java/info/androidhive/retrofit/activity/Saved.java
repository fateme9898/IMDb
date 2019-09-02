package info.androidhive.retrofit.activity;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.List;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.adapter.FavoriteAdapter;
import info.androidhive.retrofit.adapter.FilterPeopleAdapter;
import info.androidhive.retrofit.another.ItemTouchListener;
import info.androidhive.retrofit.db.FavoriteList;
import info.androidhive.retrofit.model.filter_search.FilterPeople;


public class Saved extends AppCompatActivity {
    private RecyclerView rv;
    private FavoriteAdapter adapter;
    ImageView  back , search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        rv=(RecyclerView)findViewById(R.id.rec);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        getFavData();



        back=findViewById(R.id.back_saved);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Saved.this , MainActivity.class);
                startActivity(intent);
            }
        });
        search=findViewById(R.id.search_saved);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Saved.this , Search.class);
                startActivity(intent);
            }
        });


    }

    private void getFavData() {
        List<FavoriteList>favoriteLists=MainActivity.favoriteDatabase.favoriteDao().getFavoriteData();
        rv.addOnItemTouchListener(new ItemTouchListener(rv) {
            @Override
            public boolean onClick(RecyclerView parent, View view, int position, long id) {
                FavoriteAdapter favoriteAdapter = (FavoriteAdapter) rv.getAdapter();
                FavoriteList favoriteAdapter1 = FavoriteAdapter.favoriteLists.get(position);

                    Intent intent = new Intent(Saved.this, MovieDetail.class);

                intent.putExtra("TYPE", favoriteAdapter1.getId());
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


        adapter=new FavoriteAdapter(favoriteLists,getApplicationContext());
        rv.setAdapter(adapter);
    }
}
