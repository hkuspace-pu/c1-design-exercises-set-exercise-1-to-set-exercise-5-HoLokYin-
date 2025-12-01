package com.karzaf.sushihub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class SuccessfulPage extends AppCompatActivity {

    private TextView orderNumberText;
    private TextView orderTotalText;
    private TextView orderItemsText;
    private Button buttonReturnToMenu;
    private Button buttonViewOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.successful);

        orderNumberText = findViewById(R.id.SuccessPageOrderNumber);
        orderTotalText = findViewById(R.id.SuccessPageTotalAmount);
        orderItemsText = findViewById(R.id.SuccessPageItems);
        buttonReturnToMenu = findViewById(R.id.ButtonReturnToMenu);
        buttonViewOrders = findViewById(R.id.ButtonViewOrders);

        int totalAmount = getIntent().getIntExtra("TOTAL_AMOUNT", 0);
        int itemCount = getIntent().getIntExtra("ITEM_COUNT", 0);

        String orderNumber = "#" + generateOrderNumber();
        orderNumberText.setText(orderNumber);
        orderTotalText.setText("¥" + totalAmount);
        orderItemsText.setText(itemCount + (itemCount == 1 ? " item" : " items"));

        buttonReturnToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccessfulPage.this, GuestPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        buttonViewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccessfulPage.this, GuestPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private String generateOrderNumber() {
        Random random = new Random();
        int orderNum = 10000 + random.nextInt(90000); // Generates 5-digit number
        return String.valueOf(orderNum);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SuccessfulPage.this, GuestPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
