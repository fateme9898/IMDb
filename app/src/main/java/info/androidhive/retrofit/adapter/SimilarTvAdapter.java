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
import info.androidhive.retrofit.activity.MainActivity;
import info.androidhive.retrofit.activity.TvDetail;
import info.androidhive.retrofit.db.FavoriteList;
import info.androidhive.retrofit.model.Movie.Movie;
import info.androidhive.retrofit.model.Tv.Tv;

public class SimilarTvAdapter extends RecyclerView.Adapter <SimilarTvAdapter.ViewHolder> {

    public static List <Tv> tvs;
    private int rowLayout;
    private Context context;
    private String imageurl = "https://image.tmdb.org/t/p/original";
  private ViewHolder.ItemClickListener itemClickListener;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        TextView tvTitle;
        ImageView image ,fav_btn;
        TextView rating;


        public ViewHolder(View v) {
            super(v);
            relativeLayout = v.findViewById(R.id.similar_tv_layout);
            tvTitle = (TextView) v.findViewById(R.id.title_similar_tv);
            image = v.findViewById(R.id.image_similar_tv);
            fav_btn=v.findViewById(R.id.fav_btn);


        }

        public interface ItemClickListener {
            void onClickTv(View view, int position);
        }
    }

    public SimilarTvAdapter(List <Tv> tvs, int list_item_similar_tv, Context context , ViewHolder.ItemClickListener   itemClickListener) {
        this.tvs = tvs;
        this.rowLayout = list_item_similar_tv;
        this.context = context;
        this.itemClickListener=itemClickListener;

    }

    @Override
    public SimilarTvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Tv productList = tvs.get(position);

        holder.tvTitle.setText(tvs.get(position).getName());


        Picasso.with(context)
                .load(imageurl + tvs
                        .get(position)
                        .getPosterPath())
                .fit()

                .into(holder.image);




        if (TvDetail.favoriteDatabase.favoriteDao().isFavorite(productList.getId()) == 1) {
            holder.fav_btn.setImageResource(R.mipmap.tikbookmark);
        }

        else {
            holder.fav_btn.setImageResource(R.mipmap.addbookmark);
            holder.fav_btn.setAlpha(.5f);
        }


        holder.fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoriteList favoriteList = new FavoriteList();

                int id = productList.getId();
                String image = productList.getPosterPath();
                String name = productList.getName();
                String rating = productList.getVoteAverage().toString();

                favoriteList.setId(id);
                favoriteList.setImage(image);
                favoriteList.setName(name);
                favoriteList.setRating(rating);


                if (TvDetail.favoriteDatabase.favoriteDao().isFavorite(id) != 1) {
                    holder.fav_btn.setImageResource(R.mipmap.tikbookmark);
                    TvDetail.favoriteDatabase.favoriteDao().addData(favoriteList);
                    v.setAlpha(1);


                } else {
                    holder.fav_btn.setImageResource(R.mipmap.addbookmark);
                    TvDetail.favoriteDatabase.favoriteDao().delete(favoriteList);
                    v.setAlpha(.5f);
                }


            }
        });

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClickTv(v, holder.getAdapterPosition());
            }
        });



    }

    @Override
    public int getItemCount() {
        return tvs.size();
    }
}