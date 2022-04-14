package com.pad.cuvantar.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "flashcard")
public class FlashcardModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String front;
    String back;
    String definition;

    public FlashcardModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFront() {
        return front;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlashcardModel that = (FlashcardModel) o;
        return id == that.id && Objects.equals(front, that.front) && Objects.equals(back, that.back) && Objects.equals(definition, that.definition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, front, back, definition);
    }


}
