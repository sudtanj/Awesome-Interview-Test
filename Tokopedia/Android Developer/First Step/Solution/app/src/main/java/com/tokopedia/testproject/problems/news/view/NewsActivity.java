package com.tokopedia.testproject.problems.news.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.pd.chocobar.ChocoBar;
import com.tokopedia.testproject.R;
import com.tokopedia.testproject.problems.news.model.Article;
import com.tokopedia.testproject.problems.news.presenter.NewsPresenter;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import ss.com.bannerslider.Slider;

public class NewsActivity extends AppCompatActivity implements NewsPresenter.View, MaterialSearchBar.OnSearchActionListener, PopupMenu.OnMenuItemClickListener {
    public static final String NEWS_FEED_KEY="newsFeed";
    public static final String HEAD_FEED_KEY="headFeed";
    private NewsPresenter newsPresenter;
    private NewsAdapter newsAdapter;
    private static KProgressHUD kProgressHUD;
    private  Slider slider;
    private boolean initializedEndScroll=false;
    private NewsPresenter headLinePresenter;
    private RecyclerView recyclerView;
    private int page=1;
    private MaterialSearchBar searchBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        newsPresenter = new NewsPresenter(this);
        newsAdapter = new NewsAdapter(null);
        recyclerView = findViewById(R.id.recyclerView);

        //initdb
        Paper.init(this);

        recyclerView.setAdapter(newsAdapter);
        newsPresenter.getEverything("android",page);
        kProgressHUD=KProgressHUD.create(NewsActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("Downloading data")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        Slider.init(new PicassoImageLoadingService(this));
        slider = findViewById(R.id.banner_slider1);

        headLinePresenter=new NewsPresenter(new NewsPresenter.View() {
            @Override
            public void onSuccessGetNews(List<Article> articleList) {
                slider.setAdapter(new MainSliderAdapter(articleList));
                slider.setInterval(5000);
                slider.setLoopSlides(true);
                slider.setMinimumHeight(slider.recyclerView.getHeight());
                Paper.book().write(HEAD_FEED_KEY,articleList);
            }

            @Override
            public void onErrorGetNews(Throwable throwable) {
                List<Article> offlineArticle= Paper.book().read(HEAD_FEED_KEY, new ArrayList<Article>());
                if(!offlineArticle.isEmpty()){
                    slider.setAdapter(new MainSliderAdapter(offlineArticle));
                    slider.setInterval(5000);
                    slider.setLoopSlides(true);
                    slider.setMinimumHeight(slider.recyclerView.getHeight());
                }
            }
        });
        headLinePresenter.getTopHeadlines("trump");

        searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        searchBar.setHint("Custom hint");
        searchBar.setSpeechMode(true);
        //enable searchbar callbacks
        searchBar.setOnSearchActionListener(this);

    }

    @Override
    public void onSuccessGetNews(final List<Article> articleList) {
        if(newsAdapter.getArticleList()==null) {
            newsAdapter.setArticleList(articleList);
        } else {
            newsAdapter.getArticleList().addAll(articleList);
        }
        Paper.book().write(NEWS_FEED_KEY, newsAdapter.getArticleList());
        newsAdapter.notifyDataSetChanged();
        if(newsAdapter.getItemCount()==0){
            ChocoBar.builder().setActivity(NewsActivity.this).setActionText("Try Again!")
                    .setActionClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            newsPresenter.getEverything("android",page);
                            kProgressHUD.show();
                        }

                    })
                    .setText("Sorry, i cannot find any data on the source (")
                    .setDuration(ChocoBar.LENGTH_INDEFINITE)
                    .build()
                    .show();
        }
        if(initializedEndScroll==false) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == articleList.size() - 1) {
                        //bottom of list!
                        kProgressHUD.show();
                        page++;
                        newsPresenter.getEverything("android", page);
                    }
                }
            });
            initializedEndScroll=true;
        }
        kProgressHUD.dismiss();
    }

    @Override
    public void onErrorGetNews(Throwable throwable) {
        kProgressHUD.dismiss();
        List<Article> offlineArticle= Paper.book().read(NEWS_FEED_KEY, new ArrayList<Article>());
        if(!offlineArticle.isEmpty()){
            newsAdapter.setArticleList(offlineArticle);
            newsAdapter.notifyDataSetChanged();
        } else {
            ChocoBar.builder().setActivity(NewsActivity.this).setActionText("Try Again!")
                    .setActionClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            newsPresenter.getEverything("android", page);
                            kProgressHUD.show();
                        }

                    })
                    .setDuration(5)
                    .setText("Failed to retreived data!")
                    .setDuration(ChocoBar.LENGTH_INDEFINITE)
                    .build()
                    .show();
            Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        newsPresenter.unsubscribe();
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {
        newsAdapter.setSearch(searchBar.getText());
        newsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        newsAdapter.setSearch(text.toString());
        newsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode){
            case MaterialSearchBar.BUTTON_BACK:
                this.newsAdapter.setSearch("");
                newsAdapter.notifyDataSetChanged();
                break;
            case MaterialSearchBar.BUTTON_NAVIGATION:

                break;
            case MaterialSearchBar.BUTTON_SPEECH:
                break;

                default:
                    break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }
}
