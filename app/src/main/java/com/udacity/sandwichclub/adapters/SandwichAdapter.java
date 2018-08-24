package com.udacity.sandwichclub.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.R;

/**
 * Created by Anamika Tripathi on 24/8/18.
 */
public class SandwichAdapter extends RecyclerView.Adapter<SandwichAdapter.MyViewHolder> {
    private String[] sandwiches;
    private Context context;
    private SandwichItemListener listener;
    private String[] sandwichImages;

    public SandwichAdapter(String[] sandwiches, Context context, SandwichItemListener listener) {
        this.sandwiches = sandwiches;
        this.context = context;
        this.listener = listener;
        this.sandwichImages = context.getResources().getStringArray(R.array.sandwich_images);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sandwich, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.sandwichName.setText(sandwiches[position]);
        Picasso.with(context)
                .load(sandwichImages[position])
                .into(holder.sandwichIv);
    }

    @Override
    public int getItemCount() {
        return sandwiches.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView sandwichIv;
        private TextView sandwichName;

        MyViewHolder(View itemView) {
            super(itemView);
            sandwichName = itemView.findViewById(R.id.item_sandwich_name);
            sandwichIv = itemView.findViewById(R.id.item_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listener.onItemClicked(position);
        }
    }

    public interface SandwichItemListener {
        void onItemClicked(int i);
    }
}
