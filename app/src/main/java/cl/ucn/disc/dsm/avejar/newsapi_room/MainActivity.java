package cl.ucn.disc.dsm.avejar.newsapi_room;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import java.util.List;

import cl.ucn.disc.dsm.avejar.newsapi_room.activities.WebActivity;
import cl.ucn.disc.dsm.avejar.newsapi_room.controllers.NewsController;
import cl.ucn.disc.dsm.avejar.newsapi_room.models.News;
import cl.ucn.disc.dsm.avejar.newsapi_room.adapters.NewsAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainActivity extends AppCompatActivity {

    /**
     * Adapter Class
     */
    private NewsAdapter adapter;

    /**
     * ViewModel Class
     */
    NewsViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Adapter
        adapter = new NewsAdapter(this);

        // RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rv_news);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ViewModel Implement
        model = ViewModelProviders
                .of(this)
                .get(NewsViewModel.class);

        // LiveData Observer
        model.getAllNews().observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(@Nullable List<News> news) {

                // Update the cached copy of the words in the adapter.
                adapter.setArticles(news);

                if (adapter.getItemCount() != 0 ){
                    Toast.makeText(MainActivity.this,"Loading news ...", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Download news-articles from the API
        model.downloadNewsArticles();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar_menu, menu);

        // Search item
        MenuItem searchItem = menu.findItem(R.id.search_item);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        // Bar bar_menu text searching listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                AsyncTask.execute(() -> {

                    List<News> lNews = null;

                    try {
                        lNews = NewsController.getNewsArticles_Everything(query);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    getList(lNews);
                });

                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // Close bar bar_menu listener
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                adapter.setArticles(model.getAllNews().getValue());
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Show size
     *
     * @param lNews
     */
    public void getList(final List<News> lNews){

        runOnUiThread(() -> {
            adapter.setArticles(lNews);
        });
    }

    /**
     * Enter to the News Api web site
     *
     * @param view - View activity
     */
    public void EnterApiWebSite(View view){
        Intent nextAct = new Intent(this, WebActivity.class);
        startActivity(nextAct);
    }
}
