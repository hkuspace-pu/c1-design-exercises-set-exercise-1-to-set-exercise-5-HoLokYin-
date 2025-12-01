package com.karzaf.sushihub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SushiAdapter extends RecyclerView.Adapter<SushiAdapter.SushiViewHolder> {

    private List<SushiItem> sushiList;
    private OnAddClickListener onAddClickListener;

    public interface OnAddClickListener {
        void onAddClick(SushiItem sushiItem);
    }

    public SushiAdapter(List<SushiItem> sushiList, OnAddClickListener listener) {
        this.sushiList = sushiList;
        this.onAddClickListener = listener;
    }

    @NonNull
    @Override
    public SushiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_detail, parent, false);
        return new SushiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SushiViewHolder holder, int position) {
        SushiItem sushiItem = sushiList.get(position);

        holder.itemName.setText(sushiItem.getName());
        holder.itemDescription.setText(sushiItem.getDescription());
        holder.itemPrice.setText(sushiItem.getPrice());
        holder.itemImage.setImageResource(sushiItem.getImageResId());

        holder.addCart.setOnClickListener(v -> {
            if (onAddClickListener != null) {
                onAddClickListener.onAddClick(sushiItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sushiList.size();
    }

    public static class SushiViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName;
        TextView itemDescription;
        TextView itemPrice;
        ImageButton addCart;

        public SushiViewHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.DetailItem);
            itemName = itemView.findViewById(R.id.DetailItemName);
            itemDescription = itemView.findViewById(R.id.DetailItemDescription);
            itemPrice = itemView.findViewById(R.id.DetailItemPrice);
            addCart = itemView.findViewById(R.id.DetailItemAddCart);
        }
    }
}