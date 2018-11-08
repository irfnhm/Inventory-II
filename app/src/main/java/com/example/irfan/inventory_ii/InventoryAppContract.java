package com.example.irfan.inventory_ii;

import android.net.Uri;
import android.provider.BaseColumns;

public class InventoryAppContract {

    public static final String CONTENT_AUTHORITY = "com.apps.invappstage2final";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PRODUCT = "product";

    public static final class ProductEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PRODUCT).build();

        public static final String TABLE_NAME = "product";
        public static final String COLOUM_NAME = "name";
        public static final String COLOUM_QUANTITY = "quantity";
        public static final String COLOUM_PRICE = "price";
        public static final String COLOUM_SUPPLIER_NAME = "suppliername";
        public static final String COLOUM_SUPPLIER_PHONE = "sphone";
    }
}

