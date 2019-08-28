package info.androidhive.retrofit.adapter;


import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.gestures.GestureDetector;
import com.squareup.picasso.Picasso;

import java.util.List;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.activity.MainActivity;
import info.androidhive.retrofit.db.FavoriteList;
import info.androidhive.retrofit.model.Movie.Movie;
import info.androidhive.retrofit.model.Tv.Tv;

public class TvAdapter extends RecyclerView.Adapter <TvAdapter.ViewHolder> {
    public List <Tv> product_lists;
    public Context ct;
    private ViewHolder.ItemClickListenerTv itemClickListener;
//    private static ItemClickListener clickListener;

    public TvAdapter(List <Tv> product_lists, int list_item_tv, Context ct, MainActivity itemClickListener) {
        this.product_lists = product_lists;
        this.ct = ct;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_tv, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Tv productList = product_lists.get(i);


        Picasso.with(ct)
                .load("https://image.tmdb.org/t/p/original" + product_lists

                        .get(i)

                        .getPosterPath())
                .into(viewHolder.img)
        ;

        viewHolder.title_tv2.setText(productList.getOverview());
        viewHolder.tv.setText(productList.getName());
       viewHolder.rating.setText(productList.getVoteAverage().toString());
        if (MainActivity.favoriteDatabase.favoriteDao().isFavorite(productList.getId()) == 1)
            viewHolder.fav_btn.setImageResource(R.drawable.tikbookmark);
        else
            viewHolder.fav_btn.setImageResource(R.drawable.addbookmark);


        viewHolder.fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoriteList favoriteList = new FavoriteList();

                int id = productList.getId();
                String image = productList.getPosterPath();
                String name = productList.getName();

                favoriteList.setId(id);
                favoriteList.setImage(image);
                favoriteList.setName(name);

                if (MainActivity.favoriteDatabase.favoriteDao().isFavorite(id) != 1) {
                    viewHolder.fav_btn.setImageResource(R.drawable.tikbookmark);
                    MainActivity.favoriteDatabase.favoriteDao().addData(favoriteList);

                } else {
                    viewHolder.fav_btn.setImageResource(R.drawable.addbookmark);
                    MainActivity.favoriteDatabase.favoriteDao().delete(favoriteList);

                }


            }
        });

        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClickTv(v, viewHolder.getAdapterPosition());
            }
        });
    }


    @Override
    public int getItemCount() {
        return product_lists.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img, fav_btn;
        TextView tv, rating, title_tv2;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.image2);
            tv = (TextView) itemView.findViewById(R.id.title2);
            title_tv2 = itemView.findViewById(R.id.title_tv2);
            fav_btn = (ImageView) itemView.findViewById(R.id.fav_btn);
            rating = itemView.findViewById(R.id.rating_tv);
            relativeLayout = itemView.findViewById(R.id.tv_layout);

        }

        public interface ItemClickListenerTv {
            void onClickTv(View view, int position);
        }

    }
}