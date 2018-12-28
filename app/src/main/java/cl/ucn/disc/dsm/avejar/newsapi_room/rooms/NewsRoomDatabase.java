package cl.ucn.disc.dsm.avejar.newsapi_room.rooms;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import cl.ucn.disc.dsm.avejar.newsapi_room.models.News;

// "exportSchema" --> Export the schema into a folder
@Database(entities = {News.class}, version = 1, exportSchema = false)
public abstract class NewsRoomDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();

    /**
     * NewsRoomDataBase instance
     */
    private static volatile NewsRoomDatabase INSTANCE;

    /**
     * Singleton method who prevent DB's multiples instances
     *
     * @param context - App's context
     * @return - NewsRoomDataBase instance
     */
    static NewsRoomDatabase getDatabase(final Context context) {

        // DB implement
        if (INSTANCE == null) {
            synchronized (NewsRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NewsRoomDatabase.class, "news_database")

                            // DB callback
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * RoomDataBase callback
     */
    private static Callback sRoomDatabaseCallback = new Callback(){

                /**
                 * DB's first call when its created
                 *
                 * @param db - DB
                 */
                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    /**
     *
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final NewsDao dao;

        PopulateDbAsync(NewsRoomDatabase db) {
            dao = db.newsDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            return null;
        }
    }
}
