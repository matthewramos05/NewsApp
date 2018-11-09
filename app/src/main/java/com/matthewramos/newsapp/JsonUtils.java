package com.matthewramos.newsapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {
    public static ArrayList<NewsItem> parseNews(String JSONString){
       ArrayList<NewsItem> newsList = new ArrayList<>();
        try {
            JSONObject newsJSONObject = new JSONObject(JSONString);
            JSONArray articles = newsJSONObject.getJSONArray("articles");

            for(int i = 0; i < articles.length(); i++){
                JSONObject item = articles.getJSONObject(i);
                newsList.add(new NewsItem("Title "+item.getString("title"),
                        item.getString("description"), item.getString("publishedAt"),
                        item.getString("url")));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return newsList;
    }
}

