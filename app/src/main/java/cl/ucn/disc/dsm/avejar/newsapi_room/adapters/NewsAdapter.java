package cl.ucn.disc.dsm.avejar.newsapi_room.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cl.ucn.disc.dsm.avejar.newsapi_room.MainActivity;
import cl.ucn.disc.dsm.avejar.newsapi_room.R;
import cl.ucn.disc.dsm.avejar.newsapi_room.activities.WebActivity;
import cl.ucn.disc.dsm.avejar.newsapi_room.models.News;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    /**
     * Inflater
     */
    private final LayoutInflater inflater;

    /**
     * News list
     */
    private List<News> newsArticles;


    public NewsAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    /**
     * @param list - List to add
     */
    public void addNewsArticles(final List<News> list) {
        this.newsArticles.addAll(list);
    }

    // row_news --> recyclerView_article
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = this.inflater.inflate(R.layout.row_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (newsArticles != null) {

            final News news = this.newsArticles.get(position);
            String newSource = "Source: " + news.getSource().getName();
            String newAuthor = "Author: " + news.getAuthor();

            // It will show the "Author" or "Source" only its one of then exist (Could be both exist)
            if(news.getSource().getName() != null && news.getAuthor() != null){
                if(news.getSource().getName().equalsIgnoreCase(news.getAuthor())) {

                    holder.source.setText(newSource);

                }else {

                    holder.source.setText(newSource);
                    holder.author.setText(newAuthor);
                }
            }else if(news.getSource().getName() != null && news.getAuthor() == null) {

                holder.source.setText(newSource);

            }else if(news.getSource().getName() == null && news.getAuthor() != null) {

                holder.author.setText(newAuthor);
            }else {

                holder.source.setText("Unknown");
            }

            // Shows the news data inside the TextViews
            holder.title.setText(news.getTitle());
            holder.description.setText(news.getDescription());

            // Save the url in the tag
            holder.itemView.setTag(news.getUrl());

            // Publish date
            holder.publishedAt.setText(dateChange(news.getPublishedAt()));

            // Freco Images
            if (news.getUrlToImage() != null) {
                holder.urlImage.setImageURI(Uri.parse(news.getUrlToImage()));
            }

        } else {
            // Covers the case of data not being ready yet.
            holder.title.setText("No News Found ...");
        }
    }

    // TODO: Fix publish date issue
    public String dateChange(String newDate){

        // Locale object that represents a specific geographical, political, or cultural region
        Locale locale = new Locale("en", "ES");

        // Formatting (Date -> Text) and Parsing (Text -> Date) dates class
        SimpleDateFormat format = new SimpleDateFormat("MMMM dd ',' yyyy '-' HH:mm", locale);

        Date date = null;
        try {
            date = new SimpleDateFormat("MMMM dd ',' yyyy '-' HH:mm").parse(newDate);

        }catch (ParseException e) {
            e.printStackTrace();
        }

        return newDate;
    }

    /**
     *
     * @param articles
     */
    public void setArticles(List<News> articles){
        this.newsArticles = articles;
        notifyDataSetChanged();
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if (newsArticles != null)
            return newsArticles.size();
        return 0;
    }

    /**
     *
     */
    protected static final class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final SimpleDraweeView urlImage;
        private final TextView source;
        private final TextView author;
        private final TextView title;
        private final TextView description;
        private final TextView publishedAt;


        private ViewHolder(View view) {
            super(view);
            urlImage = view.findViewById(R.id.sdv_urlImage);
            source = view.findViewById(R.id.tv_source);
            author = view.findViewById(R.id.tv_author);
            title = view.findViewById(R.id.tv_title);
            description = view.findViewById(R.id.tv_description);
            publishedAt = view.findViewById(R.id.tv_published_at);


        }

        /**
         * Call the view when this one its pressed
         *
         * @param view - View
         */
        @Override
        public void onClick(View view) {
            // TODO: Open the url in the same app by a WebView in another Activity

            final int position = super.getPosition();
        }
    }
}
