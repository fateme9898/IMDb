package info.androidhive.retrofit.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

import info.androidhive.retrofit.R;

public class FirstPage extends AppCompatActivity {
    public ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
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
