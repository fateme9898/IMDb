package info.androidhive.retrofit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.model.Movie.Movie;
import info.androidhive.retrofit.model.Top_movie.TopMovie;
import info.androidhive.retrofit.model.UpComming.UpComming;

public class TopMovieAdapter extends RecyclerView.Adapter <TopMovieAdapter.TopViewHolder> {

    public static List <TopMovie> topMovies;
    private int rowLayout;
    private Context context;
    private String imageurl = "https://image.tmdb.org/t/p/original";
    private int lastPosition = -1;


    public static class TopViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout topmovielayout;
        TextView movieTitle;
        ImageView image;
        TextView data;
        TextView rating;


        public TopViewHolder(View v) {
            super(v);
            topmovielayout = v.findViewById(R.id.topmovie_layout);
            movieTitle = (TextView) v.findViewById(R.id.text_topmovie);
            image = v.findViewById(R.id.image_topmovie);
            data = (TextView) v.findViewById(R.id.txt_topmovie);
           rating=v.findViewById(R.id.rating);

        }
    }

    public TopMovieAdapter(List <TopMovie> topMovies, int rowLayout, Context context) {
        this.topMovies = topMovies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public TopMovieAdapter.TopViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new TopViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TopViewHolder holder, final int position) {
        holder.movieTitle.setText(topMovies.get(position).getTitle());
        holder.data.setText(topMovies.get(position).getReleaseDate());
      holder.rating.setText(topMovies.get(position).getVoteAverage().toString());

        Picasso.with(context)
                .load(imageurl + topMovies
                        .get(position)
                        .getPosterPath())
                .fit()


                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        return topMovies.size();
    }




}