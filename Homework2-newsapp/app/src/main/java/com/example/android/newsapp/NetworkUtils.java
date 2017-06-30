package com.example.android.newsapp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Dibakar on 6/21/17.
 */

public class NetworkUtils {

    public static final String TAG =  NetworkUtils.class.getSimpleName();
    public static final String BASE_URL = "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest";

    final static String API_PARAM = "apikey";
    private static final String myAPI = "e75e201563564ec790664e0d5639fa52";

    public static URL makeURL(){
        Uri uri = Uri.parse(BASE_URL).buildUpon().appendQueryParameter(API_PARAM, myAPI).build();
        URL url = null;
        try{

            url = new URL(uri.toString());
            Log.d(TAG, "Url : " + url);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
    public static ArrayList<NewsItem> parseJSON(String json) throws JSONException{
        ArrayList<NewsItem> string = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray items = main.getJSONArray("articles");
        String source = main.getString("source");

        for(int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            String author = item.getString("author");
            String title = item.getString("title");
            String description = item.getString("description");
            String url = item.getString("url");
            String urlToImage = item.getString("urlToImage");
            String publishedAt = item.getString("publishedAt");
            NewsItem newsItem = new NewsItem(source, author, title, description, url, urlToImage, publishedAt);
            string.add(newsItem);
        }

        return string;
    }

}
