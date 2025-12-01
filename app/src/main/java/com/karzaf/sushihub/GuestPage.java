package com.karzaf.sushihub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class GuestPage extends AppCompatActivity {

    private static final String TAG = "SushiHub_GuestPage";
    
    private RecyclerView recyclerView;
    private SushiAdapter sushiAdapter;
    private DrinkAdapter drinkAdapter;
    private Button btnSushiCategory;
    private Button btnDrinkCategory;

    private List<SushiItem> sushiList;
    private List<DrinkItem> drinkList;
    private CartManager cartManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        cartManager = CartManager.getInstance();
        Log.d(TAG, "Cart Manager initialized");

        recyclerView = findViewById(R.id.RecyclerViewMenu);
        btnSushiCategory = findViewById(R.id.ButtonSushiCategory);
        btnDrinkCategory = findViewById(R.id.ButtonDrinkCategory);

        Button backButton = findViewById(R.id.ButtonMenuBackPage);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Check button - Navigate to checkout
        Button checkButton = findViewById(R.id.ButtonMenuCheckout);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Check button clicked");
                Log.d(TAG, "Cart is empty: " + cartManager.isEmpty());
                Log.d(TAG, "Cart entries: " + cartManager.getCartEntriesCount());
                
                try {
                    if (cartManager.isEmpty()) {
                        Log.d(TAG, "Cart is empty, showing toast");
                        Toast.makeText(GuestPage.this, "Your cart is empty! Add items first.", Toast.LENGTH_LONG).show();
                    } else {
                        Log.d(TAG, "Opening checkout page with " + cartManager.getCartEntriesCount() + " items");
                        Intent intent = new Intent(GuestPage.this, CheckoutPage.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error handling checkout", e);
                    Toast.makeText(GuestPage.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        initializeSushiList();
        initializeDrinkList();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        btnSushiCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSushiCategory();
            }
        });

        btnDrinkCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDrinkCategory();
            }
        });

        showSushiCategory();
    }

    private void initializeSushiList() {
        sushiList = new ArrayList<>();
        sushiList.add(new SushiItem("Salmon Nigiri", "Fresh salmon over sushi rice", "¥850", R.drawable.salom_sushi));
        sushiList.add(new SushiItem("Tuna Nigiri", "Premium tuna over sushi rice", "¥950", R.drawable.tuna_nigiri));
        sushiList.add(new SushiItem("California Roll", "Crab, avocado, cucumber roll", "¥650", R.drawable.california_roll));
        sushiList.add(new SushiItem("Eel Nigiri", "Grilled eel with special sauce", "¥900", R.drawable.eel_nigiri));
        sushiList.add(new SushiItem("Shrimp Tempura Roll", "Crispy shrimp tempura roll", "¥800", R.drawable.shrimp_tempura_roll));
        sushiList.add(new SushiItem("Philadelphia Roll", "Salmon, cream cheese, cucumber", "¥700", R.drawable.philadelphia_roll));
        sushiList.add(new SushiItem("Volcano Roll", "Spicy baked seafood roll", "¥1,100", R.drawable.volcano_roll));

        sushiAdapter = new SushiAdapter(sushiList, new SushiAdapter.OnAddClickListener() {
            @Override
            public void onAddClick(SushiItem sushiItem) {
                try {
                    // Add to cart
                    cartManager.addItem(
                            sushiItem.getName(),
                            sushiItem.getPrice(),
                            sushiItem.getImageResId()
                    );
                    Log.d(TAG, "Added " + sushiItem.getName() + " to cart. Total items: " + cartManager.getCartEntriesCount());
                    Toast.makeText(GuestPage.this,
                            sushiItem.getName() + " added to cart!",
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e(TAG, "Error adding item to cart", e);
                    Toast.makeText(GuestPage.this, "Error adding item", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initializeDrinkList() {
        drinkList = new ArrayList<>();
        drinkList.add(new DrinkItem("Green Tea", "Traditional Japanese green tea", "¥300", R.drawable.green_tea));
        drinkList.add(new DrinkItem("Sake", "Premium Japanese rice wine", "¥800", R.drawable.sake));
        drinkList.add(new DrinkItem("Asahi Beer", "Refreshing Japanese beer", "¥600", R.drawable.asahi_beer));
        drinkList.add(new DrinkItem("Ramune", "Classic Japanese soda", "¥400", R.drawable.ramune));
        drinkList.add(new DrinkItem("Matcha Latte", "Creamy matcha green tea latte", "¥500", R.drawable.matcha_latte));
        drinkList.add(new DrinkItem("Iced Oolong Tea", "Refreshing oolong tea", "¥350", R.drawable.iced_oolong_tea));
        drinkList.add(new DrinkItem("Plum Wine", "Sweet Japanese plum wine", "¥700", R.drawable.plum_wine));
        drinkList.add(new DrinkItem("Calpis", "Japanese cultured milk drink", "¥400", R.drawable.calpis));

        drinkAdapter = new DrinkAdapter(drinkList, new DrinkAdapter.OnAddClickListener() {
            @Override
            public void onAddClick(DrinkItem drinkItem) {
                try {
                    // Add to cart
                    cartManager.addItem(
                            drinkItem.getName(),
                            drinkItem.getPrice(),
                            drinkItem.getImageResId()
                    );
                    Log.d(TAG, "Added " + drinkItem.getName() + " to cart. Total items: " + cartManager.getCartEntriesCount());
                    Toast.makeText(GuestPage.this,
                            drinkItem.getName() + " added to cart!",
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e(TAG, "Error adding drink to cart", e);
                    Toast.makeText(GuestPage.this, "Error adding item", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showSushiCategory() {
        btnSushiCategory.setBackgroundTintList(getResources().getColorStateList(R.color.red));
        btnDrinkCategory.setBackgroundTintList(getResources().getColorStateList(R.color.black));

        recyclerView.setAdapter(sushiAdapter);
    }

    private void showDrinkCategory() {
        btnSushiCategory.setBackgroundTintList(getResources().getColorStateList(R.color.black));
        btnDrinkCategory.setBackgroundTintList(getResources().getColorStateList(R.color.red));
        recyclerView.setAdapter(drinkAdapter);
    }
}
