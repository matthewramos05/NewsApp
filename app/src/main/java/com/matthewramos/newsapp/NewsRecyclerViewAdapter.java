package com.matthewramos.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsItemViewHolder> {

    private ArrayList<NewsItem> mNewsList;

    final private NewsClick mOnClickListener;
    public NewsRecyclerViewAdapter(ArrayList<NewsItem> newsList, NewsClick onClickListener) {
        mNewsList = newsList;
        mOnClickListener = onClickListener;
    }

    public interface NewsClick{
        void onNewsClick(int i);
    }
    @NonNull
    @Override
    public NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.item, parent, shouldAttachToParentImmediately);
        NewsItemViewHolder viewHolder = new NewsItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsItemViewHolder newsItemViewHolder, int position) {
        newsItemViewHolder.title.setText(mNewsList.get(position).getTitle());
        newsItemViewHolder.description.setText(mNewsList.get(position).getPublishedAt() + " . " +
                mNewsList.get(position).getDescription());


        String url = mNewsList.get(position).getImageUrl();
        if (url != null) {
            Picasso.get().load(url).into(newsItemViewHolder.mImageView);
        }
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }


    public class NewsItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView title;
        private TextView description;
        private ImageView mImageView;

        public NewsItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_imageUrl);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickPos = getAdapterPosition();
            mOnClickListener.onNewsClick(clickPos);
        }
    }
}
