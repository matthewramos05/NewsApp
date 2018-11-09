package com.matthewramos.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsRecyclerViewAdapter.NewsClick {

    private TextView mUrlDisplayTextView;
    private RecyclerView mRecylcerView;
    private NewsRecyclerViewAdapter mNewsRecyclerViewAdapter;
    private ArrayList<NewsItem> newList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);

        mRecylcerView = (RecyclerView)findViewById(R.id.news_recyclerview);
        mRecylcerView.setLayoutManager(new LinearLayoutManager(this));
        mRecylcerView.setHasFixedSize(true);

    }

    private void newsAppSearchQuery() {
        //String newsAppQuery = mSearchBoxEditText.getText().toString();
        URL newsAppSearchUrl = NetworkUtils.buildUrl("the-next-web");
        mUrlDisplayTextView.setText(newsAppSearchUrl.toString());
        new NewsQueryTask().execute(newsAppSearchUrl);
    }

    @Override
    public void onNewsClick(int i) {
        String link = newList.get(i).getUrl();

        Intent browseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browseIntent);
    }

    public class NewsQueryTask extends AsyncTask<URL, Void, String> {



        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String newsAppSearchResults = null;
            try {
                newsAppSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newsAppSearchResults;
        }
         @Override
         protected void onPostExecute(String newsAppSearchResults) {
            if (newsAppSearchResults != null && !newsAppSearchResults.equals("")) {
                newList = JsonUtils.parseNews(newsAppSearchResults);
                mNewsRecyclerViewAdapter = new NewsRecyclerViewAdapter(newList, MainActivity.this);
                mRecylcerView.setAdapter(mNewsRecyclerViewAdapter);
            }
     }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.get_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            newsAppSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
