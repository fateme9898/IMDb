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
import info.androidhive.retrofit.model.Top_movie.TopMovie;
import info.androidhive.retrofit.model.Tv.Tv;

public class AirTvAdapter extends RecyclerView.Adapter <AirTvAdapter.TopViewHolder> {

    public static List <Tv> airTv;
    private int rowLayout;
    private Context context;
    private String imageurl = "https://image.tmdb.org/t/p/original";


    public static class TopViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout airlayout;
        TextView tvTitle;
        ImageView image;
        TextView rating;


        public TopViewHolder(View v) {
            super(v);
            airlayout = v.findViewById(R.id.air_layout);
            tvTitle = (TextView) v.findViewById(R.id.text_air);
            image = v.findViewById(R.id.image_air);
            rating = (TextView) v.findViewById(R.id.txt_air);

        }
    }

    public AirTvAdapter(List <Tv> airTv, int rowLayout, Context context) {
        this.airTv = airTv;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public AirTvAdapter.TopViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new TopViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TopViewHolder holder, final int position) {
        holder.tvTitle.setText(airTv.get(position).getName());
        holder.rating.setText(airTv.get(position).getFirstAirDate());

        Picasso.with(context)
                .load(imageurl + airTv
                        .get(position)
                        .getPosterPath())
                .fit()

                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        return airTv.size();
    }
}