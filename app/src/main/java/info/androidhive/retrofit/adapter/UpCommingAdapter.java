package info.androidhive.retrofit.adapter;

import android.content.Context;
import android.graphics.Color;
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
import info.androidhive.retrofit.model.UpComming.UpComming;
import jp.wasabeef.picasso.transformations.BlurTransformation;

public class UpCommingAdapter extends RecyclerView.Adapter <UpCommingAdapter.UpViewHolder> {

    public List <UpComming> upCommings;
    private int rowLayout;
    private Context context;
    private String imageurl = "https://image.tmdb.org/t/p/original";


    public static class UpViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout uplayout;
        TextView movieTitle;
        ImageView image ,image2;
        TextView data, rating;


        public UpViewHolder(View v) {
            super(v);
            uplayout = v.findViewById(R.id.upcomming_layout);
            movieTitle = (TextView) v.findViewById(R.id.text_upmovie);
            image2 = v.findViewById(R.id.image_upmovie2);
            image=v.findViewById(R.id.image_upmovie);
            image.setColorFilter(Color.argb(150,0,0,0));
            data = (TextView) v.findViewById(R.id.txt_upmovie);
            rating = v.findViewById(R.id.rating);
        }
    }

    public UpCommingAdapter(List <UpComming> upCommings, int rowLayout, Context context) {
        this.upCommings = upCommings;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public UpCommingAdapter.UpViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new UpViewHolder(view);
    }


    @Override
    public void onBindViewHolder(UpViewHolder holder, final int position) {
        holder.movieTitle.setText(upCommings.get(position).getTitle());
        holder.data.setText(upCommings.get(position).getReleaseDate());
        holder.rating.setText(upCommings.get(position).getVoteAverage().toString());

        Picasso.with(context)
                .load(imageurl + upCommings
                        .get(position)
                        .getPosterPath())
                .fit()

                .into(holder.image2);

        Picasso.with(context)
                .load(imageurl + upCommings

                        .get(position)
                        .getBackdropPath())
                .transform(new BlurTransformation(holder.image.getContext(),25,1))
                .fit()


                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        return upCommings.size();
    }
}