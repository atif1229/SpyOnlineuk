package com.example.spyonlineuk.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.spyonlineuk.models.CartItem;
import com.example.spyonlineuk.models.Products;
import com.example.spyonlineuk.models.Sizes;

import java.util.ArrayList;

public class CartHelper extends SQLiteOpenHelper {
    public CartHelper(Context context) {
        super(context, "shoppingcart", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE cart (cartId INTEGER PRIMARY KEY AUTOINCREMENT, productId INTEGER,productName TEXT,productPrice INTEGER, productImage TEXT,productDescription TEXT, productDiscount INTEGER,selectedSizeId INTEGER,selectedSizeName TEXT,quantity INTEGER)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS cart");
        onCreate(db);


    }

    public long addToCart(Products products, Sizes selectedSizes, int quantity) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("productId", products.getCatId());
        cv.put("productName", products.getProductName());
        cv.put("productPrice", products.getProductPrice());
        cv.put("productImage", products.getProductImage());
        cv.put("productDescription", products.getProductDetail());
        cv.put("productDiscount", products.getProductDiscount());

        cv.put("selectedSizeId", selectedSizes.getSizeId());
        cv.put("selectedSizeName", selectedSizes.getSizeName());

        cv.put("quantity", quantity);
        return db.insert("cart", null, cv);

    }

    public ArrayList<CartItem> getAllCartItem() {

        ArrayList<CartItem> cartItemsList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM cart ORDER BY cartId";
        Cursor c = db.rawQuery(query, null);

        while (c.moveToNext()) {

            int cartId = c.getInt(c.getColumnIndex("cartId"));

            Products pro = new Products();
            int productId = c.getInt(c.getColumnIndex("productId"));
            String productName = c.getString(c.getColumnIndex("productName"));
            String productImage = c.getString(c.getColumnIndex("productImage"));
            String productDescription = c.getString(c.getColumnIndex("productDescription"));
            int productPrice = c.getInt(c.getColumnIndex("productPrice"));
            int productDiscount = c.getInt(c.getColumnIndex("productDiscount"));

            pro.setProductId(productId);
            pro.setProductName(productName);
            pro.setProductImage(productImage);
            pro.setProductDetail(productDescription);
            pro.setProductPrice(productPrice);
            pro.setProductDiscount(productDiscount);

            Sizes siz = new Sizes();

            int selectedSizeId = c.getInt(c.getColumnIndex("selectedSizeId"));
            String selectedSizeName = c.getString(c.getColumnIndex("selectedSizeName"));

            siz.setSizeId(selectedSizeId);
            siz.setSizeName(selectedSizeName);

            int qty = c.getInt(c.getColumnIndex("quantity"));

            CartItem cartItem = new CartItem(cartId, pro, siz, qty);

            cartItemsList.add(cartItem);
        }
        c.close();
        return cartItemsList;
    }
    public int updateItemIntoCart(int cartId, int quantity) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("quantity", quantity);
        return db.update("cart", cv, "cartId=" + cartId, null);
    }


    public int updateItemIntoCart(int cartId, Sizes selectedSize, int quantity) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("selectedSizeId", selectedSize.getSizeId());
        cv.put("selectedSizeName", selectedSize.getSizeName());
        cv.put("quantity", quantity);
        return db.update("cart", cv, "cartId=" + cartId, null);
    }

    public int deleteItemFromCart(int cartId) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("cart", "cartId=" + cartId, null);
    }

    public int clearCart() {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("cart", "1", null);
    }

    public int getTotalItemInCart() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM cart";
        Cursor c = db.rawQuery(query, null);
        int numberOfItems = c.getCount();
        c.close();
        return numberOfItems;
    }

    public int getTotalBill() {
        int totalBill = 0;
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT SUM(productPrice*quantity -productDiscount) as totalBill FROM cart";
        Cursor c = db.rawQuery(query, null);
        c.moveToNext();
        totalBill = c.getInt(c.getColumnIndex("totalBill"));
        c.close();
        return totalBill;


    }

    public boolean isAlreadyAdded(int productId, int sizeId) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM cart WHERE productId=" + productId + " AND selectedSizeId=" + sizeId;
        Cursor c = db.rawQuery(query, null);
        int count = c.getCount();
        c.close();
        return count > 0;
    }


}