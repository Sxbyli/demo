package com.example.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class TokenEntity implements Serializable {

    private String accessToken;

    public void updateToken(String token) {
        accessToken = token;
    }

}
