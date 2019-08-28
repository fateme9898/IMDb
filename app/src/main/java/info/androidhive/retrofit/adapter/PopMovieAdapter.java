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
import info.androidhive.retrofit.model.Pop_movie.PopMovie;

public class PopMovieAdapter extends RecyclerView.Adapter <PopMovieAdapter.PopViewHolder> {

    public static List <PopMovie> popMovies;
    private int rowLayout;
    private Context context;
    private String imageurl = "https://image.tmdb.org/t/p/original";


    public static class PopViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout popmovielayout;
        TextView movieTitle;
        ImageView image;
        TextView data , rating;


        public PopViewHolder(View v) {
            super(v);
            popmovielayout = v.findViewById(R.id.popmovie_layout);
            movieTitle = (TextView) v.findViewById(R.id.text_popmovie);
            image = v.findViewById(R.id.image_popmovie);
            data = (TextView) v.findViewById(R.id.txt_popmovie);
            rating=v.findViewById(R.id.rating_pop_movie);

        }
    }

    public PopMovieAdapter(List <PopMovie> popMovies, int rowLayout, Context context) {
        this.popMovies = popMovies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public PopMovieAdapter.PopViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new PopViewHolder(view);
    }


    @Override
    public void onBindViewHolder(PopViewHolder holder, final int position) {
        holder.movieTitle.setText(popMovies.get(position).getTitle());
        holder.data.setText(popMovies.get(position).getReleaseDate());
        holder.rating.setText( popMovies.get(position).getOriginalLanguage());

        Picasso.with(context)
                .load(imageurl + popMovies
                        .get(position)
                        .getPosterPath())
                .fit()

                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        return popMovies.size();
    }
}