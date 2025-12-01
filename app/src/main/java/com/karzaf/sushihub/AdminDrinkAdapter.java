package com.karzaf.sushihub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdminDrinkAdapter extends RecyclerView.Adapter<AdminDrinkAdapter.DrinkViewHolder> {

    private Context context;
    private List<DrinkModel> drinkList;
    private OnItemActionListener listener;

    public interface OnItemActionListener {
        void onEdit(DrinkModel drink);
        void onDelete(DrinkModel drink);
    }

    public AdminDrinkAdapter(Context context, List<DrinkModel> drinkList, OnItemActionListener listener) {
        this.context = context;
        this.drinkList = drinkList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_item, parent, false);
        return new DrinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position) {
        DrinkModel drink = drinkList.get(position);

        holder.itemName.setText(drink.getName());
        holder.itemDescription.setText(drink.getDescription());
        holder.itemPrice.setText(drink.getPrice());
        holder.itemUuid.setText("UUID: " + drink.getUuid());

        String imageName = drink.getImageName();
        if (imageName != null && !imageName.isEmpty()) {
            int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
            if (resId != 0) {
                holder.itemImage.setImageResource(resId);
            } else {
                holder.itemImage.setImageResource(R.drawable.green_tea); // Default image
            }
        }

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEdit(drink);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDelete(drink);
            }
        });
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }

    public static class DrinkViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName, itemDescription, itemPrice, itemUuid;
        ImageButton btnEdit, btnDelete;

        public DrinkViewHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.AdminItem);
            itemName = itemView.findViewById(R.id.AdminItemName);
            itemDescription = itemView.findViewById(R.id.AdminItemDescription);
            itemPrice = itemView.findViewById(R.id.AdminItemPrice);
            itemUuid = itemView.findViewById(R.id.adminItemUuid);
            btnEdit = itemView.findViewById(R.id.ButtonAdminItemEdit);
            btnDelete = itemView.findViewById(R.id.ButtonAdminItemDelete);
        }
    }
}
