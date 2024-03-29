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
import info.androidhive.retrofit.model.search.Search2;

public class SearchAdapter extends RecyclerView.Adapter <SearchAdapter.SearchViewHolder> {

    public static List <Search2> search;
    private int rowLayout;
    private Context context;


    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout searchLayout;
        TextView searchTitle;
        TextView country, text_search_adult;
        ImageView image_search;


        public SearchViewHolder(View v) {
            super(v);
            searchLayout = v.findViewById(R.id.search_layout);
            searchTitle = (TextView) v.findViewById(R.id.text_search);
            country = (TextView) v.findViewById(R.id.text_search_DATA);
            image_search = v.findViewById(R.id.image_search);
            text_search_adult = v.findViewById(R.id.text_search_adult);

        }
    }

    public SearchAdapter(List <Search2> search, int rowLayout, Context context) {
        this.search = search;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new SearchViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SearchViewHolder holder, final int position) {
        holder.searchTitle.setText(search.get(position).getOriginalName());
        holder.country.setText(search.get(position).getFirstAirDate());
        holder.text_search_adult.setText(search.get(position).getOriginalLanguage());
        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/original" + search

                        .get(position)

                        .getPosterPath())
                .fit()
                .into(holder.image_search)
        ;
    }

    @Override
    public int getItemCount() {
        return search.size();
    }
}