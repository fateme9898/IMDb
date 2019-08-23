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
import info.androidhive.retrofit.model.people.Peaple;

public class PeapleAdapter extends RecyclerView.Adapter<PeapleAdapter.PeapleViewHolder> {

    public static List<Peaple> peaple;
    private int rowLayout;
    private Context context;


    public static class PeapleViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout peaplelayout;
        TextView title;
        TextView a;
        TextView b;
        ImageView image3;




        public PeapleViewHolder(View v) {
            super(v);
            peaplelayout =  v.findViewById(R.id.peaple_layout);
            title = (TextView) v.findViewById(R.id.title3);

            image3=v.findViewById(R.id.image3);


        }
    }

    public PeapleAdapter(List<Peaple> peaples, int rowLayout, Context context) {
        this.peaple = peaples;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public PeapleAdapter.PeapleViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new PeapleViewHolder(view);
    }


    @Override
    public void onBindViewHolder(PeapleViewHolder holder, final int position) {
        holder.title.setText(peaple.get(position).getName());



        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/original" + peaple

                        .get(position)

                        .getProfilePath())
                .fit()
                .into(holder.image3)
        ;
    }

    @Override
    public int getItemCount() {
        return peaple.size();
    }
}