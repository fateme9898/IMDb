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
import info.androidhive.retrofit.model.filter_search.FilterPeople;
import info.androidhive.retrofit.model.search.Search2;

public class FilterPeopleAdapter extends RecyclerView.Adapter<FilterPeopleAdapter.FilterPeopleViewHolder> {

    public static List<FilterPeople> filterpeople;
    private int rowLayout;
    private Context context;


    public static class FilterPeopleViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout filterpeopleLayout;
        TextView filterpeopleTitle  ,text_search_DATA;

        ImageView image_filterpeople;




        public FilterPeopleViewHolder(View v) {
            super(v);
            filterpeopleLayout =  v.findViewById(R.id.filterpeople_layout);
            filterpeopleTitle = (TextView) v.findViewById(R.id.text_filterpeople);
            text_search_DATA=v.findViewById(R.id.text_search_DATA);
            image_filterpeople=v.findViewById(R.id.image_filterpeople);

        }
    }

    public FilterPeopleAdapter(List<FilterPeople> search, int rowLayout, Context context) {
        this.filterpeople = search;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public FilterPeopleAdapter.FilterPeopleViewHolder onCreateViewHolder(ViewGroup parent,
                                                                       int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new FilterPeopleViewHolder(view);
    }


    @Override
    public void onBindViewHolder(FilterPeopleViewHolder holder, final int position) {
        holder.filterpeopleTitle.setText(filterpeople.get(position).getName());
        holder.text_search_DATA.setText(filterpeople.get(position).getPopularity().toString());

        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/original" + filterpeople

                        .get(position)

                        .getProfilePath())
                .fit()
                .into(holder.image_filterpeople)
        ;
    }

    @Override
    public int getItemCount() {
        return filterpeople.size();
    }
}