package com.matthewramos.newsapp;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    final static String NEWSAPP_BASE_URL =
            "https://newsapi.org/v1/articles";

    final static String PARAM_QUERY = "source";
    final static String PARAM_QUERY1 = "sortBy";
    final static String PARAM_QUERY2 = "apiKey";


    public static URL buildUrl(String newsAppSearchQuery) {
        Uri builtUri = Uri.parse(NEWSAPP_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, "the-next-web")
                .appendQueryParameter(PARAM_QUERY1, "latest")
                .appendQueryParameter(PARAM_QUERY2, "58b47b22fc784c00851957c447596321")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
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