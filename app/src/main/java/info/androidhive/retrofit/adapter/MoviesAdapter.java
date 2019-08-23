package info.androidhive.retrofit.adapter;



import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.activity.MainActivity;
import info.androidhive.retrofit.db.FavoriteList;
import info.androidhive.retrofit.model.Movie.Movie;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
   public List<Movie>product_lists;
   public  Context ct;

    public MoviesAdapter(List <Movie> product_lists, int list_item_movie, Context ct) {
        this.product_lists = product_lists;
        this.ct = ct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_movie,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Movie productList=product_lists.get(i);
        String pimg=productList.getTitle();

        Picasso.with(ct)
                .load("https://image.tmdb.org/t/p/original" + product_lists

                        .get(i)

                        .getPosterPath())
                .into(viewHolder.img)
        ;


        viewHolder.tv.setText(productList.getTitle());
        if (MainActivity.favoriteDatabase.favoriteDao().isFavorite(productList.getId())==1)
            viewHolder.fav_btn.setImageResource(R.drawable.tikbookmark);
        else
            viewHolder.fav_btn.setImageResource(R.drawable.addbookmark);


        viewHolder.fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoriteList favoriteList=new FavoriteList();

                int id=productList.getId();
                String image=productList.getPosterPath();
                String name=productList.getTitle();

                favoriteList.setId(id);
                favoriteList.setImage(image);
                favoriteList.setName(name);

                if (MainActivity.favoriteDatabase.favoriteDao().isFavorite(id)!=1){
                    viewHolder.fav_btn.setImageResource(R.drawable.tikbookmark);
                    MainActivity.favoriteDatabase.favoriteDao().addData(favoriteList);

                }else {
                    viewHolder.fav_btn.setImageResource(R.drawable.addbookmark);
                    MainActivity.favoriteDatabase.favoriteDao().delete(favoriteList);

                }


            }
        });
    }


    @Override
    public int getItemCount() {
        return product_lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img,fav_btn;
        TextView tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.image);
            tv=(TextView)itemView.findViewById(R.id.title);
            fav_btn=(ImageView)itemView.findViewById(R.id.fav_btn);

        }
    }
}
