package com.example.hamza.inventoryappstage2;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.hamza.inventoryappstage2.Data.ProductContract.ProductEntry;

public class ProductCursorAdaptor extends CursorAdapter {

    public ProductCursorAdaptor(Context context, Cursor c) {

        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }


    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        final TextView nameTextView = view.findViewById(R.id.name);
        final TextView priceTextView = view.findViewById(R.id.price);
        final TextView QuantityTextView = view.findViewById(R.id.quantity);

        int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
        int QuantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);

        String ProductName = cursor.getString(nameColumnIndex);
        String ProductPrice = cursor.getString(priceColumnIndex);
        String ProductQuantity = cursor.getString(QuantityColumnIndex);
        final String id = cursor.getString(cursor.getColumnIndex(ProductEntry._ID));
        final Uri uri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, Long.parseLong(id));

        nameTextView.setText(ProductName);
        priceTextView.setText(ProductPrice);
        QuantityTextView.setText(ProductQuantity);

        Button sale = view.findViewById(R.id.sale);
        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int quantity = Integer.parseInt(QuantityTextView.getText().toString());
                --quantity;
                if (quantity <= 0) {
                    quantity = 0;
                }

                QuantityTextView.setText(String.valueOf(quantity));


                if (quantity >= 0) {
                    ContentResolver resolver = context.getContentResolver();
                    ContentValues values = new ContentValues();
                    values.put(ProductEntry.COLUMN_PRODUCT_NAME, nameTextView.getText().toString());
                    values.put(ProductEntry.COLUMN_PRODUCT_PRICE, priceTextView.getText().toString());
                    values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, quantity);

                }

            }
        });
    }

}
