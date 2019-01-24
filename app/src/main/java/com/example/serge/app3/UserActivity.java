package com.example.serge.app3;

import java.util.concurrent.TimeUnit;

import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;


public class UserActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

    private DBHelper dbHelper;
    private ListView lvData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SimpleCursorAdapter scAdapter;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        dbHelper =  new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        // формируем столбцы сопоставления

        String[] from = new String[] { "FirstName", "MiddleName", "LastName" };
        int[] to = new int[] { R.id.tvItemFirstName, R.id.tvItemMiddleName, R.id.tvItemLastName };

        // создааем адаптер и настраиваем список
        scAdapter = new SimpleCursorAdapter(this, R.layout.item_user, null, from, to, 0);
        lvData = (ListView) findViewById(R.id.lvUser);
        lvData.setAdapter(scAdapter);
        // создаем лоадер для чтения данных
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new MyCursorLoader(this );
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new MyCursorLoader(this, db);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        scAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
    static class MyCursorLoader extends CursorLoader {

        private DBHelper dbHelper;

        public MyCursorLoader(Context context, DBHelper dbHelper) {
            super(context);
            this.dbHelper = dbHelper;
        }
        @Override
        public Cursor loadInBackground() {
            Cursor cursor;
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            cursor = database.rawQuery("Select UserID as _id" +
                    ", FirstName" +
                    ", MiddleName" +
                    ", LastName" +
                    " from tUser" +
                    " order by LastName, FirstName", null);

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return cursor;
        }

    }
}
