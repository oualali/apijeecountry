package com.example.pulco.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "token_black_list")
public class TokenBlackList implements Serializable {
    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private int id;
    private String token;
    @Column(name = "expiration_time")
    private LocalDateTime expirationTime;

    public TokenBlackList() {
    }

    public TokenBlackList(String token, LocalDateTime expirationTime) {
        this.token = token;
        this.expirationTime = expirationTime;
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    @Override
    public String toString() {
        return "TokenBlackList{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", expirationTime=" + expirationTime +
                '}';
    }
}
