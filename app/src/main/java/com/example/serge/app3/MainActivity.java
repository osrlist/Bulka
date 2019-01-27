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

    class OnClickUser implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            showUser();
        }
    }

    public static final String LOGTAG = "mLog";
    public static final String PREFEREMCES_USER = "PREFEREMCES_USER";

    public static final int REQUEST_CODE_USER = 1;

    public static DBHelper dbHelper;

    private int UserID;
    private TextView twUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        twUser = (TextView) findViewById(R.id.twUser);

        OnClickUser onClBtnCancel = new OnClickUser();
        twUser.setOnClickListener(onClBtnCancel);

        dbHelper = new DBHelper(this);

        // Начитаем значение, которое было в пролшлый раз
        LoadPreferences();
        loadUser();

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
        startActivityForResult(intent, REQUEST_CODE_USER);
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
        dbHelper.close();
    }

    private void loadUser(){
        if (UserID != 0) {
            SQLiteDatabase database = dbHelper.getWritableDatabase();

            Cursor cursor = database.rawQuery("Select FirstName" +
                    ", MiddleName" +
                    ", LastName" +
                    " from tUser" +
                    " where UserID = ?", new String[]{String.valueOf(UserID)});

            if (cursor.moveToFirst()) {
                int firstNameIndex = cursor.getColumnIndex("FirstName");
                int middleNameIndex = cursor.getColumnIndex("MiddleName");
                int LastNameIndex = cursor.getColumnIndex("LastName");

                twUser.setText(cursor.getString(firstNameIndex) + ' ' +
                        cursor.getString(middleNameIndex) + ' ' +
                        cursor.getString(LastNameIndex)
                );
                cursor.close();
                database.close();
            }
        }
    }
}
