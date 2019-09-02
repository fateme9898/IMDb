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
import info.androidhive.retrofit.model.filter_search.FilterTv;
import info.androidhive.retrofit.model.search.Search2;

public class FilterTvAdapter extends RecyclerView.Adapter<FilterTvAdapter.FilterTvViewHolder> {

   public static List<FilterTv> filtertv;
    private int rowLayout;
    private Context context;


    public static class FilterTvViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout filtertvLayout;
        TextView filtertvTitle , text_search_tv_DATA , text_search_adult;

        ImageView image_filtertv;




        public FilterTvViewHolder(View v) {
            super(v);
            filtertvLayout =  v.findViewById(R.id.filtertv_layout);
            filtertvTitle = (TextView) v.findViewById(R.id.text_filtertv);
            text_search_tv_DATA=v.findViewById(R.id.text_search_tv_DATA);
            text_search_adult=v.findViewById(R.id.text_search_adult);
            image_filtertv=v.findViewById(R.id.image_filtertv);

        }
    }

    public FilterTvAdapter(List<FilterTv> search, int rowLayout, Context context) {
        this.filtertv = search;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public FilterTvAdapter.FilterTvViewHolder onCreateViewHolder(ViewGroup parent,
                                                                       int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new FilterTvViewHolder(view);
    }


    @Override
    public void onBindViewHolder(FilterTvViewHolder holder, final int position) {
        holder.filtertvTitle.setText(filtertv.get(position).getName());
        holder.text_search_tv_DATA.setText(filtertv.get(position).getFirstAirDate());
        holder.text_search_adult.setText(filtertv.get(position).getOriginalLanguage());

        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/original" + filtertv

                        .get(position)

                        .getPosterPath())
                .fit()
                .into(holder.image_filtertv)
        ;
    }

    @Override
    public int getItemCount() {
        return filtertv.size();
    }
}