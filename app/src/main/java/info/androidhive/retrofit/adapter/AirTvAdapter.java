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
import info.androidhive.retrofit.model.Tv.Tv;
import jp.wasabeef.picasso.transformations.BlurTransformation;

public class AirTvAdapter extends RecyclerView.Adapter <AirTvAdapter.TopViewHolder> {

    public static List <Tv> airTv;
    private int rowLayout;
    private Context context;
    private String imageurl = "https://image.tmdb.org/t/p/original";


    public static class TopViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout airlayout;
        TextView tvTitle;
        ImageView image ,image2;
        TextView air , rating;


        public TopViewHolder(View v) {
            super(v);
            airlayout = v.findViewById(R.id.air_layout);
            tvTitle = (TextView) v.findViewById(R.id.text_air);
            image = v.findViewById(R.id.image_air);
            image2=v.findViewById(R.id.image_air2);
            image2.setColorFilter(Color.argb(150,0,0,0));
            air = (TextView) v.findViewById(R.id.txt_air);
            rating=v.findViewById(R.id.rating);

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
        holder.air.setText(airTv.get(position).getFirstAirDate());
        holder.rating.setText(airTv.get(position).getVoteAverage().toString());

        Picasso.with(context)
                .load(imageurl + airTv
                        .get(position)
                        .getPosterPath())
                .fit()

                .into(holder.image);


        Picasso.with(context)
                .load(imageurl + airTv

                        .get(position)
                        .getBackdropPath())
                .transform(new BlurTransformation(holder.image2.getContext(),25,1))
                .fit()


                .into(holder.image2);


    }

    @Override
    public int getItemCount() {
        return airTv.size();
    }
}