package cl.ucn.disc.dsm.avejar.newsapi_room.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cl.ucn.disc.dsm.avejar.newsapi_room.R;

public class WebActivity extends AppCompatActivity {

    WebView webUrl;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        // WebView
        this.webUrl = findViewById(R.id.web_view);

        // Open the url in the same app
        webUrl.setWebViewClient(new WebViewClient());

        // Get the data from the MainActivity --> URL
        url = getIntent().getStringExtra("new_url");

        // If the url is null goes to the News-Api web site (Button), if it not goes to the URL from the list
        if(url != null){
            webUrl.loadUrl(url);

        }else{
            webUrl.loadUrl("https://newsapi.org/docs/get-started");
        }
    }

    /**
     * Back button
     *
     * @param  - Activity view
     */
    public void BackButton(View view){
        finish();
    }
}
