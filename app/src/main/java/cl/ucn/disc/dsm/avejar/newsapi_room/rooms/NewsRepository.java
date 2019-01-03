package cl.ucn.disc.dsm.avejar.newsapi_room.rooms;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import java.util.List;

import cl.ucn.disc.dsm.avejar.newsapi_room.models.News;

public class NewsRepository {

    private NewsDao newsDao;
    private LiveData<List<News>> allArticles;

    // Constructor
    public NewsRepository(Application application) {
        NewsRoomDatabase db = NewsRoomDatabase.getDatabase(application);
        newsDao = db.newsDao();
        allArticles = newsDao.getAllNews();
    }

    /**
     * Observer - Executes all the query in on separate threads
     *
     * @return - Changed data notified
     */
    public LiveData<List<News>> getAllNewsArticles() {
        return allArticles;
    }

    /**
     * Insert method called from a different threads
     *
     * @param news - Insert execute
     */
    public void insert (News news) {
        new insertAsyncTask(newsDao).execute(news);
    }

    // AsyncTask
    private static class insertAsyncTask extends AsyncTask<News, Void, Void> {

        private NewsDao mAsyncTaskDao;

        insertAsyncTask(NewsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final News... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
