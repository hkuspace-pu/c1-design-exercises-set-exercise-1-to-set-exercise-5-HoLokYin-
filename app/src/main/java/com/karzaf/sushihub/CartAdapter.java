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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;
    private OnCartItemChangeListener listener;

    public interface OnCartItemChangeListener {
        void onQuantityChanged();
        void onItemRemoved();
    }

    public CartAdapter(List<CartItem> cartItems, OnCartItemChangeListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);

        holder.itemNameCart.setText(item.getName());
        holder.itemPriceCart.setText(item.getPrice());
        holder.itemImageCart.setImageResource(item.getImageResId());
        holder.quantityText.setText(String.valueOf(item.getQuantity()));

        holder.buttonItemRemove.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                CartItem currentItem = cartItems.get(adapterPosition);
                
                if (currentItem.getQuantity() > 1) {
                    currentItem.setQuantity(currentItem.getQuantity() - 1);
                    notifyItemChanged(adapterPosition);
                    if (listener != null) {
                        listener.onQuantityChanged();
                    }
                } else {
                    cartItems.remove(adapterPosition);
                    notifyItemRemoved(adapterPosition);
                    notifyItemRangeChanged(adapterPosition, cartItems.size());
                    if (listener != null) {
                        listener.onItemRemoved();
                    }
                }
            }
        });

        holder.buttonItemAdd.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                CartItem currentItem = cartItems.get(adapterPosition);
                
                currentItem.setQuantity(currentItem.getQuantity() + 1);
                notifyItemChanged(adapterPosition);
                if (listener != null) {
                    listener.onQuantityChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImageCart;
        TextView itemNameCart;
        TextView itemPriceCart;
        TextView quantityText;
        ImageButton buttonItemRemove;
        ImageButton buttonItemAdd;

        public CartViewHolder(View itemView) {
            super(itemView);
            itemImageCart = itemView.findViewById(R.id.CartItem);
            itemNameCart = itemView.findViewById(R.id.CartItemName);
            itemPriceCart = itemView.findViewById(R.id.CartItemPrice);
            quantityText = itemView.findViewById(R.id.ItemQuantity);
            buttonItemRemove = itemView.findViewById(R.id.ButtonItemRemove);
            buttonItemAdd = itemView.findViewById(R.id.ButtonItemAdd);
        }
    }
}
