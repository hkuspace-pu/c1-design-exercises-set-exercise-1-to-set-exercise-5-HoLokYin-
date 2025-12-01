package com.karzaf.sushihub;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdminPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnOrdersTab, btnSushiTab, btnDrinksTab;
    private Button btnBack, btnAddItem;

    private DatabaseHelper dbHelper;
    private String currentTab = "orders"; // orders, sushi, drinks

    private AdminOrderAdapter orderAdapter;
    private AdminSushiAdapter sushiAdapter;
    private AdminDrinkAdapter drinkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        dbHelper = DatabaseHelper.getInstance(this);

        recyclerView = findViewById(R.id.RecyclerViewAdmin);
        btnOrdersTab = findViewById(R.id.ButtonListOrder);
        btnSushiTab = findViewById(R.id.ButtonListSushi);
        btnDrinksTab = findViewById(R.id.ButtonListDrink);
        btnBack = findViewById(R.id.ButtonBack);
        btnAddItem = findViewById(R.id.ButtonAddItem);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        btnOrdersTab.setOnClickListener(v -> showOrdersTab());
        btnSushiTab.setOnClickListener(v -> showSushiTab());
        btnDrinksTab.setOnClickListener(v -> showDrinksTab());

        btnBack.setOnClickListener(v -> finish());

        btnAddItem.setOnClickListener(v -> showAddItemDialog());

        showOrdersTab();
    }

    private void showOrdersTab() {
        currentTab = "orders";
        updateTabColors();
        btnAddItem.setVisibility(View.GONE);

        List<OrderModel> orders = dbHelper.getAllOrders();
        orderAdapter = new AdminOrderAdapter(this, orders);
        recyclerView.setAdapter(orderAdapter);
    }

    private void showSushiTab() {
        currentTab = "sushi";
        updateTabColors();
        btnAddItem.setVisibility(View.VISIBLE);

        List<SushiModel> sushiList = dbHelper.getAllSushi();
        sushiAdapter = new AdminSushiAdapter(this, sushiList, new AdminSushiAdapter.OnItemActionListener() {
            @Override
            public void onEdit(SushiModel sushi) {
                showEditSushiDialog(sushi);
            }

            @Override
            public void onDelete(SushiModel sushi) {
                confirmDelete("Sushi", sushi.getName(), sushi.getUuid(), "sushi");
            }
        });
        recyclerView.setAdapter(sushiAdapter);
    }

    private void showDrinksTab() {
        currentTab = "drinks";
        updateTabColors();
        btnAddItem.setVisibility(View.VISIBLE);

        List<DrinkModel> drinkList = dbHelper.getAllDrinks();
        drinkAdapter = new AdminDrinkAdapter(this, drinkList, new AdminDrinkAdapter.OnItemActionListener() {
            @Override
            public void onEdit(DrinkModel drink) {
                showEditDrinkDialog(drink);
            }

            @Override
            public void onDelete(DrinkModel drink) {
                confirmDelete("Drink", drink.getName(), drink.getUuid(), "drink");
            }
        });
        recyclerView.setAdapter(drinkAdapter);
    }

    private void updateTabColors() {
        btnOrdersTab.setBackgroundTintList(getResources().getColorStateList(
                currentTab.equals("orders") ? R.color.red : R.color.black));
        btnSushiTab.setBackgroundTintList(getResources().getColorStateList(
                currentTab.equals("sushi") ? R.color.red : R.color.black));
        btnDrinksTab.setBackgroundTintList(getResources().getColorStateList(
                currentTab.equals("drinks") ? R.color.red : R.color.black));
    }

    private void showAddItemDialog() {
        if (currentTab.equals("sushi")) {
            showEditSushiDialog(null);
        } else if (currentTab.equals("drinks")) {
            showEditDrinkDialog(null);
        }
    }

    private void showEditSushiDialog(SushiModel sushi) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.edit_item, null);
        builder.setView(dialogView);

        EditText etName = dialogView.findViewById(R.id.EditItemName);
        EditText etDescription = dialogView.findViewById(R.id.EditItemDescription);
        EditText etPrice = dialogView.findViewById(R.id.EditItemPrice);
        EditText etImage = dialogView.findViewById(R.id.EditItemImage);

        builder.setTitle(sushi == null ? "Add Sushi" : "Edit Sushi");

        if (sushi != null) {
            etName.setText(sushi.getName());
            etDescription.setText(sushi.getDescription());
            etPrice.setText(sushi.getPrice());
            etImage.setText(sushi.getImageName());
        }

        builder.setPositiveButton("Save", (dialog, which) -> {
            String name = etName.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            String price = etPrice.getText().toString().trim();
            String image = etImage.getText().toString().trim();

            if (name.isEmpty() || price.isEmpty()) {
                Toast.makeText(this, "Name and price are required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (sushi == null) {
                // Add new
                String uuid = dbHelper.addSushi(name, description, price, image);
                if (uuid != null) {
                    Toast.makeText(this, "Sushi added successfully", Toast.LENGTH_SHORT).show();
                    showSushiTab(); // Refresh
                }
            } else {
                // Update existing
                boolean success = dbHelper.updateSushi(sushi.getUuid(), name, description, price, image);
                if (success) {
                    Toast.makeText(this, "Sushi updated successfully", Toast.LENGTH_SHORT).show();
                    showSushiTab(); // Refresh
                }
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showEditDrinkDialog(DrinkModel drink) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.edit_item, null);
        builder.setView(dialogView);

        EditText etName = dialogView.findViewById(R.id.EditItemName);
        EditText etDescription = dialogView.findViewById(R.id.EditItemDescription);
        EditText etPrice = dialogView.findViewById(R.id.EditItemPrice);
        EditText etImage = dialogView.findViewById(R.id.EditItemImage);

        builder.setTitle(drink == null ? "Add Drink" : "Edit Drink");

        if (drink != null) {
            etName.setText(drink.getName());
            etDescription.setText(drink.getDescription());
            etPrice.setText(drink.getPrice());
            etImage.setText(drink.getImageName());
        }

        builder.setPositiveButton("Save", (dialog, which) -> {
            String name = etName.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            String price = etPrice.getText().toString().trim();
            String image = etImage.getText().toString().trim();

            if (name.isEmpty() || price.isEmpty()) {
                Toast.makeText(this, "Name and price are required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (drink == null) {
                String uuid = dbHelper.addDrink(name, description, price, image);
                if (uuid != null) {
                    Toast.makeText(this, "Drink added successfully", Toast.LENGTH_SHORT).show();
                    showDrinksTab();
                }
            } else {
                boolean success = dbHelper.updateDrink(drink.getUuid(), name, description, price, image);
                if (success) {
                    Toast.makeText(this, "Drink updated successfully", Toast.LENGTH_SHORT).show();
                    showDrinksTab();
                }
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void confirmDelete(String type, String name, String uuid, String table) {
        new AlertDialog.Builder(this)
                .setTitle("Delete " + type)
                .setMessage("Are you sure you want to delete \"" + name + "\"?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    boolean success;
                    if (table.equals("sushi")) {
                        success = dbHelper.deleteSushi(uuid);
                        if (success) {
                            Toast.makeText(this, "Sushi deleted", Toast.LENGTH_SHORT).show();
                            showSushiTab();
                        }
                    } else {
                        success = dbHelper.deleteDrink(uuid);
                        if (success) {
                            Toast.makeText(this, "Drink deleted", Toast.LENGTH_SHORT).show();
                            showDrinksTab();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}