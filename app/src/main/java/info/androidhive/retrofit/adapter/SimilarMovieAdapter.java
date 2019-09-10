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
import info.androidhive.retrofit.activity.MovieDetail;
import info.androidhive.retrofit.db.FavoriteList;
import info.androidhive.retrofit.model.Movie.Movie;

public class SimilarMovieAdapter extends RecyclerView.Adapter <SimilarMovieAdapter.ViewHolder> {

    public static List <Movie> movies;
    private int rowLayout;
    private Context context;
    private String imageurl = "https://image.tmdb.org/t/p/original";
    private ViewHolder.ItemClickListener itemClickListener;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        TextView movieTitle;
        ImageView image , fav_btn;
        TextView rating;


        public ViewHolder(View v) {
            super(v);
            relativeLayout = v.findViewById(R.id.similar_movie_layout);
            movieTitle = (TextView) v.findViewById(R.id.title_similar_movie);
            image = v.findViewById(R.id.image_similar_movie);
            fav_btn=v.findViewById(R.id.fav_btn);


        }

        public interface ItemClickListener {
            void onClick(View view, int position);
        }
    }

    public SimilarMovieAdapter(List <Movie> movies, int list_item_similar_movie, Context context ,ViewHolder.ItemClickListener itemClickListener) {
        this.movies = movies;
        this.rowLayout = list_item_similar_movie;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Movie productList = movies.get(position);
        holder.movieTitle.setText(movies.get(position).getOriginalTitle());


        Picasso.with(context)
                .load(imageurl + movies
                        .get(position)
                        .getPosterPath())
                .fit()

                .into(holder.image);


        if (MovieDetail.favoriteDatabase.favoriteDao().isFavorite(productList.getId()) == 1) {
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
                String name = productList.getTitle();
                String rating = productList.getVoteAverage().toString();


                favoriteList.setId(id);
                favoriteList.setImage(image);
                favoriteList.setName(name);
                favoriteList.setRating(rating);

                if (MovieDetail.favoriteDatabase.favoriteDao().isFavorite(id) != 1) {
                    holder.fav_btn.setImageResource(R.mipmap.tikbookmark);
                    MovieDetail.favoriteDatabase.favoriteDao().addData(favoriteList);
                    v.setAlpha(1);

                } else  {
                    holder.fav_btn.setImageResource(R.mipmap.addbookmark);
                    MovieDetail.favoriteDatabase.favoriteDao().delete(favoriteList);
                    v.setAlpha(.5f);
                }





            }
        });

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClick(v,holder.getAdapterPosition());
            }
        });


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}