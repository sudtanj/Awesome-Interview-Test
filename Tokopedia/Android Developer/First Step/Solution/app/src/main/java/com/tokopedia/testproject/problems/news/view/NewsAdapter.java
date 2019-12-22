package com.tokopedia.testproject.problems.news.view;

import android.icu.text.DateFormatSymbols;
import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tokopedia.testproject.R;
import com.tokopedia.testproject.problems.news.model.Article;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<Article> articleList = null;
    private String search="";
    private List<Integer> foundPosition=new ArrayList<>();
    NewsAdapter(List<Article> articleList) {
        setArticleList(articleList);
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    void setArticleList(final List<Article> articleList) {
        if (articleList == null) {
            this.articleList = new ArrayList<>();
        } else {
            this.articleList = articleList;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return articleList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder newsViewHolder, int i) {
        if(newsViewHolder instanceof  NewsViewHolder) {
            if(search.isEmpty())
                ((NewsViewHolder)newsViewHolder).bind(articleList.get(i));
            else
                ((NewsViewHolder)newsViewHolder).bind(articleList.get(foundPosition.get(i)));
        } else if(newsViewHolder instanceof DateViewHolder){
            ((DateViewHolder)newsViewHolder).bind(articleList.get(i).getPublishedAt());
        }
    }

    @Override
    public int getItemCount() {
        Collections.sort(this.articleList, new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
                return o2.getPublishedAt().compareTo(o1.getPublishedAt());
            }
        });
        if(search.isEmpty())
            return articleList.size();
        else
            return foundPosition.size();
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
        foundPosition.clear();
        if(!search.isEmpty()){
            int i=0;
            for(Article article:articleList){
                if(article.getTitle().contains(search)){
                    foundPosition.add(i);
                }
                i++;
            }
        }
    }

    class DateViewHolder extends RecyclerView.ViewHolder {
        public DateViewHolder(@NonNull View itemView) {
            super(itemView);

        }
        public void bind(String date){
            this.date.setText(date);
        }

        TextView date;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvTitle;
        TextView tvDescription;
        TextView tvDate;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDate = itemView.findViewById(R.id.tvDate);
        }

        void bind(Article article) {
            Glide.with(itemView).load(article.getUrlToImage()).into(imageView);
            tvTitle.setText(article.getTitle());
            tvDescription.setText(article.getDescription());
            String[] date = article.getPublishedAt().split("T")[0].split("-");
            tvDate.setText(date[2] + " " + getMonth(Integer.parseInt(date[1])) + " " + date[0]);
        }

        public String getMonth(int month) {
            return new DateFormatSymbols().getMonths()[month - 1];
        }
    }
}
