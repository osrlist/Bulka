package com.example.serge.app3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static final String LOGTAG = "mLog";
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper =  new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query("tProduct", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("ProductID");
            int ProductGroupIDIndex = cursor.getColumnIndex("ProductGroupID");
            int NameIndex = cursor.getColumnIndex("Name");
            do {
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                        ", ProductGroupID = " + cursor.getInt(ProductGroupIDIndex) +
                        ", Name = " + cursor.getString(NameIndex));
            } while (cursor.moveToNext());
        } else
            Log.d("mLog","0 rows");

        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean retVal;
        retVal = super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return retVal;
    }

    private void showUser(){
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.idUser:
                showUser();
                break;
        }

        return super.onOptionsItemSelected(item);

    }
}
