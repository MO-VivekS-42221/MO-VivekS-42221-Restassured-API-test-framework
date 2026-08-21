package com.api.automation.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * LoginResponse POJO - Represents login response body
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {
    private Integer id;
    private String token;
    private String message;
}
