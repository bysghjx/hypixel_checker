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

public class AHP_Adapter extends RecyclerView.Adapter<AHP_Adapter.MyViewHolder>{
    List<Bean> bin;
    List<Bean> starting_bid;
    List<Bean> highest_bid_amount;
    List<Bean> end;
    List<Bean> item_name;
    Context context;

    public AHP_Adapter(List<Bean> bin, List<Bean> starting_bid, List<Bean> highest_bid_amount, List<Bean> end, List<Bean> item_name, Context context) {
        this.bin = bin;
        this.starting_bid = starting_bid;
        this.highest_bid_amount = highest_bid_amount;
        this.end = end;
        this.item_name = item_name;
        this.context = context;
    }

    @NonNull
    @Override
    public AHP_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.list_item,null);
        return new AHP_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AHP_Adapter.MyViewHolder holder, int position) {
        autoRefresh(holder, position);
    }

    @Override
    public int getItemCount() {
        return bin == null ? 0 : bin.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Bin,Starting_bid,Highest_bid_amount,End,Item_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Item_name = itemView.findViewById(R.id.itemName);
            Starting_bid = itemView.findViewById(R.id.buyPrice);
            Highest_bid_amount = itemView.findViewById(R.id.sellPrice);
            End = itemView.findViewById(R.id.buyVolume);
            Bin = itemView.findViewById(R.id.sellVolume);

            itemView.setOnClickListener(v -> {
                if (onRecyclerViewItemClickListener != null){
                    onRecyclerViewItemClickListener.onRecyclerItemClick(getAdapterPosition());
                }
            });
        }
    }

    static rvAdapter.OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public void setOnRecyclerViewItemClickListener(rvAdapter.OnRecyclerViewItemClickListener listener){
        onRecyclerViewItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener{
        void onRecyclerItemClick(int position);
    }

    void autoRefresh(AHP_Adapter.MyViewHolder holder, int position){
        //holder.Item_name.setText(position+"."+item_name.get(position).getName());
        holder.Item_name.setText(String.format("%s.%s",position+1,item_name.get(position).getName()));
        holder.Starting_bid.setText(String.format("最高购买价：%s",starting_bid.get(position).getName()));
        holder.Highest_bid_amount.setText(String.format("是否被购买：%s",highest_bid_amount.get(position).getName()));
        holder.End.setText(String.format("结束时间：\n%s",end.get(position).getName()));
        holder.Bin.setText(String.format("BIN：%s",bin.get(position).getName()));

    }
}
