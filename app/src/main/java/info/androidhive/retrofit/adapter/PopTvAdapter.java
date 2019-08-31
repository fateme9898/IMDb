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

public class PopTvAdapter extends RecyclerView.Adapter <PopTvAdapter.PopViewHolder> {

    public static List <Tv> popTv;
    private int rowLayout;
    private Context context;
    private String imageurl = "https://image.tmdb.org/t/p/original";


    public static class PopViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout poptvlayout;
        TextView tvTitle;
        ImageView image , image2;
        TextView air , rating;


        public PopViewHolder(View v) {
            super(v);
            poptvlayout = v.findViewById(R.id.poptv_layout);
           tvTitle = (TextView) v.findViewById(R.id.text_poptv);
            image = v.findViewById(R.id.image_poptv);
            image2=v.findViewById(R.id.image_poptv2);
            image2.setColorFilter(Color.argb(150,0,0,0));
            air = (TextView) v.findViewById(R.id.txt_poptv);
            rating=v.findViewById(R.id.rating);

        }
    }

    public PopTvAdapter(List <Tv> popMovies, int rowLayout, Context context) {
        this.popTv = popMovies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public PopTvAdapter.PopViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new PopViewHolder(view);
    }


    @Override
    public void onBindViewHolder(PopViewHolder holder, final int position) {
        holder.tvTitle.setText(popTv.get(position).getName());
        holder.air.setText(popTv.get(position).getFirstAirDate());
        holder.rating.setText(popTv.get(position).getVoteAverage().toString());

        Picasso.with(context)
                .load(imageurl + popTv
                        .get(position)
                        .getPosterPath())
                .fit()

                .into(holder.image);


        Picasso.with(context)
                .load(imageurl + popTv

                        .get(position)
                        .getBackdropPath())
                .transform(new BlurTransformation(holder.image2.getContext(),25,1))
                .fit()


                .into(holder.image2);



    }

    @Override
    public int getItemCount() {
        return popTv.size();
    }
}