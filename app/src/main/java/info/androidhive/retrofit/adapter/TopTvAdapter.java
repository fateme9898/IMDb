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

public class TopTvAdapter extends RecyclerView.Adapter <TopTvAdapter.TopViewHolder> {

    public static List <Tv> topTv;
    private int rowLayout;
    private Context context;
    private String imageurl = "https://image.tmdb.org/t/p/original";


    public static class TopViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout toptvlayout;
        TextView tvTitle;
        ImageView image;
        TextView rating;


        public TopViewHolder(View v) {
            super(v);
            toptvlayout = v.findViewById(R.id.toptv_layout);
          tvTitle = (TextView) v.findViewById(R.id.text_toptv);
            image = v.findViewById(R.id.image_toptv);
            rating = (TextView) v.findViewById(R.id.txt_toptv);

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
        holder.rating.setText( topTv.get(position).getFirstAirDate());

        Picasso.with(context)
                .load(imageurl + topTv
                        .get(position)
                        .getPosterPath())
                .fit()

                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        return topTv.size();
    }
}