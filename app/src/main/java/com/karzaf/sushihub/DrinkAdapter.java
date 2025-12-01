package com.karzaf.sushihub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {

    private List<DrinkItem> drinkList;
    private OnAddClickListener onAddClickListener;
    public interface OnAddClickListener {
        void onAddClick(DrinkItem drinkItem);
    }
    public DrinkAdapter(List<DrinkItem> drinkList, OnAddClickListener listener) {
        this.drinkList = drinkList;
        this.onAddClickListener = listener;
    }
    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_detail, parent, false);
        return new DrinkViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position) {
        DrinkItem drinkItem = drinkList.get(position);

        holder.itemName.setText(drinkItem.getName());
        holder.itemDescription.setText(drinkItem.getDescription());
        holder.itemPrice.setText(drinkItem.getPrice());
        holder.itemImage.setImageResource(drinkItem.getImageResId());

        holder.addCart.setOnClickListener(v -> {
            if (onAddClickListener != null) {
                onAddClickListener.onAddClick(drinkItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }
    public static class DrinkViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName;
        TextView itemDescription;
        TextView itemPrice;
        ImageButton addCart;
        public DrinkViewHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.DetailItem);
            itemName = itemView.findViewById(R.id.DetailItemName);
            itemDescription = itemView.findViewById(R.id.DetailItemDescription);
            itemPrice = itemView.findViewById(R.id.DetailItemPrice);
            addCart = itemView.findViewById(R.id.DetailItemAddCart);
        }
    }
}