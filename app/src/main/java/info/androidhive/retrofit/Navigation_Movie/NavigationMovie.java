package info.androidhive.retrofit.Navigation_Movie;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.activity.Saved;
import info.androidhive.retrofit.activity.MainActivity;
import info.androidhive.retrofit.activity.Search;


public class NavigationMovie extends AppCompatActivity  {


    FragmentManager fm;
    NavigationMovieTop navigationMovieTop;
    NavigationMoviePop navigationMoviePop;
    NavigationMovieUp navigationMovieUp;
    BottomNavigationView bottomNavigationView;
    Fragment active;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_movie);

 bottomNavigationView=findViewById(R.id.navigation);
 bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
     @Override
     public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
         Fragment fragment;

         switch (menuItem.getItemId()) {
             case R.id.navigation_pop:
                 fm.beginTransaction().hide(active).show(navigationMoviePop).commit();
                 active=navigationMoviePop;
                 return true;

             case R.id.navigation_top:
                 fm.beginTransaction().hide(active).show(navigationMovieTop).commit();
                 active=navigationMovieTop;
                 return true;

             case  R.id.navigation_upcoming:
                 fm.beginTransaction().hide(active).show(navigationMovieUp).commit();
                 active=navigationMovieUp;
                 return  true;


         }

         return false;
     }


 });
 setFragments();


        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(NavigationMovie.this , MainActivity.class);
                startActivity(intent);
            }
        });


        ImageView user = findViewById(R.id.user);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationMovie.this, Saved.class);
                startActivity(intent);
            }
        });

        ImageView search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationMovie.this, Search.class);
                startActivity(intent);
            }
        });




    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }




    public void setFragments(){
       navigationMovieTop= new NavigationMovieTop();
        navigationMoviePop=new NavigationMoviePop();
       navigationMovieUp=new NavigationMovieUp();
       active=navigationMovieTop;
       fm=getSupportFragmentManager();

        fm.beginTransaction().add(R.id.frame_container, navigationMoviePop ).hide(navigationMoviePop).commit();
        fm.beginTransaction().add(R.id.frame_container,navigationMovieUp).hide(navigationMovieUp).commit();
        fm.beginTransaction().add(R.id.frame_container, navigationMovieTop).commit();

    }


}