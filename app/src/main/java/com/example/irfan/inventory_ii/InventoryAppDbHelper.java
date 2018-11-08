package com.example.irfan.inventory_ii;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InventoryAppDbHelper extends SQLiteOpenHelper {
    // The database name
    private static final String DATABASE_NAME = "inventory.db";

    //  The database version
    private static final int DATABASE_VERSION = 1;

    public InventoryAppDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_INVENTORY_TABLE = "CREATE TABLE " + InventoryAppContract.ProductEntry.TABLE_NAME + " (" +
                InventoryAppContract.ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                InventoryAppContract.ProductEntry.COLOUM_NAME + " TEXT NOT NULL, " +
                InventoryAppContract.ProductEntry.COLOUM_QUANTITY + " INTEGER NOT NULL DEFAULT 0, " +
                InventoryAppContract.ProductEntry.COLOUM_PRICE + " REAL NOT NULL DEFAULT 0, " +
                InventoryAppContract.ProductEntry.COLOUM_SUPPLIER_NAME + " TEXT NOT NULL, " +
                InventoryAppContract.ProductEntry.COLOUM_SUPPLIER_PHONE + " TEXT NOT NULL " +
                "); ";
        // execute the query
        sqLiteDatabase.execSQL(SQL_CREATE_INVENTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + InventoryAppContract.ProductEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

