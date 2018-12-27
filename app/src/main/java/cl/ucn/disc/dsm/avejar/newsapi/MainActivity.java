package cl.ucn.disc.dsm.avejar.newsapi;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import cl.ucn.disc.dsm.avejar.newsapi.activities.WebActivity;
import cl.ucn.disc.dsm.avejar.newsapi.adapters.NewsAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainActivity extends AppCompatActivity {

    /**
     * Adapter Class
     */
    private NewsAdapter adapter;

    /**
     * ListView Object
     */
    private ListView listView;

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

        // ListView
        this.listView = findViewById(R.id.lv_news);

        // ViewModel Implement
        model = ViewModelProviders
                .of(this)
                .get(NewsViewModel.class);

        // LiveData Observer
        model.getNews().observe(this, newsArticles -> {

            this.adapter = new NewsAdapter(this);

            if (newsArticles != null) {
                this.adapter.loadListNews(newsArticles);
            }

            listView.setAdapter(adapter);
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // When its pressed opens the url in a new activity
                NextActURL(position);
            }
        });
    }

    public void NextActURL(int position){

        // Get the url from News class
        String getUrl = adapter.getItem(position).getUrl();

        // Change to the next activity with their specific url
        Intent nextAct = new Intent(this, WebActivity.class);

        // Save the url
        nextAct.putExtra("new_url", getUrl);
        startActivity(nextAct);
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

                // Query search
                model.loadNewsArticles(query);
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
                model.loadNewsArticles();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
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
