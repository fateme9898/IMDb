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

public class TopTvAdapter extends RecyclerView.Adapter <TopTvAdapter.TopViewHolder> {

    public static List <Tv> topTv;
    private int rowLayout;
    private Context context;
    private String imageurl = "https://image.tmdb.org/t/p/original";


    public static class TopViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout toptvlayout;
        TextView tvTitle;
        ImageView image2 , image;
        TextView air , rating;


        public TopViewHolder(View v) {
            super(v);
            toptvlayout = v.findViewById(R.id.toptv_layout);
          tvTitle = (TextView) v.findViewById(R.id.text_toptv);
            image2= v.findViewById(R.id.image_toptv2);
            image=v.findViewById(R.id.image_toptv);
            image.setColorFilter(Color.argb(150,0,0,0));
            air = (TextView) v.findViewById(R.id.txt_toptv);
            rating=v.findViewById(R.id.rating);

        }
    }

    public TopTvAdapter(List <Tv> toptv, int rowLayout, Context context) {
        this.topTv = toptv;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public TopTvAdapter.TopViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new TopViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TopViewHolder holder, final int position) {
        holder.tvTitle.setText(topTv.get(position).getName());
        holder.air.setText( topTv.get(position).getFirstAirDate());
        holder.rating.setText(topTv.get(position).getVoteAverage().toString());

        Picasso.with(context)
                .load(imageurl + topTv
                        .get(position)
                        .getPosterPath())
                .fit()

                .into(holder.image2);

        Picasso.with(context)
                .load(imageurl + topTv

                        .get(position)
                        .getBackdropPath())
                .transform(new BlurTransformation(holder.image.getContext(),25,1))
                .fit()


                .into(holder.image);



    }

    @Override
    public int getItemCount() {
        return topTv.size();
    }
}