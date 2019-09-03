package info.androidhive.retrofit.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.activity.MainActivity;
import info.androidhive.retrofit.activity.Saved;
import info.androidhive.retrofit.db.FavoriteDatabase;
import info.androidhive.retrofit.db.FavoriteList;

public class FavoriteAdapter extends RecyclerView.Adapter <FavoriteAdapter.ViewHolder> {
    public static List <FavoriteList> favoriteLists;
    Context context;
    public ViewHolder.ItemClickListener itemClickListener;

    public FavoriteAdapter(List <FavoriteList> favoriteLists, int favorites_list, Context context ,  ViewHolder.ItemClickListener itemClickListener) {
        this.favoriteLists = favoriteLists;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favorites_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final FavoriteList fl = favoriteLists.get(i);
        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/original" + favoriteLists

                        .get(i)

                        .getImage())
                .fit()
                .into(viewHolder.img)

        ;
        final int id = fl.getId();
        viewHolder.tv.setText(fl.getName());
        viewHolder.rating.setText(fl.getRating());
        viewHolder.fav_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Saved.favoriteDatabase.favoriteDao().deleteByItemId(id);
                favoriteLists.remove(fl);
                notifyDataSetChanged();
                v.setAlpha(1);


            }
        });
        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    itemClickListener.onClick(viewHolder.getAdapterPosition());

            }
        });

    }

    @Override
    public int getItemCount() {
        return favoriteLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img, fav_btn_back;
        TextView tv, rating, data;
                LinearLayout relativeLayout;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.fimg_pr);
            tv = (TextView) itemView.findViewById(R.id.ftv_name);
            rating = itemView.findViewById(R.id.rating);
            fav_btn_back=itemView.findViewById(R.id.fav_btn_back);
            relativeLayout=itemView.findViewById(R.id.favorit_layout);
//        data=itemView.findViewById(R.id.data);
        }

        public interface ItemClickListener {
            void onClick(int position);
        }
    }

}
