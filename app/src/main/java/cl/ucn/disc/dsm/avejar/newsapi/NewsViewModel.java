package cl.ucn.disc.dsm.avejar.newsapi;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import java.io.IOException;
import java.util.List;

import cl.ucn.disc.dsm.avejar.newsapi.controllers.NewsController;
import cl.ucn.disc.dsm.avejar.newsapi.models.News;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class NewsViewModel extends AndroidViewModel {

    // https://developer.android.com/topic/libraries/architecture/livedata

    /**
     * MutableLiveData Instance
     */
    private MutableLiveData<List<News>> newsArticles;

    // Constructor
    public NewsViewModel(Application application) {
        super(application);
    }

    /**
     * Holds the list data
     *
     * @return - The data of the news list
     */
    public LiveData<List<News>> getNews() {
        if (newsArticles == null) {
            newsArticles = new MutableLiveData<>();
            loadNewsArticles();
        }
        return newsArticles;
    }

    /**
     * Loads the news to MainActivity
     */
    public void loadNewsArticles() {

        // Gets the news articles from "NewsController"
        AsyncTask.execute(() -> {

            log.debug("LOADING ...");

            List<News> lNews = null;

            try {
                lNews = NewsController.getNewsArticles();

            } catch (Exception e) {
                //log.debug("ERROR");
            }

            // Posts task on the main thread to set the given value
            this.newsArticles.postValue(lNews);
        });
    }

    /**
     * Loads specific news by a search query to MainActivity
     *
     * @param query - Search query
     */
    public void loadNewsArticles(String query) {

        // Gets the news articles from "NewsController"
        AsyncTask.execute(() -> {

            log.debug("LOADING ...");

            List<News> lNews = null;

            try {
                lNews = NewsController.getNewsArticles_Everything(query);

            } catch (IOException e) {
                //e.printStackTrace();
            }

            // Posts task on the main thread to set the given value
            this.newsArticles.postValue(lNews);
        });
    }
}
