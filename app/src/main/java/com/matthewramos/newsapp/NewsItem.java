package com.matthewramos.newsapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.widget.ImageView;

@Entity(tableName = "news_item")
public class NewsItem {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String publishedAt;
    private String url;
    private String imageUrl;

    @Ignore
    public NewsItem(String title, String description, String publishedAt, String url, String imageUrl) {

        this.title = title;
        this.description = description;
        this.publishedAt = publishedAt;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public NewsItem(int id, String title, String description, String publishedAt, String url, String imageUrl){

        this.id = id;
        this.title = title;
        this.description = description;
        this.publishedAt = publishedAt;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
