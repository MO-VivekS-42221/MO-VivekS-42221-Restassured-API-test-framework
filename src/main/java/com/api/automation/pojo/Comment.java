package com.api.automation.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * Comment POJO - Represents a Comment object from JSONPlaceholder API
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {
    private Integer postId;
    private Integer id;
    private String name;
    private String email;
    private String body;

    /**
     * Constructor for creating new Comment (without id)
     */
    public Comment(Integer postId, String name, String email, String body) {
        this.postId = postId;
        this.name = name;
        this.email = email;
        this.body = body;
    }
}
