package info.androidhive.retrofit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.model.filter_search.FilterMovie;
import info.androidhive.retrofit.model.search.Search2;

public class FilterMovieAdapter extends RecyclerView.Adapter <FilterMovieAdapter.FilterMovieViewHolder> {

    private List <FilterMovie> filtermovie;
    private int rowLayout;
    private Context context;


    public static class FilterMovieViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout filtermovieLayout;
        TextView filtermovieTitle, text_search_tv_DATA, text_search_adult;

        ImageView image_filtermovie;


        public FilterMovieViewHolder(View v) {
            super(v);
            filtermovieLayout = v.findViewById(R.id.filtermovie_layout);
            filtermovieTitle = (TextView) v.findViewById(R.id.text_filtermovie);
            text_search_tv_DATA = v.findViewById(R.id.text_search_tv_DATA);
            text_search_adult = v.findViewById(R.id.text_search_adult);
            image_filtermovie = v.findViewById(R.id.image_filtermovie);

        }
    }

    public FilterMovieAdapter(List <FilterMovie> search, int rowLayout, Context context) {
        this.filtermovie = search;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public FilterMovieAdapter.FilterMovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                                       int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new FilterMovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(FilterMovieViewHolder holder, final int position) {
        holder.filtermovieTitle.setText(filtermovie.get(position).getPosterPath());
        holder.text_search_tv_DATA.setText(filtermovie.get(position).getReleaseDate());
        holder.text_search_adult.setText(filtermovie.get(position).getOriginalLanguage());
        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/original" + filtermovie

                        .get(position)

                        .getPosterPath())
                .fit()
                .into(holder.image_filtermovie)
        ;
    }

    @Override
    public int getItemCount() {
        return filtermovie.size();
    }
}