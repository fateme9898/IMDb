package info.androidhive.retrofit;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import info.androidhive.retrofit.activity.MainActivity;

public class FirstPage extends AppCompatActivity {
    public ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        progress=(ProgressBar)findViewById(R.id.progressBar);
        progress.setVisibility(View.VISIBLE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(FirstPage.this, MainActivity.class);

                startActivity(intent);
            }
        }, 2000);
    }
}
