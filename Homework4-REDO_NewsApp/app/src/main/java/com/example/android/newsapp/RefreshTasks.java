package com.example.android.newsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.newsapp.data.DBHelper;
import com.example.android.newsapp.data.DataBaseUtils;
import com.example.android.newsapp.data.NewsItem;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Dibakar on 7/28/17.
 */

public class RefreshTasks {
    public static final String ACTION_REFRESH = "refresh";
    public static void refreshArticles(Context context) {
        ArrayList<NewsItem> result = null;
        URL url = NetworkUtils.makeURL();

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {
            DataBaseUtils.deleteAll(db);
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            result = NetworkUtils.parseJSON(json);
            DataBaseUtils.bulkInsert(db, result);

        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        db.close();
    }
}
