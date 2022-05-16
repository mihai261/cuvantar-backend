package com.pad.cuvantar.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "auth_session")
public class AuthSessionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    int user_id;
    String username;
    String token;
    Timestamp created_at;

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthSessionModel that = (AuthSessionModel) o;
        return id == that.id && user_id == that.user_id && Objects.equals(username, that.username) && Objects.equals(token, that.token) && Objects.equals(created_at, that.created_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, username, token, created_at);
    }

    @Override
    public String toString() {
        return "AuthSessionModel{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", createdAt=" + created_at +
                '}';
    }
}
