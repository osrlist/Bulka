package com.example.serge.app3;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.example.serge.app3.MainActivity.LOGTAG;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Dictionary.db";
    private Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    private ArrayList<String> readFromFile(String FileName){
         /*
          * Читаем из файла FileName
          *  и рабиваем на отдельные Query
          * */
        Log.d(LOGTAG, "readFromFile:");
        BufferedReader bufferedReader;
        String query;
        String line;
        String lineTrim;
        ArrayList<String> oneQuery = new ArrayList<>();

        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open(FileName);
            bufferedReader = new BufferedReader(new InputStreamReader(is));
            query = "";
            while ((line = bufferedReader.readLine()) != null){
                if (line.length() !=0 & !line.startsWith("--")) {
                    // нужно разбить сплошной SQL на отдельные Query
                    lineTrim = line.trim();
                    if (lineTrim.charAt(lineTrim.length() - 1) == ';'){
                        query = "\n" + query + line;
                        oneQuery.add(query);
                        Log.d(LOGTAG, query);
                        query = "";
                    } else {
                        query = "\n" + query + line;
                    }
                }
            }
            bufferedReader.close();
            is.close();
        } catch (IOException io) {
            io.printStackTrace();
            Log.d(LOGTAG, "Ошибка в чтении файла " + FileName);
        }
        Log.d(LOGTAG, "Завершили работу с  " + FileName);
        return oneQuery;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ArrayList<String> queries;
        queries = readFromFile("DB1/CreateDB.sql");
        try {
            for (String query: queries){
                Log.d(LOGTAG, query);
                db.execSQL(query);
            }
        } catch (Exception io) {
            io.printStackTrace();
            Log.d(LOGTAG, "Error Exec!" + io.getMessage());
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
        Log.d(LOGTAG, "after Upgrade!");
    }

}
