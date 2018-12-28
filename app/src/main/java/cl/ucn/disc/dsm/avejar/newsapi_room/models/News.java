package cl.ucn.disc.dsm.avejar.newsapi_room.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import lombok.Getter;
import lombok.Setter;

@Entity(tableName = "news_table")
public class News {

    /**
     * News source
     */
    @Embedded(prefix = "src_")
    @Getter
    @Setter
    private Source source;

    /**
     * News author
     */
    @Getter
    @Setter
    private String author;

    /**
     * News title
     */
    @Getter
    @Setter
    private String title;

    /**
     * News description
     */
    @Getter
    @Setter
    private String description;

    /**
     * News url
     */
    @PrimaryKey
    @NonNull
    @Getter
    @Setter
    private String url;

    /**
     * News url image
     */
    @Getter
    @Setter
    private String urlToImage;

    /**
     * News publish date
     */
    @Getter
    @Setter
    private String publishedAt;

    /**
     * News content
     */
    @Getter
    @Setter
    private String content;

}