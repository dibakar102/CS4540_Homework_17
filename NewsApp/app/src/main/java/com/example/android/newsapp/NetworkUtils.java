package com.example.android.newsapp;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Dibakar on 6/21/17.
 */

public class NetworkUtils {

    public static final String TAG =  NetworkUtils.class.getSimpleName();
    public static final String BASE_URL = "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest";

    final static String API_PARAM = "apikey";
    private static final String myAPI = "";

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

}
