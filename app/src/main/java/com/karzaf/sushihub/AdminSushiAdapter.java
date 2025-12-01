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

public class AdminSushiAdapter extends RecyclerView.Adapter<AdminSushiAdapter.SushiViewHolder> {

    private Context context;
    private List<SushiModel> sushiList;
    private OnItemActionListener listener;

    public interface OnItemActionListener {
        void onEdit(SushiModel sushi);
        void onDelete(SushiModel sushi);
    }

    public AdminSushiAdapter(Context context, List<SushiModel> sushiList, OnItemActionListener listener) {
        this.context = context;
        this.sushiList = sushiList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SushiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_item, parent, false);
        return new SushiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SushiViewHolder holder, int position) {
        SushiModel sushi = sushiList.get(position);

        holder.itemName.setText(sushi.getName());
        holder.itemDescription.setText(sushi.getDescription());
        holder.itemPrice.setText(sushi.getPrice());
        holder.itemUuid.setText("UUID: " + sushi.getUuid());

        String imageName = sushi.getImageName();
        if (imageName != null && !imageName.isEmpty()) {
            int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
            if (resId != 0) {
                holder.itemImage.setImageResource(resId);
            } else {
                holder.itemImage.setImageResource(R.drawable.salom_sushi); // Default image
            }
        }

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEdit(sushi);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDelete(sushi);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sushiList.size();
    }

    public static class SushiViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName, itemDescription, itemPrice, itemUuid;
        ImageButton btnEdit, btnDelete;

        public SushiViewHolder(View itemView) {
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
