package com.matthewramos.newsapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import android.support.annotation.Nullable;
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
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewsRecyclerViewAdapter.NewsClick {

    private TextView mUrlDisplayTextView;
    private RecyclerView mRecylcerView;
    private NewsRecyclerViewAdapter mNewsRecyclerViewAdapter;
    private ArrayList<NewsItem> newList;
    private NewsItemViewModel mNewsItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);

        mRecylcerView = (RecyclerView)findViewById(R.id.news_recyclerview);
        mNewsItemViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);
        mNewsItemViewModel.getAllNewsItems().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable List<NewsItem> newsItems) {
                mNewsRecyclerViewAdapter = new NewsRecyclerViewAdapter(new ArrayList<NewsItem>(newsItems), MainActivity.this);
                mRecylcerView.setAdapter(mNewsRecyclerViewAdapter);
                mRecylcerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                mRecylcerView.setHasFixedSize(true);
                newList = NewsItemRepository.SyncNewsTask.sNewItems;
            }
        });
        NewsItemReminderUtils.scheduleChargingReminder(this);
    }

    @Override
    public void onNewsClick(int i) {
        String link = newList.get(i).getUrl();

        Intent browseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browseIntent);
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
            mNewsItemViewModel.syncNews();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
