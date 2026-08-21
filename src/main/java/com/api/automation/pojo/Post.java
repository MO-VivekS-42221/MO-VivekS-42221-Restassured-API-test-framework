package com.api.automation.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * Post POJO - Represents a Post object from JSONPlaceholder API
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
    private Integer userId;
    private Integer id;
    private String title;
    private String body;

    /**
     * Constructor for creating new Post (without id)
     */
    public Post(Integer userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }
}
