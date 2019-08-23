package retrofit.anew.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import info.androidhive.retrofit.db.FavoriteList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
private List<FavoriteList>favoriteLists;
        Context context;

public FavoriteAdapter(List<FavoriteList> favoriteLists, Context context) {
        this.favoriteLists = favoriteLists;
        this.context = context;
        }

    public FavoriteAdapter(List <FavoriteList> favoriteLists, Context applicationContext) {

    }

    @NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favorites_list,viewGroup,false);
        return new ViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        FavoriteList fl=favoriteLists.get(i);
        Picasso.with(context).load(fl.getImage()).into(viewHolder.img);
        viewHolder.tv.setText(fl.getName());
        }

@Override
public int getItemCount() {
        return favoriteLists.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder{
    ImageView img;
    TextView tv;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        img=(ImageView)itemView.findViewById(R.id.fimg_pr);
        tv=(TextView)itemView.findViewById(R.id.ftv_name);
    }
}
}
