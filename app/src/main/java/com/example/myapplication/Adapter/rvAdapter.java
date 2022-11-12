package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Bean.Bean;
import com.example.myapplication.R;

import java.util.List;

public class rvAdapter extends RecyclerView.Adapter<rvAdapter.MyViewHolder>{

    List<Bean> disPlayName;
    List<Bean> buyPrice;
    List<Bean> sellPrice;
    List<Bean> buyVolume;
    List<Bean> sellVolume;
    Context context;

    public rvAdapter(List<Bean> disPlayName, List<Bean> buyPrice, List<Bean> sellPrice, List<Bean> buyVolume, List<Bean> sellVolume, Context context) {
        this.disPlayName = disPlayName;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.buyVolume = buyVolume;
        this.sellVolume = sellVolume;
        this.context = context;
    }

    @NonNull
    @Override
    public rvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.list_item,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull rvAdapter.MyViewHolder holder, int position) {
        holder.itemName.setText(disPlayName.get(position).getName());
        holder.buyPrice.setText(String.format("购买价：%s",buyPrice.get(position).getName()));
        holder.sellPrice.setText(String.format("出售价：%s",sellPrice.get(position).getName()));
        holder.buyVolume.setText(String.format("购买量：%s",buyVolume.get(position).getName()));
        holder.sellVolume.setText(String.format("出售量：%s",sellVolume.get(position).getName()));
    }

    @Override
    public int getItemCount() {
        return disPlayName == null ? 0 : disPlayName.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemName,buyPrice,sellPrice,buyVolume,sellVolume;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            buyPrice = itemView.findViewById(R.id.buyPrice);
            sellPrice = itemView.findViewById(R.id.sellPrice);
            buyVolume = itemView.findViewById(R.id.buyVolume);
            sellVolume = itemView.findViewById(R.id.sellVolume);

            itemView.setOnClickListener(v -> {
                if (onRecyclerViewItemClickListener != null){
                    onRecyclerViewItemClickListener.onRecyclerItemClick(getAdapterPosition());
                }
            });
        }
    }

    static OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener){
        onRecyclerViewItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener{
        void onRecyclerItemClick(int position);
    }
}
