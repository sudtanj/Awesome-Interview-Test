package com.tokopedia.testproject.problems.news.view;

import com.tokopedia.testproject.problems.news.model.Article;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter extends ss.com.bannerslider.adapters.SliderAdapter {

    private List<Article> articleList=null;
    public MainSliderAdapter(List<Article> articleList){
        this.articleList=articleList;
    }
    @Override
    public int getItemCount() {
        if(articleList.size()<5) {
            return articleList.size();
        }
        return 5;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
        viewHolder.bindImageSlide(articleList.get(position).getUrlToImage());
        /**
        switch (position) {
            case 0:
                viewHolder.bindImageSlide("https://assets.materialup.com/uploads/dcc07ea4-845a-463b-b5f0-4696574da5ed/preview.jpg");
                break;
            case 1:
                viewHolder.bindImageSlide("https://assets.materialup.com/uploads/20ded50d-cc85-4e72-9ce3-452671cf7a6d/preview.jpg");
                break;
            case 2:
                viewHolder.bindImageSlide("https://assets.materialup.com/uploads/76d63bbc-54a1-450a-a462-d90056be881b/preview.png");
                break;
        }*/
    }
}