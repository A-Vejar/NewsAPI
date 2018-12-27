package cl.ucn.disc.dsm.avejar.newsapi.models;

import java.util.Date;
import lombok.Getter;

public class News {

    /**
     * News source
     */
    @Getter
    private Source source;

    /**
     * News author
     */
    @Getter
    private String author;

    /**
     * News title
     */
    @Getter
    private String title;

    /**
     * News description
     */
    @Getter
    private String description;

    /**
     * News url
     */
    @Getter
    private String url;

    /**
     * News url image
     */
    @Getter
    private String urlToImage;

    /**
     * News publish date
     */
    @Getter
    private Date publishedAt;

    /**
     * News content
     */
    @Getter
    private String content;

}