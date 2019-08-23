package info.androidhive.retrofit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.model.Movie.Movie;

public class SimilarMovieAdapter extends RecyclerView.Adapter <SimilarMovieAdapter.MovieViewHolder> {

    public List <Movie> movies;
    private int rowLayout;
    private Context context;
    private String imageurl = "https://image.tmdb.org/t/p/original";


    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout tvLayout;
        TextView movieTitle;
        ImageView image;
        TextView rating;


        public MovieViewHolder(View v) {
            super(v);
            tvLayout = v.findViewById(R.id.similar_movie_layout);
            movieTitle = (TextView) v.findViewById(R.id.title_similar_movie);
            image = v.findViewById(R.id.image_similar_movie);


        }
    }

    public SimilarMovieAdapter(List <Movie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public SimilarMovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        holder.movieTitle.setText(movies.get(position).getTitle());


        Picasso.with(context)
                .load(imageurl + movies
                        .get(position)
                        .getPosterPath())
                .fit()

                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}