package cl.ucn.disc.dsm.avejar.newsapi.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cl.ucn.disc.dsm.avejar.newsapi.R;
import cl.ucn.disc.dsm.avejar.newsapi.models.News;

public final class NewsAdapter extends BaseAdapter {

    /**
     * News list
     */
    private List<News> news = new ArrayList<>();

    /**
     * Inflater
     */
    private final LayoutInflater inflater;

    /**
     * Constructor
     *
     * @param context - Context to obtain the inflater
     */
    public NewsAdapter(final Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * Load the news from a source
     *
     * @param news
     */
    public void loadListNews(List<News> news){
        this.news = news;
    }

    /**
     * Size of items in the list represented by this adapter
     *
     * @return - Count of items
     */
    @Override
    public int getCount() {
        return this.news.size();
    }

    /**
     * Get the specific position of the item in the list
     *
     * @param position - Item's position whose data we want inside the adapter
     * @return - The specific position and data
     */
    @Override
    public News getItem(int position) {
        return this.news.get(position);
    }

    /**
     * Get the row id associated with the specific position in the list
     *
     * @param position - Item's position inside the adapter whose row id needed
     * @return - Item's id in their specific position
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Get a "View" that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use:
     *  -> {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position - Item's position inside the adapter of the items whose view wanted
     * @param convertView - The old view to re-use
     *        Note: You should check that this view is non-null and of an appropriate type before using.
     *              If it is not possible to convert this view to display the correct data, this method
     *              can create a new view.
     *              Heterogeneous lists can specify their number of view types, so that this View is always
     *              of the right type (see {@link #getViewTypeCount()} and {@link #getItemViewType(int)}).
     *
     * @param parent - The parent that this view will eventually be attached to
     * @return - A "View" corresponding to the data at the specified position
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Gets all the data present in the class "News" and in their specific position
        final News news = getItem(position);

        // ViewHolder class instance (News class)
        ViewHolder holder;

        // If the activity view its null, it will be inflate
        if(convertView == null){

            // Display all the data inside the XML file "row_news" in the activity view
            convertView = inflater.inflate(R.layout.row_news, parent, false);

            // Instance and storage
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        }else {
            // It will be used again - get the holder from the view
            holder = (ViewHolder) convertView.getTag();
        }

        // It will show the "Author" or "Source" only its one of then exist (Could be both exist)
        if(news.getSource().getName() != null && news.getAuthor() != null){
            if(news.getSource().getName().equalsIgnoreCase(news.getAuthor())) {

                holder.source.setText(news.getSource().getName());
                convertView.findViewById(R.id.tv_author).setVisibility(View.GONE);

            }else {

                convertView.findViewById(R.id.tv_author).setVisibility(View.VISIBLE);
                holder.source.setText(news.getSource().getName());
                holder.author.setText(news.getAuthor());
            }
        }else if(news.getSource().getName() != null && news.getAuthor() == null) {

            holder.source.setText(news.getSource().getName());
            convertView.findViewById(R.id.tv_author).setVisibility(View.GONE);

        }else if(news.getSource().getName() == null && news.getAuthor() != null) {

            convertView.findViewById(R.id.tv_source).setVisibility(View.GONE);
            holder.author.setText(news.getAuthor());
        }else {

            holder.source.setText("Unknown");
            convertView.findViewById(R.id.tv_author).setVisibility(View.GONE);
        }

        // Shows the news data inside the TextViews
        holder.title.setText(news.getTitle());
        holder.description.setText(news.getDescription());

        // Publish date
        // Locale object that represents a specific geographical, political, or cultural region
        Locale locale = new Locale("en", "ES");

        // Formatting (Date -> Text) and Parsing (Text -> Date) dates class
        SimpleDateFormat format = new SimpleDateFormat("MMMM dd ',' yyyy '-' HH:mm", locale);
        holder.publishAt.setText(format.format(news.getPublishedAt()));

        // Display the url-image using "Fresco", only if this one exist
        if(news.getUrlToImage() != null){
            holder.urlImage.setImageURI(Uri.parse(news.getUrlToImage()));
        }

        // Return the activity view with the news info.
        return convertView;
    }

    /**
     * Inner class
     */
    private static class ViewHolder {

        // XML file attributes
        SimpleDraweeView urlImage;
        TextView source;
        TextView author;
        TextView title;
        TextView description;
        TextView publishAt;

        // Constructor
        ViewHolder(final View view) {
            this.urlImage = view.findViewById(R.id.sdv_urlImage);
            this.source = view.findViewById(R.id.tv_source);
            this.author = view.findViewById(R.id.tv_author);
            this.title = view.findViewById(R.id.tv_title);
            this.description = view.findViewById(R.id.tv_description);
            this.publishAt = view.findViewById(R.id.tv_published_at);
        }
    }
}
