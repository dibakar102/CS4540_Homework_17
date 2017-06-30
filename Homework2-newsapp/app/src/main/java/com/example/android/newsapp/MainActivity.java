package com.example.android.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private RecyclerView recyclerView;
    private RecycleAdapter recycleAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_news);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        editText = (EditText) findViewById(R.id.search);
        progressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        loadNewsData();
    }
    private void loadNewsData() {
        new NewsTask("").execute();
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
            String s = editText.getText().toString();
            NewsTask task = new NewsTask(s);
            task.execute();
        }
        return true;
    }


    class NewsTask extends AsyncTask<URL, Void, ArrayList<NewsItem>> implements RecycleAdapter.ItemClickListener{
        String query;
        ArrayList<NewsItem> data;
        NewsTask(String s) {
            query = s;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<NewsItem> doInBackground(URL... params) {
            ArrayList<NewsItem> result = null;
            URL newsRequestURL = NetworkUtils.makeURL();

            try {
                String json = NetworkUtils.getResponseFromHttpUrl(newsRequestURL);
                result = NetworkUtils.parseJSON(json);
            } catch (IOException e) {
                e.printStackTrace();

            }catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }



        @Override
        protected void onPostExecute(final ArrayList<NewsItem> newsData) {
            this.data = newsData;
            super.onPostExecute(data);
            progressBar.setVisibility(View.INVISIBLE);

            if (newsData != null) {
                RecycleAdapter adapter = new RecycleAdapter(data,this);
                recyclerView.setAdapter(adapter);
            }
        }
        @Override
        public void onListItemClick(int clickedItemIndex) {
            openWebPage(data.get(clickedItemIndex).getUrl());
        }

        public void openWebPage(String url) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }


    }

}
