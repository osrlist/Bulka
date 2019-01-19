package com.example.serge.app3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String LOGTAG = "mLog";
    public static final String PREFEREMCES_USER = "PREFEREMCES_USER";

    private DBHelper dbHelper;
    private int UserID;
    private TextView twUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        twUser = (TextView) findViewById(R.id.twUser);

        View.OnClickListener onClBtnCancel = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUser();
            }
        };
        twUser.setOnClickListener(onClBtnCancel);

        dbHelper =  new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        // Начитаем значение, которое было в пролшлый раз
        LoadPreferences();
        loadUser();

/*        Cursor cursor = database.query("tProduct", null, null, null, null, null, null);

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
        */
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

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        dbHelper.close();
    }

    private void savePreferences(){
        SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(PREFEREMCES_USER, UserID);
        ed.commit();
        return;
    }

    private void LoadPreferences(){
        SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        UserID = sPref.getInt(PREFEREMCES_USER, 0);
        return;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        savePreferences();
    }

    private void loadUser(){
        if (UserID != 0) {
            SQLiteDatabase database = dbHelper.getWritableDatabase();


            Cursor cursor = database.rawQuery("Select FirstName" +
                    ", MidleName" +
                    ", LastName" +
                    " from tUser" +
                    " where UserID = ?", new String[]{String.valueOf(UserID)});

            if (cursor.moveToFirst()) {
                int firstNameIndex = cursor.getColumnIndex("FirstName");
                int midleNameIndex = cursor.getColumnIndex("MidleName");
                int LastNameIndex = cursor.getColumnIndex("LastName");

                twUser.setText(cursor.getString(firstNameIndex) + ' ' +
                        cursor.getString(midleNameIndex) + ' ' +
                        cursor.getString(LastNameIndex)
                );
                cursor.close();
                database.close();
            }
        }
    }
}
