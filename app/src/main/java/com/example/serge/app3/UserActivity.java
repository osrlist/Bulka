package com.example.serge.app3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class UserActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private ListView lvData;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SimpleCursorAdapter scAdapter;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        dbHelper =  new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        cursor = database.rawQuery("Select UserID as _id" +
                ", FirstName" +
                ", MiddleName" +
                ", LastName" +
                " from tUser" +
                " order by LastName, FirstName", null);
        startManagingCursor(cursor);
        // формируем столбцы сопоставления
        String[] from = new String[] { "FirstName", "MiddleName", "LastName" };
        int[] to = new int[] { R.id.tvItemFirstName, R.id.tvItemMiddleName, R.id.tvItemLastName };

        // создааем адаптер и настраиваем список
        scAdapter = new SimpleCursorAdapter(this, R.layout.item_user, cursor, from, to);
        lvData = (ListView) findViewById(R.id.lvUser);
        lvData.setAdapter(scAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        dbHelper.close();
    }
}
