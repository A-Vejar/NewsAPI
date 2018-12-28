package cl.ucn.disc.dsm.avejar.newsapi_room.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import cl.ucn.disc.dsm.avejar.newsapi_room.models.News;
import cl.ucn.disc.dsm.avejar.newsapi_room.models.NewsAPI;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public final class NewsController {

    /**
     * News service how do the connection with the news API - "NewsAPI"
     */
    public interface NewsService {

        /**
         * Get the news from the News Api web site
         * @return - Top science news from US
         */
        @Headers({"X-Api-Key: ab97f667d6dd4d73a7388bcdc269170c"})
        @GET("top-headlines?country=us&category=science")
        Call<NewsAPI> getTopHeadlines();

        /**
         * Get the news from the News Api web site
         *
         * @param query - Get a specific news by a search query
         * @return - All news from US
         */
        @Headers({"X-Api-Key: ab97f667d6dd4d73a7388bcdc269170c"})
        @GET("everything?language=en")
        Call<NewsAPI> getEverything(@Query("q") String query);
    }

    /**
     * GSON object how convert the publish date from "String" to "Date"
     */
    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();

    /**
     * Http interceptor
     */
    private static final HttpLoggingInterceptor interceptor;

    static {
        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
    }

    /**
     * Http client
     */
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptor).build();

    /**
     * Retrofit instance
     */
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    /**
     * Build this service in the interface through the retrofit
     */
    private static final NewsService service = retrofit.create(NewsService.class);

    /**
     * 'Top' news-article return method
     *
     * @return - News list
     * @throws IOException - Exception
     */
    public static List<News> getNewsArticles() throws IOException {
        Call<NewsAPI> callNews = service.getTopHeadlines();
        NewsAPI news = callNews.execute().body();
        return news.getArticles();
    }

    /**
     * News-articles return method
     *
     * @param query - Query search
     * @return - A searching news list
     * @throws IOException - Exception
     */
    public static List<News> getNewsArticles_Everything(String query) throws IOException {
        Call<NewsAPI> callNews = service.getEverything(query);
        NewsAPI news = callNews.execute().body();
        return news.getArticles();
    }

}
