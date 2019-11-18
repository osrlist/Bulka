package com.example.serge.app3.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.serge.app3.R;
import com.example.serge.app3.shared.KeyExtra;

import static com.example.serge.app3.activity.MainActivity.dbHelper;

public class UserActivity extends AppCompatActivity {

    private boolean editMode;

    private ListView lvData;
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SimpleCursorAdapter scAdapter;

        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        editMode = intent.getBooleanExtra( KeyExtra.EDIT_MODE.getKey(), false );

        setContentView(R.layout.activity_user);



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


}
