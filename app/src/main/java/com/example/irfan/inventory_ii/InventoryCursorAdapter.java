package com.example.irfan.inventory_ii;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class InventoryCursorAdapter extends CursorAdapter {


    public InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.products_list_item, viewGroup, false);

    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView mProductName = view.findViewById(R.id.tv_productName);
        TextView mProductPrice = view.findViewById(R.id.tv_productPrice);
        TextView mProductQuantity = view.findViewById(R.id.tv_productQuantity);
        Button mSaleButton = view.findViewById(R.id.saleBtn);
        int productNameIndex = cursor.getColumnIndex(InventoryAppContract.ProductEntry.COLOUM_NAME);
        int productQuantityIndex = cursor.getColumnIndex(InventoryAppContract.ProductEntry.COLOUM_QUANTITY);
        int productPriceIndex = cursor.getColumnIndex(InventoryAppContract.ProductEntry.COLOUM_PRICE);
        int idIndex = cursor.getColumnIndex(InventoryAppContract.ProductEntry._ID);
        String productnameString = cursor.getString(productNameIndex);
        final int quantityInteger = cursor.getInt(productQuantityIndex);
        double priceDouble = cursor.getDouble(productPriceIndex);
        final long id = cursor.getLong(idIndex);
        mSaleButton.setFocusable(false);
        if (quantityInteger >= 0) {
            mSaleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                   public void onClick(View view) {
                       ContentValues values = new ContentValues();
                       if (quantityInteger == 0) {
                           values.put(InventoryAppContract.ProductEntry.COLOUM_QUANTITY, quantityInteger);
                       } else {
                           values.put(InventoryAppContract.ProductEntry.COLOUM_QUANTITY, quantityInteger - 1);
                       }
                       Uri uri = ContentUris.withAppendedId(InventoryAppContract.ProductEntry.CONTENT_URI, id);
                       context.getContentResolver().update(
                               uri,
                               values,
                               InventoryAppContract.ProductEntry._ID + "=?",
                               new String[]{String.valueOf(ContentUris.parseId(uri))});
                   }

               }
    );

            mProductName.setText(productnameString);
            mProductQuantity.setText(String.format("Quantity: %s", String.valueOf(quantityInteger)));
            mProductPrice.setText(String.format("Price: $ %s", String.valueOf(priceDouble)));

        } else {

            Toast.makeText(context, "Quantity is Zero", Toast.LENGTH_SHORT).show();

        }
    }
}
