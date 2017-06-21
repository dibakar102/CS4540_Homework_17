package com.example.android.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.android.newsapp.NetworkUtils;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView textView; // mNewsTextView
    private EditText editText; //mSearchBoxEditText
    private ProgressBar progressBar; // mLoadingIndicator

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.news_data);
        editText = (EditText) findViewById(R.id.search);
        progressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        loadNewsData();
    }
    private void loadNewsData() {
        new FetchNewsTask().execute();
    }
    public class FetchNewsTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            URL newsRequestURL = NetworkUtils.makeURL();

            try {
                return NetworkUtils.getResponseFromHttpUrl(newsRequestURL);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String newsData) {
            progressBar.setVisibility(View.INVISIBLE);

            if (newsData != null) {
                textView.setText(newsData);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemNumber = item.getItemId();
        if(itemNumber == R.id.search){
            Toast.makeText(this, "In Progress...", Toast.LENGTH_LONG).show();

        }
        return true;
    }
}
