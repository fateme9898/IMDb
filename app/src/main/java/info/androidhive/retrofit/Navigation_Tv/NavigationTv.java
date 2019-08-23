package info.androidhive.retrofit.Navigation_Tv;

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


public class NavigationTv extends AppCompatActivity  {


    FragmentManager fm;
    NavigationTvTop navigationTvTop;
    NavigationTvPop navigationTvPop;
    NavigationTvAir navigationTvAir;
    BottomNavigationView bottomNavigationView;
    Fragment active;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_tv);

        bottomNavigationView=findViewById(R.id.navigation_tv);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;

                switch (menuItem.getItemId()) {
                    case R.id.navigation_tv_pop:
                        fm.beginTransaction().hide(active).show(navigationTvPop).commit();
                        active=navigationTvPop;
                        return true;

                    case R.id.navigation_tv_top:
                        fm.beginTransaction().hide(active).show(navigationTvTop).commit();
                        active=navigationTvTop;
                        return true;

                    case  R.id.navigation_tv_air:
                        fm.beginTransaction().hide(active).show(navigationTvAir).commit();
                        active=navigationTvAir;
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

                Intent intent=new Intent(NavigationTv.this , MainActivity.class);
                startActivity(intent);
            }
        });


        ImageView search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationTv.this, Search.class);
                startActivity(intent);
            }
        });

        ImageView user = findViewById(R.id.user);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationTv.this, Saved.class);
                startActivity(intent);
            }
        });




    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_tv, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }




    public void setFragments(){
        navigationTvTop= new NavigationTvTop();
        navigationTvPop=new NavigationTvPop();
        navigationTvAir=new NavigationTvAir();
        active=navigationTvTop;
        fm=getSupportFragmentManager();

        fm.beginTransaction().add(R.id.frame_container_tv, navigationTvPop ).hide(navigationTvPop).commit();
        fm.beginTransaction().add(R.id.frame_container_tv,navigationTvAir).hide(navigationTvAir).commit();
        fm.beginTransaction().add(R.id.frame_container_tv, navigationTvTop).commit();


    }


}
