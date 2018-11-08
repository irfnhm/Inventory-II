package com.example.irfan.inventory_ii;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private InventoryCursorAdapter productCursorAdapter;
    private static final int PRODUCT_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fff);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });
        ListView productListView = findViewById(R.id.list);
        productListView.setEmptyView(findViewById(R.id.empty_view));
        productCursorAdapter = new InventoryCursorAdapter(this, null);
        productListView.setAdapter(productCursorAdapter);
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.setData(ContentUris.withAppendedId(InventoryAppContract.ProductEntry.CONTENT_URI, id));
                startActivity(intent);
            }
        });
        productListView.setItemsCanFocus(true);
        getLoaderManager().initLoader(PRODUCT_LOADER, null, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all:
                //////////////
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Delete All Products !?");
                //alert.setIcon(R.drawable.cart);
                alert.setMessage("You are about to delete all inventory products, are you sure you want to delete them ?");
                alert.setPositiveButton("Delete All", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteAllProducts();
                    }
                });
                alert.setNeutralButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                alert.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllProducts() {
        int numberOfRowsDeleted = getContentResolver().delete(InventoryAppContract.ProductEntry.CONTENT_URI, null, null);
        Log.v("MainActivity", numberOfRowsDeleted + " rows deleted from product database");
    }

    ///////////////////// Loader Methods ////////////////////////////////
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                InventoryAppContract.ProductEntry._ID,
                InventoryAppContract.ProductEntry.COLOUM_NAME,
                InventoryAppContract.ProductEntry.COLOUM_PRICE,
                InventoryAppContract.ProductEntry.COLOUM_QUANTITY
        };
        return new CursorLoader(this,
                InventoryAppContract.ProductEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        productCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        productCursorAdapter.swapCursor(null);
    }
}
