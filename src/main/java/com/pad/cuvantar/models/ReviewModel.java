package com.pad.cuvantar.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "review")
public class ReviewModel {
    @Id
    @GeneratedValue
    int id;

    int user_id;
    int card_id;
    int level;
    LocalDateTime due_date;

    public ReviewModel() {
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

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public LocalDateTime getDue_date() {
        return due_date;
    }

    public void setDue_date(LocalDateTime due_date) {
        this.due_date = due_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewModel that = (ReviewModel) o;
        return id == that.id && user_id == that.user_id && card_id == that.card_id && level == that.level && Objects.equals(due_date, that.due_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, card_id, level, due_date);
    }

    @Override
    public String toString() {
        return "ReviewModel{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", card_id=" + card_id +
                ", level=" + level +
                ", due_date=" + due_date +
                '}';
    }
}