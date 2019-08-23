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
import info.androidhive.retrofit.model.Tv.Tv;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {

   public static List<Tv> tv;
    private int rowLayout;
    private Context context;


    public static class TvViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout moviesLayout;
        TextView tvTitle;
        TextView tvTitle2;
        TextView tvTitle3;
        ImageView image2;
        TextView rating2;




        public  TvViewHolder(View v) {
            super(v);
            moviesLayout =  v.findViewById(R.id.tv_layout);
            tvTitle = (TextView) v.findViewById(R.id.title2);
            tvTitle2 = (TextView) v.findViewById(R.id.title_tv2);
         //   tvTitle3 = (TextView) v.findViewById(R.id.title_tv3);
            image2=v.findViewById(R.id.image2);
            rating2=v.findViewById(R.id.rating_tv);

        }
    }

    public TvAdapter(List<Tv> tv, int rowLayout, Context context) {
        this.tv = tv;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public TvAdapter.TvViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new TvViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TvViewHolder holder, final int position) {
        holder.tvTitle.setText(tv.get(position).getName());
        holder.tvTitle2.setText(tv.get(position).getOverview());
      //  holder.tvTitle3.setText(tv.get(position).getFirstAirDate());
        holder.rating2.setText(tv.get(position).getVoteAverage().toString());
        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/original" + tv

                        .get(position)

                        .getPosterPath())
                .into(holder.image2)
        ;
    }

    @Override
    public int getItemCount() {
        return tv.size();
    }
}