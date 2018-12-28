package cl.ucn.disc.dsm.avejar.newsapi_room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import java.util.List;

import cl.ucn.disc.dsm.avejar.newsapi_room.controllers.NewsController;
import cl.ucn.disc.dsm.avejar.newsapi_room.models.News;
import cl.ucn.disc.dsm.avejar.newsapi_room.rooms.NewsRepository;

public final class NewsViewModel extends AndroidViewModel {

    // https://developer.android.com/topic/libraries/architecture/livedata

    /**
     * LiveData Instance - Save the news list in cache
     */
    private LiveData<List<News>> getNews;

    /**
     * DB connect by the repository
     */
    private NewsRepository repository;

    /**
     * Get the list news from the repository
     *
     * @param application - App state
     */
    public NewsViewModel(Application application) {
        super(application);
        repository = new NewsRepository(application);
        getNews = repository.getAllNewsArticles();
    }

    /**
     * Getter method
     *
     * @return - The data of the news list
     */
    public LiveData<List<News>> getAllNews() {
        return getNews;
    }

    /**
     * Add a news-article to the DB - Repository call
     *
     * @param news - News-article
     */
    public void insertNews(News news) {
        repository.insert(news);
    }

    /**
     * NewsController service call - Gets the news-articles from the controller
     */
    public void downloadNewsArticles() {

        AsyncTask.execute(() -> {

            List<News> lNews = null;

            try {
                lNews = NewsController.getNewsArticles();

            } catch (Exception e) {
                e.printStackTrace();
            }

            // If exist data inside the list, this one are inserted on the DB
            if (lNews != null) {
                for (News article : lNews) {
                    insertNews(article);
                }
            }
        });
    }

    /**
     * Get the news-articles size
     *
     * @return - Number of news-articles
     */
    public int newsSize() {
        return repository.count();
    }

    /**
     * Load all news-articles inside the DB
     */
    public void loadAllArticles(){
        this.getNews = repository.getAllNewsArticles();
    }
}
