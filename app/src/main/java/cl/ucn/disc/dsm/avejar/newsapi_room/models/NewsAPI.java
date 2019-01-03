package cl.ucn.disc.dsm.avejar.newsapi_room.models;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class NewsAPI {

    /**
     * API Response status - Could take two values "ok" or "error_placeholder"
     */
    @Getter
    @Setter
    private String status;

    /**
     * News size
     */
    @Getter
    @Setter
    private int allResult;

    /**
     * News list to get
     */
    @Getter
    @Setter
    private List<News> articles = null;

}