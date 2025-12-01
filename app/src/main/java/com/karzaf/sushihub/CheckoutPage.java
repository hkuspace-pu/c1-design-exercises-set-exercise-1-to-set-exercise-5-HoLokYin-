package com.karzaf.sushihub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CheckoutPage extends AppCompatActivity {

    private RecyclerView recyclerViewCart;
    private TextView itemCountText;
    private TextView subtotalText;
    private TextView taxText;
    private TextView totalText;
    private Button buttonCheckoutBack;
    private Button buttonConfirmOrder;

    private CartAdapter cartAdapter;
    private CartManager cartManager;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);

        dbHelper = DatabaseHelper.getInstance(this);

        recyclerViewCart = findViewById(R.id.RecyclerViewCart);
        itemCountText = findViewById(R.id.CheckoutPageItemCount);
        subtotalText = findViewById(R.id.CheckoutPageSubTotalPrice);
        taxText = findViewById(R.id.CheckoutPageTaxPrice);
        totalText = findViewById(R.id.CheckoutPageTotalPrice);
        buttonCheckoutBack = findViewById(R.id.ButtonCheckoutPageBack);
        buttonConfirmOrder = findViewById(R.id.ButtonCheckoutPageConfirm);

        cartManager = CartManager.getInstance();

        if (cartManager.isEmpty()) {
            Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setupRecyclerView();

        updateSummary();

        buttonCheckoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOrder();
            }
        });
    }

    private void setupRecyclerView() {
        List<CartItem> cartItems = cartManager.getCartItems();

        cartAdapter = new CartAdapter(cartItems, new CartAdapter.OnCartItemChangeListener() {
            @Override
            public void onQuantityChanged() {
                updateSummary();
            }

            @Override
            public void onItemRemoved() {
                updateSummary();
                if (cartManager.isEmpty()) {
                    Toast.makeText(CheckoutPage.this, "Cart is empty!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCart.setAdapter(cartAdapter);
        recyclerViewCart.setHasFixedSize(true);
    }

    private void updateSummary() {
        int itemCount = cartManager.getTotalItemCount();
        itemCountText.setText(itemCount + (itemCount == 1 ? " item" : " items"));

        int subtotal = cartManager.getSubtotal();
        int tax = cartManager.getTax();
        int total = cartManager.getTotal();

        subtotalText.setText("¥" + subtotal);
        taxText.setText("¥" + tax);
        totalText.setText("¥" + total);
    }

    private void confirmOrder() {
        int totalAmount = cartManager.getTotal();
        int itemCount = cartManager.getTotalItemCount();
        int subtotal = cartManager.getSubtotal();
        int tax = cartManager.getTax();
        List<CartItem> cartItems = cartManager.getCartItems();

        String orderUuid = dbHelper.createOrder(cartItems, subtotal, tax, totalAmount);

        if (orderUuid != null) {
            cartManager.clearCart();

            Intent intent = new Intent(CheckoutPage.this, SuccessfulPage.class);
            intent.putExtra("TOTAL_AMOUNT", totalAmount);
            intent.putExtra("ITEM_COUNT", itemCount);
            intent.putExtra("ORDER_UUID", orderUuid);
            startActivity(intent);

            finish();
        } else {
            Toast.makeText(this, "Error creating order", Toast.LENGTH_SHORT).show();
        }
    }
}
