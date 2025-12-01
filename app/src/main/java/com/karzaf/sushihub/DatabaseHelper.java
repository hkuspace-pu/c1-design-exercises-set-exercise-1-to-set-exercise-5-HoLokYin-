package com.karzaf.sushihub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "restaurant.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ORDER = "OrderTable";
    private static final String TABLE_ACCOUNT = "Account";
    private static final String TABLE_DRINK = "Drink";
    private static final String TABLE_SUSHI = "Sushi";
    private static final String COLUMN_UUID = "uuid";
    private static final String COLUMN_ORDER_NUMBER = "order_number";
    private static final String COLUMN_ORDER_ITEMS = "order_items";
    private static final String COLUMN_ORDER_TOTAL = "order_total";
    private static final String COLUMN_ORDER_TAX = "order_tax";
    private static final String COLUMN_ORDER_SUBTOTAL = "order_subtotal";
    private static final String COLUMN_ORDER_ITEM_COUNT = "item_count";
    private static final String COLUMN_ORDER_TIMESTAMP = "timestamp";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_DRINK_NAME = "drink_name";
    private static final String COLUMN_DRINK_DESCRIPTION = "drink_description";
    private static final String COLUMN_DRINK_PRICE = "drink_price";
    private static final String COLUMN_DRINK_IMAGE = "drink_image";
    private static final String COLUMN_SUSHI_NAME = "sushi_name";
    private static final String COLUMN_SUSHI_DESCRIPTION = "sushi_description";
    private static final String COLUMN_SUSHI_PRICE = "sushi_price";
    private static final String COLUMN_SUSHI_IMAGE = "sushi_image";
    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ORDER_TABLE = "CREATE TABLE " + TABLE_ORDER + " ("
                + COLUMN_UUID + " TEXT PRIMARY KEY, "
                + COLUMN_ORDER_NUMBER + " TEXT NOT NULL, "
                + COLUMN_ORDER_ITEMS + " TEXT NOT NULL, "
                + COLUMN_ORDER_TOTAL + " INTEGER NOT NULL, "
                + COLUMN_ORDER_TAX + " INTEGER NOT NULL, "
                + COLUMN_ORDER_SUBTOTAL + " INTEGER NOT NULL, "
                + COLUMN_ORDER_ITEM_COUNT + " INTEGER NOT NULL, "
                + COLUMN_ORDER_TIMESTAMP + " INTEGER NOT NULL)";

        String CREATE_ACCOUNT_TABLE = "CREATE TABLE " + TABLE_ACCOUNT + " ("
                + COLUMN_UUID + " TEXT PRIMARY KEY, "
                + COLUMN_USERNAME + " TEXT UNIQUE NOT NULL, "
                + COLUMN_PASSWORD + " TEXT NOT NULL)";

        String CREATE_DRINK_TABLE = "CREATE TABLE " + TABLE_DRINK + " ("
                + COLUMN_UUID + " TEXT PRIMARY KEY, "
                + COLUMN_DRINK_NAME + " TEXT NOT NULL, "
                + COLUMN_DRINK_DESCRIPTION + " TEXT, "
                + COLUMN_DRINK_PRICE + " TEXT NOT NULL, "
                + COLUMN_DRINK_IMAGE + " TEXT)";

        String CREATE_SUSHI_TABLE = "CREATE TABLE " + TABLE_SUSHI + " ("
                + COLUMN_UUID + " TEXT PRIMARY KEY, "
                + COLUMN_SUSHI_NAME + " TEXT NOT NULL, "
                + COLUMN_SUSHI_DESCRIPTION + " TEXT, "
                + COLUMN_SUSHI_PRICE + " TEXT NOT NULL, "
                + COLUMN_SUSHI_IMAGE + " TEXT)";

        db.execSQL(CREATE_ORDER_TABLE);
        db.execSQL(CREATE_ACCOUNT_TABLE);
        db.execSQL(CREATE_DRINK_TABLE);
        db.execSQL(CREATE_SUSHI_TABLE);

        Log.d(TAG, "Database tables created");

        insertDefaultAdmin(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DRINK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUSHI);
        onCreate(db);
    }

    private void insertDefaultAdmin(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_UUID, UUID.randomUUID().toString());
        values.put(COLUMN_USERNAME, "admin");
        values.put(COLUMN_PASSWORD, "admin123");
        db.insert(TABLE_ACCOUNT, null, values);
        Log.d(TAG, "Default admin account created");
    }

    // ==================== ORDER METHODS ====================

    public String createOrder(List<CartItem> items, int subtotal, int tax, int total) {
        SQLiteDatabase db = this.getWritableDatabase();
        String orderUUID = UUID.randomUUID().toString();
        String orderNumber = "#" + (10000 + (int)(Math.random() * 90000));

        // Convert cart items to JSON-like string
        StringBuilder itemsJson = new StringBuilder("[");
        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            if (i > 0) itemsJson.append(",");
            itemsJson.append("{")
                    .append("\"name\":\"").append(item.getName()).append("\",")
                    .append("\"price\":\"").append(item.getPrice()).append("\",")
                    .append("\"quantity\":").append(item.getQuantity())
                    .append("}");
        }
        itemsJson.append("]");

        ContentValues values = new ContentValues();
        values.put(COLUMN_UUID, orderUUID);
        values.put(COLUMN_ORDER_NUMBER, orderNumber);
        values.put(COLUMN_ORDER_ITEMS, itemsJson.toString());
        values.put(COLUMN_ORDER_TOTAL, total);
        values.put(COLUMN_ORDER_TAX, tax);
        values.put(COLUMN_ORDER_SUBTOTAL, subtotal);
        values.put(COLUMN_ORDER_ITEM_COUNT, items.size());
        values.put(COLUMN_ORDER_TIMESTAMP, System.currentTimeMillis());

        long result = db.insert(TABLE_ORDER, null, values);
        db.close();

        if (result != -1) {
            Log.d(TAG, "Order created: " + orderNumber + " UUID: " + orderUUID);
            return orderUUID;
        }
        return null;
    }

    public List<OrderModel> getAllOrders() {
        List<OrderModel> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ORDER + " ORDER BY " + COLUMN_ORDER_TIMESTAMP + " DESC", null);

        if (cursor.moveToFirst()) {
            do {
                OrderModel order = new OrderModel();
                order.setUuid(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UUID)));
                order.setOrderNumber(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_NUMBER)));
                order.setOrderItems(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_ITEMS)));
                order.setTotal(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_TOTAL)));
                order.setTax(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_TAX)));
                order.setSubtotal(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_SUBTOTAL)));
                order.setItemCount(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_ITEM_COUNT)));
                order.setTimestamp(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ORDER_TIMESTAMP)));
                orders.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return orders;
    }

    public OrderModel getOrderByUUID(String uuid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ORDER, null, COLUMN_UUID + "=?",
                new String[]{uuid}, null, null, null);

        OrderModel order = null;
        if (cursor.moveToFirst()) {
            order = new OrderModel();
            order.setUuid(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UUID)));
            order.setOrderNumber(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_NUMBER)));
            order.setOrderItems(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_ITEMS)));
            order.setTotal(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_TOTAL)));
            order.setTax(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_TAX)));
            order.setSubtotal(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_SUBTOTAL)));
            order.setItemCount(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_ITEM_COUNT)));
            order.setTimestamp(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ORDER_TIMESTAMP)));
        }
        cursor.close();
        db.close();
        return order;
    }

    // ==================== ACCOUNT METHODS ====================

    public String createAccount(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String uuid = UUID.randomUUID().toString();

        ContentValues values = new ContentValues();
        values.put(COLUMN_UUID, uuid);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_ACCOUNT, null, values);
        db.close();

        return result != -1 ? uuid : null;
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ACCOUNT, null,
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);

        boolean loginSuccess = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return loginSuccess;
    }

    // ==================== DRINK METHODS ====================

    public String addDrink(String name, String description, String price, String imageName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String uuid = UUID.randomUUID().toString();

        ContentValues values = new ContentValues();
        values.put(COLUMN_UUID, uuid);
        values.put(COLUMN_DRINK_NAME, name);
        values.put(COLUMN_DRINK_DESCRIPTION, description);
        values.put(COLUMN_DRINK_PRICE, price);
        values.put(COLUMN_DRINK_IMAGE, imageName);

        long result = db.insert(TABLE_DRINK, null, values);
        db.close();

        return result != -1 ? uuid : null;
    }

    public List<DrinkModel> getAllDrinks() {
        List<DrinkModel> drinks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DRINK, null);

        if (cursor.moveToFirst()) {
            do {
                DrinkModel drink = new DrinkModel();
                drink.setUuid(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UUID)));
                drink.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DRINK_NAME)));
                drink.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DRINK_DESCRIPTION)));
                drink.setPrice(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DRINK_PRICE)));
                drink.setImageName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DRINK_IMAGE)));
                drinks.add(drink);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return drinks;
    }

    public boolean updateDrink(String uuid, String name, String description, String price, String imageName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DRINK_NAME, name);
        values.put(COLUMN_DRINK_DESCRIPTION, description);
        values.put(COLUMN_DRINK_PRICE, price);
        values.put(COLUMN_DRINK_IMAGE, imageName);

        int rows = db.update(TABLE_DRINK, values, COLUMN_UUID + "=?", new String[]{uuid});
        db.close();
        return rows > 0;
    }

    public boolean deleteDrink(String uuid) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_DRINK, COLUMN_UUID + "=?", new String[]{uuid});
        db.close();
        return rows > 0;
    }

    // ==================== SUSHI METHODS ====================

    public String addSushi(String name, String description, String price, String imageName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String uuid = UUID.randomUUID().toString();

        ContentValues values = new ContentValues();
        values.put(COLUMN_UUID, uuid);
        values.put(COLUMN_SUSHI_NAME, name);
        values.put(COLUMN_SUSHI_DESCRIPTION, description);
        values.put(COLUMN_SUSHI_PRICE, price);
        values.put(COLUMN_SUSHI_IMAGE, imageName);

        long result = db.insert(TABLE_SUSHI, null, values);
        db.close();

        return result != -1 ? uuid : null;
    }

    public List<SushiModel> getAllSushi() {
        List<SushiModel> sushiList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SUSHI, null);

        if (cursor.moveToFirst()) {
            do {
                SushiModel sushi = new SushiModel();
                sushi.setUuid(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UUID)));
                sushi.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUSHI_NAME)));
                sushi.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUSHI_DESCRIPTION)));
                sushi.setPrice(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUSHI_PRICE)));
                sushi.setImageName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUSHI_IMAGE)));
                sushiList.add(sushi);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return sushiList;
    }

    public boolean updateSushi(String uuid, String name, String description, String price, String imageName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SUSHI_NAME, name);
        values.put(COLUMN_SUSHI_DESCRIPTION, description);
        values.put(COLUMN_SUSHI_PRICE, price);
        values.put(COLUMN_SUSHI_IMAGE, imageName);

        int rows = db.update(TABLE_SUSHI, values, COLUMN_UUID + "=?", new String[]{uuid});
        db.close();
        return rows > 0;
    }

    public boolean deleteSushi(String uuid) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_SUSHI, COLUMN_UUID + "=?", new String[]{uuid});
        db.close();
        return rows > 0;
    }
}
