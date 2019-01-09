package com.example.serge.app3;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.example.serge.app3.MainActivity.LOGTAG;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Dictionary";
    private Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    private  String readFromFile(String FileName){
        BufferedReader bufferedReader;
        String query;
        String line;

        AssetManager assetManager = context.getAssets();
        query = "";
        try {
            InputStream is = assetManager.open(FileName);
            bufferedReader = new BufferedReader(new InputStreamReader(is));
            while ((line = bufferedReader.readLine()) != null){
                Log.d(LOGTAG, line);
                if (!line.startsWith("--")) {
                    query = "\n" + query + line;
                }
            }
            bufferedReader.close();
            is.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
        Log.d(LOGTAG, query);
        return query;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(readFromFile("DB1/CreateDB.sql"));
        Log.d(LOGTAG, "after exec!");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
