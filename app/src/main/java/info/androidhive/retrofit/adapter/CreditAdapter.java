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
import info.androidhive.retrofit.model.Credit_people.Cast;
import info.androidhive.retrofit.model.Credit_people.Crew;
import info.androidhive.retrofit.model.Credit_people.Example;
import info.androidhive.retrofit.model.Movie.Movie;

public class CreditAdapter extends RecyclerView.Adapter <CreditAdapter.CreditViewHolder> {

    public static List <Cast> movies;
    private int rowLayout;
    private Context context;
    private String imageurl = "https://image.tmdb.org/t/p/original";


    public static class CreditViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout tvLayout;
        TextView movieTitle;
        ImageView image;
        TextView rating;


        public CreditViewHolder(View v) {
            super(v);
            tvLayout = v.findViewById(R.id.credit_layout);
            movieTitle = (TextView) v.findViewById(R.id.title_credit);
            image = v.findViewById(R.id.image_credit);


        }
    }

    public CreditAdapter(List <Cast> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public CreditAdapter.CreditViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new CreditViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CreditViewHolder holder, final int position) {
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