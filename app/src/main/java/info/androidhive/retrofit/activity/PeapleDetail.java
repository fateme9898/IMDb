package info.androidhive.retrofit.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.github.chuross.library.ExpandableLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.adapter.CreditAdapter;
import info.androidhive.retrofit.another.ItemTouchListener;
import info.androidhive.retrofit.model.Credit_people.Cast;
import info.androidhive.retrofit.model.Credit_people.Example;
import info.androidhive.retrofit.model.people.KnownFor;
import info.androidhive.retrofit.model.people.Peaple;
import info.androidhive.retrofit.model.people.PeopleDetailModel;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeapleDetail extends AppCompatActivity {
    private final static String API_KEY = "53f93d98fdababca6efda85ba6ccc57a";
    private int querytrailer;
    TextView overview_info_peopledetail;
    TextView text_info_peopledetail;
    ImageView image_info_peopledetail;
    TextView birth_info_peopledetail;
    TextView place_info_peopledetail;

    private static final String TAG = Search.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peaple_detail);


        Intent getId=getIntent();
        Bundle bundle=getId.getExtras();
        querytrailer=bundle.getInt("TYPE");
        getQueryInformation();
        image_info_peopledetail=findViewById(R.id.image_info_peopledetail);
        getQueryInformation2();
        text_info_peopledetail=findViewById(R.id.text_info_peopledetail);


        getQueryInformation3();
        overview_info_peopledetail=findViewById(R.id.overview_info_peopledetail);
        birth_info_peopledetail=findViewById(R.id.birth_info_peopledetail);
        place_info_peopledetail=findViewById(R.id.place_info_peopledetail);

        getQueryInformationCrew();
        RelativeLayout b=findViewById(R.id.button);

        TextView seemore=findViewById(R.id.seemore);

        seemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpandableLayout ex=findViewById(R.id.expandableLayout);
                ex.expand();
                ex.collapse();

            }
        });





        ImageView back=findViewById(R.id.back2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(PeapleDetail.this , MainActivity.class);
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

        Call<Peaple> call=apiService.getPeopleDetail(querytrailer);
        call.enqueue(new Callback<Peaple>() {
            @Override
            public void onResponse(Call <Peaple> call, Response<Peaple> response) {

                int statusCode = response.code();
                Peaple movie = response.body();
                bindView(movie);

            }

            @Override
            public void onFailure(Call <Peaple> call, Throwable t) {

            }
        });

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public  void bindView(Peaple movie ){
      //  text_info_moviedetail.setText(movie.getOverview());
      //  title_info_moviedetail.setText(movie.getTitle());
        Picasso.with(getBaseContext()).load("https://image.tmdb.org/t/p/original"+movie.getProfilePath()).fit().into(image_info_peopledetail);
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////
    public void getQueryInformation2() {

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<KnownFor> call=apiService.getPeopleDetail2(querytrailer);
        call.enqueue(new Callback<KnownFor>() {
            @Override
            public void onResponse(Call <KnownFor> call, Response<KnownFor> response) {

                int statusCode = response.code();
                KnownFor movie = response.body();
                bindView2(movie);

            }

            @Override
            public void onFailure(Call <KnownFor> call, Throwable t) {

            }
        });

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public  void bindView2(KnownFor movie ){
          text_info_peopledetail.setText(movie.getName());


    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    public void getQueryInformation3() {

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<PeopleDetailModel> call=apiService.getPeopleDetail3(querytrailer);
        call.enqueue(new Callback<PeopleDetailModel>() {
            @Override
            public void onResponse(Call <PeopleDetailModel> call, Response<PeopleDetailModel> response) {

                int statusCode = response.code();
                PeopleDetailModel movie = response.body();
                bindView3(movie);

            }

            @Override
            public void onFailure(Call <PeopleDetailModel> call, Throwable t) {

            }
        });

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public  void bindView3(PeopleDetailModel movie ){
        overview_info_peopledetail.setText(movie.getBiography());
birth_info_peopledetail.setText((CharSequence) movie.getBirthday());
place_info_peopledetail.setText(movie.getPlaceOfBirth());

    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////

    public void getQueryInformationCrew() {

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from" +
                    " themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }

        final RecyclerView recyclerView3 = (RecyclerView) findViewById(R.id.recyclerview_credit);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));

        recyclerView3.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false));


        recyclerView3.addOnItemTouchListener(new ItemTouchListener(recyclerView3) {
            @Override
            public boolean onClick(RecyclerView parent, View view, int position, long id) {
                CreditAdapter creditAdapter = (CreditAdapter) recyclerView3.getAdapter();
                Cast creditAdapter1 = CreditAdapter.movies.get(position);
                Intent intent = new Intent(PeapleDetail.this, MovieDetail.class);

                intent.putExtra("TYPE", creditAdapter1.getId());
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
        Call <Example> call3 = apiService.getCredit(querytrailer);
        call3.enqueue(new Callback <Example>() {
            @Override
            public void onResponse(Call <Example> call, Response <Example> response) {
                int statusCode = response.code();
                List<Cast> peaples = response.body().getCast();
                recyclerView3.setAdapter(new CreditAdapter(peaples, R.layout.list_item_credit,
                        getApplicationContext()));


            }

            @Override
            public void onFailure(Call <Example> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////


}
