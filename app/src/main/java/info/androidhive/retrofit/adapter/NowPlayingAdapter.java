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
import info.androidhive.retrofit.model.NowPlaying.NowPlaying;

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.TrailerViewHolder> {

    public List<NowPlaying> trailer;
    private int rowLayout;
    private Context context;


    public static class TrailerViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout trailerLayout;
        TextView trailerTitle;
        ImageView trailerimage;
        ImageView trailerimage2;




        public TrailerViewHolder(View v) {
            super(v);
            trailerLayout =  v.findViewById(R.id.trailer_layout);
            trailerTitle = (TextView) v.findViewById(R.id.text_trailer);
            trailerimage=v.findViewById(R.id.image_trailer);
            trailerimage2=v.findViewById(R.id.back_image_trailer);


        }
    }

    public NowPlayingAdapter(List<NowPlaying> trailer, int rowLayout, Context context) {
        this.trailer = trailer;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public NowPlayingAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new TrailerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TrailerViewHolder holder, final int position) {
        holder.trailerTitle.setText(trailer.get(position).getTitle());


        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/original" + trailer
                        .get(position)
                        .getPosterPath())
                .fit()
                .into(holder.trailerimage);

        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/original" + trailer
                        .get(position)
                        .getBackdropPath())
                .fit()
                .into(holder.trailerimage2);


    }

    @Override
    public int getItemCount() {
        return trailer.size();
    }
}