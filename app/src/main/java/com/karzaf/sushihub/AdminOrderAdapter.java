package com.karzaf.sushihub;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdminOrderAdapter extends RecyclerView.Adapter<AdminOrderAdapter.OrderViewHolder> {

    private Context context;
    private List<OrderModel> orders;

    public AdminOrderAdapter(Context context, List<OrderModel> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderModel order = orders.get(position);

        holder.orderNumber.setText(order.getOrderNumber());
        holder.orderTotal.setText("¥" + order.getTotal());
        holder.orderItemCount.setText(String.valueOf(order.getItemCount()));
        holder.orderSubtotal.setText("¥" + order.getSubtotal());
        holder.orderTax.setText("¥" + order.getTax());
        holder.orderUuid.setText("UUID: " + order.getUuid());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String dateTime = sdf.format(new Date(order.getTimestamp()));
        holder.orderTimestamp.setText(dateTime);
        holder.btnViewDetails.setOnClickListener(v -> showOrderDetails(order));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    private void showOrderDetails(OrderModel order) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Order Details - " + order.getOrderNumber());

        String details = "Order UUID: " + order.getUuid() + "\n\n"
                + "Items: " + order.getItemCount() + "\n"
                + "Subtotal: ¥" + order.getSubtotal() + "\n"
                + "Tax (10%): ¥" + order.getTax() + "\n"
                + "Total: ¥" + order.getTotal() + "\n\n"
                + "Order Items:\n" + order.getOrderItems();

        builder.setMessage(details);
        builder.setPositiveButton("Close", null);
        builder.show();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderNumber, orderTotal, orderItemCount, orderSubtotal, orderTax, orderTimestamp, orderUuid;
        Button btnViewDetails;

        public OrderViewHolder(View itemView) {
            super(itemView);
            orderNumber = itemView.findViewById(R.id.AdminOrderNumber);
            orderTotal = itemView.findViewById(R.id.AdminOrderTotal);
            orderItemCount = itemView.findViewById(R.id.AdminOrderItemCount);
            orderSubtotal = itemView.findViewById(R.id.AdminOrderSubTotal);
            orderTax = itemView.findViewById(R.id.AdminOrderTax);
            orderTimestamp = itemView.findViewById(R.id.AdminOrderTimesTamp);
            orderUuid = itemView.findViewById(R.id.AdminOrderUDID);
            btnViewDetails = itemView.findViewById(R.id.BottenAdminOrder);
        }
    }
}