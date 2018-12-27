package cl.ucn.disc.dsm.avejar.newsapi.models;

import java.util.List;
import lombok.Getter;

public class NewsAPI {

    /**
     * API Response status - Could take two values "ok" or "error_placeholder"
     */
    @Getter
    private String status;

    /**
     * News size
     */
    @Getter
    private int allResult;

    /**
     * News list to get
     */
    @Getter
    private List<News> articles = null;

}