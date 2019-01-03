package cl.ucn.disc.dsm.avejar.newsapi_room.rooms;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import java.util.List;

import cl.ucn.disc.dsm.avejar.newsapi_room.models.News;

@Dao
public interface NewsDao {

    /**
     * DB Insert query - As the table has more that one column we use "OnConflictStrategy" to replace it
     *
     * @param news - News row to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(News news);

    /**
     * DB Delete query
     */
    @Query("DELETE FROM news_table")
    void deleteAll();

    /**
     * Gets all the news-articles from the DB
     *
     * @return - All the data inside the list to be update, using LiveData (If this changes)
     */
    @Query("SELECT * FROM news_table ORDER BY publishedAt DESC")
    LiveData<List<News>> getAllNews();
}
